package com.optic.Smartpillbox.Modulos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseUser;
import com.optic.Smartpillbox.FirebaseService.FireStoreService;
import com.optic.Smartpillbox.LogIn.CredencialesActivity;
import com.optic.Smartpillbox.LogIn.SelectOpcionRegistrarActivity;
import com.optic.Smartpillbox.R;
import com.optic.Smartpillbox.Services.CheckNetworkStatus;
import com.optic.Smartpillbox.Services.InternetServiceActivity;

public class MainActivity extends AppCompatActivity {

    Button mButtonIamPaciente;
    Button mButtonIamFamiliar;
    private FireStoreService service;
    private CheckNetworkStatus networkStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        networkStatus = new CheckNetworkStatus((ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE));
        mButtonIamPaciente = findViewById(R.id.btnGoToLogin);
        mButtonIamFamiliar = findViewById(R.id.btnGoToRegistrarse);
        service = new FireStoreService();

        //---creamos metodo para indicarle la funcionalidad que va ejecutar para el paciente
        mButtonIamPaciente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToLogIn();
            }
        });

        //---creamos metodo para indicarle la funcionalidad que va ejecutar para el paciente
        mButtonIamFamiliar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToSelectAuth2();
            }
        });
    }

    private void goToLogIn() {
        Intent intent = new Intent(MainActivity.this, CredencialesActivity.class);
        startActivity(intent);
    }
    private void goToSelectAuth2() {
        Intent intent = new Intent(MainActivity.this, SelectOpcionRegistrarActivity.class);
        startActivity(intent);
    }
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = service.mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(MainActivity.this, MenuActivity.class);
            startActivity(intent);
            finish();
        }
        if(networkStatus.verifyConnectivity()){

        }else if(!networkStatus.verifyConnectivity()){
            startActivity(new Intent(this, InternetServiceActivity.class));
        }
    }

}
