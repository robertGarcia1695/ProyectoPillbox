package com.optic.Smartpillbox.Services;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ProgressBar;

import com.optic.Smartpillbox.Modulos.MainActivity;
import com.optic.Smartpillbox.R;

import butterknife.BindView;

public class InternetServiceActivity extends AppCompatActivity {
    @BindView(R.id.progInternet)
    ProgressBar mProgInternet;
    CountDownTimer CDT;
    private CheckNetworkStatus checkNetworkStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internet_service);
        checkNetworkStatus = new CheckNetworkStatus((ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE));
        CDT = new CountDownTimer(Long.MAX_VALUE, 100)
        {
            public void onTick(long millisUntilFinished)
            {
                if(checkNetworkStatus.verifyConnectivity()){
                    startActivity(new Intent(InternetServiceActivity.this, MainActivity.class));
                    finish();
                    cancel();
                }
            }
            public void onFinish()
            {

            }

        }.start();
    }
}