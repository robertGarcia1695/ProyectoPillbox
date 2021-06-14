package com.optic.Smartpillbox.Services;

import android.app.Service;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.optic.Smartpillbox.FirebaseService.FireStoreService;
import com.optic.Smartpillbox.Modulos.MainActivity;
import com.optic.Smartpillbox.Modulos.MenuActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class GeneralService extends Service {
    private Integer alarmHour;
    private Integer alarmMinute;
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
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                service.perfil_usuarios().whereEqualTo("userId",service.mAuth.getUid()).addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value1, @Nullable FirebaseFirestoreException error) {
                        try {
                            service.pastillero_virtual().whereEqualTo("serie", value1.getDocuments().get(0).getString("serie")).addSnapshotListener(new EventListener<QuerySnapshot>() {
                                @Override
                                public void onEvent(@Nullable QuerySnapshot value2, @Nullable FirebaseFirestoreException error) {
                                    for (DocumentSnapshot d : value2.getDocuments()) {
                                        Integer alarmHour = Integer.parseInt(d.getString("hora").substring(0, 2));
                                        Integer alarmMinute = Integer.parseInt(d.getString("hora").substring(3, 5));
                                        Log.d("Loging_Data_Alarm", d.getId() + d.getString("nom"));
                                        if (Calendar.getInstance().getTime().getHours() == alarmHour &&
                                                Calendar.getInstance().getTime().getMinutes() == alarmMinute) {
                                            t.cancel();
                                            Intent intent = new Intent(GeneralService.this, AlarmAlertActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                        }
                                    /*else {
                                        ringtone.stop();
                                    }*/
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
                service.pastillero_rtdb().addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Object isLunes = dataSnapshot.child("Lunes").getValue();
                        Object isMartes = dataSnapshot.child("Martes").getValue();
                        Object isMiercoles = dataSnapshot.child("Miercoles").getValue();
                        Object isJueves = dataSnapshot.child("Jueves").getValue();
                        Object isViernes = dataSnapshot.child("Viernes").getValue();
                        Object isSabado = dataSnapshot.child("Sabado").getValue();
                        Object isDomingo = dataSnapshot.child("Domingo").getValue();
                        ArrayList<Boolean> diasSemanaIoT = new ArrayList<>();
                        diasSemanaIoT.add(Boolean.parseBoolean(isLunes.toString()));
                        diasSemanaIoT.add(Boolean.parseBoolean(isMartes.toString()));
                        diasSemanaIoT.add(Boolean.parseBoolean(isMiercoles.toString()));
                        diasSemanaIoT.add(Boolean.parseBoolean(isJueves.toString()));
                        diasSemanaIoT.add(Boolean.parseBoolean(isViernes.toString()));
                        diasSemanaIoT.add(Boolean.parseBoolean(isSabado.toString()));
                        diasSemanaIoT.add(Boolean.parseBoolean(isDomingo.toString()));
                        if(!diasSemanaIoT.get(0) && !diasSemanaIoT.get(1) && !diasSemanaIoT.get(2) && !diasSemanaIoT.get(3) &&
                        !diasSemanaIoT.get(4) && !diasSemanaIoT.get(5) && !diasSemanaIoT.get(6)
                        && (Calendar.getInstance().getTime().getMinutes() == 5
                                || Calendar.getInstance().getTime().getMinutes() == 10
                                || Calendar.getInstance().getTime().getMinutes() == 15
                                || Calendar.getInstance().getTime().getMinutes() == 20
                                || Calendar.getInstance().getTime().getMinutes() == 25
                                || Calendar.getInstance().getTime().getMinutes() == 30
                                || Calendar.getInstance().getTime().getMinutes() == 35
                                || Calendar.getInstance().getTime().getMinutes() == 40
                                || Calendar.getInstance().getTime().getMinutes() == 45
                                || Calendar.getInstance().getTime().getMinutes() == 55
                                || Calendar.getInstance().getTime().getMinutes() == 0)){
                            t.cancel();
                            Intent intent = new Intent(GeneralService.this,PillCheckActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        }, 0, 1000);
        /*alarmHour = intent.getIntExtra("alarmHour", 0);
        alarmMinute = intent.getIntExtra("alarmMinute", 0);*/
        /*t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (Calendar.getInstance().getTime().getHours() == alarmHour &&
                        Calendar.getInstance().getTime().getMinutes() == alarmMinute){
                    ringtone.play();
                }
                else {
                    ringtone.stop();
                }

            }
        }, 0, 2000);*/

        return Service.START_STICKY;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
