package com.optic.Smartpillbox.Services;

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

import com.optic.Smartpillbox.Modulos.MenuActivity;
import com.optic.Smartpillbox.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AlarmAlertActivity extends AppCompatActivity {
    private Ringtone ringtone;
    @BindView(R.id.btnAlarmStop)
    Button mBtnAlarmStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_alert);
        stopService(new Intent(AlarmAlertActivity.this, GeneralService.class));
        ButterKnife.bind(this);
        ringtone = RingtoneManager.getRingtone(getApplicationContext(), RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE));
        ringtone.play();
        mBtnAlarmStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ringtone.stop();
                startActivity(new Intent(AlarmAlertActivity.this,MenuActivity.class));
                finishAffinity();
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