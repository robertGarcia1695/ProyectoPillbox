package com.optic.Smartpillbox.Services;

import android.app.Service;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.optic.Smartpillbox.FirebaseService.FireStoreService;
import com.optic.Smartpillbox.Model.Pastilla;
import com.optic.Smartpillbox.Modulos.MainActivity;
import com.optic.Smartpillbox.Modulos.MenuActivity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class GeneralService extends Service {
    private Timer t = new Timer();
    private FireStoreService service;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        service = new FireStoreService();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                checkAlarmPills();
            }
        }, 200, 1000);
        return START_STICKY;
    }
    public void checkAlarmPills(){
        service.perfil_usuarios().whereEqualTo("userId",service.mAuth.getUid()).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value1, @Nullable FirebaseFirestoreException error) {
                try {
                    service.pastillero_virtual().whereEqualTo("serie", value1.getDocuments().get(0).getString("serie")).addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value2, @Nullable FirebaseFirestoreException error) {
                            for (DocumentSnapshot d : value2.getDocuments()) {
                                service.pastillero_rtdb().addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        Pastilla pastilla = d.toObject(Pastilla.class);
                                        Integer alarmHour = Integer.parseInt(pastilla.getHora().substring(0, 2));
                                        Integer alarmMinute = Integer.parseInt(pastilla.getHora().substring(3, 5));
                                        Object isLunes = dataSnapshot.child("Lunes").getValue();
                                        Object isMartes = dataSnapshot.child("Martes").getValue();
                                        Object isMiercoles = dataSnapshot.child("Miercoles").getValue();
                                        Object isJueves = dataSnapshot.child("Jueves").getValue();
                                        Object isViernes = dataSnapshot.child("Viernes").getValue();
                                        Object isSabado = dataSnapshot.child("Sabado").getValue();
                                        Object isDomingo = dataSnapshot.child("Domingo").getValue();
                                        ArrayList<Boolean> diasSemanaIoT = new ArrayList<>();
                                        diasSemanaIoT.add(Boolean.parseBoolean(isDomingo.toString()));
                                        diasSemanaIoT.add(Boolean.parseBoolean(isLunes.toString()));
                                        diasSemanaIoT.add(Boolean.parseBoolean(isMartes.toString()));
                                        diasSemanaIoT.add(Boolean.parseBoolean(isMiercoles.toString()));
                                        diasSemanaIoT.add(Boolean.parseBoolean(isJueves.toString()));
                                        diasSemanaIoT.add(Boolean.parseBoolean(isViernes.toString()));
                                        diasSemanaIoT.add(Boolean.parseBoolean(isSabado.toString()));
                                        Log.d("dayOfWeek",LocalDateTime.now().getDayOfWeek().getValue() + "");
                                        Log.d("Loging_Data_Alarm", d.getId() + d.getString("nom"));
                                        if (Calendar.getInstance().getTime().getHours() == alarmHour &&
                                                Calendar.getInstance().getTime().getMinutes() == alarmMinute
                                                && pastilla.getDiasSemana().get(LocalDateTime.now().getDayOfWeek().getValue())
                                                && diasSemanaIoT.get(LocalDateTime.now().getDayOfWeek().getValue())) {
                                            Intent intent = new Intent(GeneralService.this, AlarmAlertActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                            t.cancel();
                                        }
                                        if(!diasSemanaIoT.get(0) && !diasSemanaIoT.get(1) && !diasSemanaIoT.get(2) && !diasSemanaIoT.get(3) &&
                                                !diasSemanaIoT.get(4) && !diasSemanaIoT.get(5) && !diasSemanaIoT.get(6)){
                                            Intent intent = new Intent(GeneralService.this,PillCheckActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                            t.cancel();
                                        }
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                        }
                    });
                }catch (IndexOutOfBoundsException i){
                    stopService(new Intent(GeneralService.this,GeneralService.class));
                    Intent intent = new Intent(GeneralService.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    t.cancel();
                }
            }
        });
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
