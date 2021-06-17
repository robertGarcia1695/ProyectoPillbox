package com.optic.Smartpillbox.Services;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.optic.Smartpillbox.FirebaseService.FireStoreService;
import com.optic.Smartpillbox.Modulos.MenuActivity;
import com.optic.Smartpillbox.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PillCheckActivity extends AppCompatActivity {
    @BindView(R.id.btnAlarmStop)
    Button mBtnAlarmStop;
    private FireStoreService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pill_check);
        stopService(new Intent(PillCheckActivity.this, GeneralService.class));
        ButterKnife.bind(this);
        service = new FireStoreService();
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
                        /*&& (Calendar.getInstance().getTime().getMinutes() == 5
                                || Calendar.getInstance().getTime().getMinutes() == 10
                                || Calendar.getInstance().getTime().getMinutes() == 15
                                || Calendar.getInstance().getTime().getMinutes() == 20
                                || Calendar.getInstance().getTime().getMinutes() == 25
                                || Calendar.getInstance().getTime().getMinutes() == 30
                                || Calendar.getInstance().getTime().getMinutes() == 35
                                || Calendar.getInstance().getTime().getMinutes() == 40
                                || Calendar.getInstance().getTime().getMinutes() == 45
                                || Calendar.getInstance().getTime().getMinutes() == 55
                                || Calendar.getInstance().getTime().getMinutes() == 0)*/){
                    mBtnAlarmStop.setText("Vacio");
                    mBtnAlarmStop.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(PillCheckActivity.this,"Su pastillero sigue vacio. Ingrese las pastillas que necesita.",Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    mBtnAlarmStop.setText("Siguiente");
                    mBtnAlarmStop.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(PillCheckActivity.this, MenuActivity.class));
                            finishAffinity();
                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    protected void onPause() {
        super.onPause();
        ActivityManager activityManager = (ActivityManager) getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);
        activityManager.moveTaskToFront(getTaskId(), 0);
    }
    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Debe presionar el botón.",Toast.LENGTH_LONG).show();
    }
}