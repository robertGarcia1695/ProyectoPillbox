package com.optic.Smartpillbox.Modulos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.QuerySnapshot;
import com.optic.Smartpillbox.FirebaseService.FireStoreService;
import com.optic.Smartpillbox.LogIn.ValidarPastilleraActivity;
import com.optic.Smartpillbox.R;
import com.optic.Smartpillbox.Services.BroadcastReciever;
import com.optic.Smartpillbox.Services.CheckNetworkStatus;
import com.optic.Smartpillbox.Services.GeneralService;
import com.optic.Smartpillbox.Services.InternetServiceActivity;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuActivity extends AppCompatActivity {

    @BindView(R.id.btnSignout)
    Button mBtnSignOut;
    @BindView(R.id.btnGoToPerfil)
    Button mBtnGoToPerfil;
    @BindView(R.id.btnGoToPastillero)
    Button mBtnGoToPastillero;

    private FireStoreService service;
    private CheckNetworkStatus networkStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        networkStatus = new CheckNetworkStatus((ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE));
        service = new FireStoreService();
        ButterKnife.bind(this);
        verficarPastillero(service.mAuth.getCurrentUser());

        /*if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            Toast.makeText(this,"Starting Alarm",Toast.LENGTH_LONG).show();
            CharSequence name = "Example Alarm";
            String description = "This is an example alarm";
            int importance_level = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("Alarm1", name, importance_level);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            Intent intent = new Intent(this, BroadcastReciever.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,intent,0);

            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

            long timeAtStartTime = System.currentTimeMillis();
            long tenSecsInMillis = 1000 * 10;
            alarmManager.set(AlarmManager.RTC_WAKEUP,timeAtStartTime + tenSecsInMillis,pendingIntent);
        }else{
            Toast.makeText(this,"Can't activate alarm",Toast.LENGTH_LONG).show();
        }*/

    }
    public void verficarPastillero(FirebaseUser user){
        service.perfil_usuarios().whereEqualTo("userId",user.getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    try {
                        Map<String, Object> userProfile = task.getResult().getDocuments().get(0).getData();
                        if(userProfile.get("serie").equals(null)){
                            Intent intent = new Intent(MenuActivity.this, ValidarPastilleraActivity.class);
                            startActivity(intent);
                            finishAffinity();
                        }else{
                            mBtnSignOut.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    service.mAuth.signOut();
                                    Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finishAffinity();
                                }
                            });
                            mBtnGoToPerfil.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(MenuActivity.this, PerfilActivity.class);
                                    startActivity(intent);
                                }
                            });
                            mBtnGoToPastillero.setOnClickListener(v -> {
                                Intent intent = new Intent(MenuActivity.this,ListPastilleroActivity.class)
                                        .putExtra("serie",userProfile.get("serie").toString());
                                startActivity(intent);
                            });
                            //Inicializa el servicio
                            Intent serviceStarter = new Intent(MenuActivity.this, GeneralService.class);
                            startService(serviceStarter);
                        }
                    }catch (NullPointerException n){
                        Intent intent = new Intent(MenuActivity.this, ValidarPastilleraActivity.class);
                        startActivity(intent);
                        finishAffinity();
                    }catch (IndexOutOfBoundsException i){

                    }
                }
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        if (networkStatus.verifyConnectivity()) {

        } else if (!networkStatus.verifyConnectivity()) {
            startActivity(new Intent(this, InternetServiceActivity.class));
        }
    }

}