package com.optic.Smartpillbox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.optic.Smartpillbox.FirebaseService.FireStoreService;
import com.optic.Smartpillbox.LogIn.CredencialesActivity;
import com.optic.Smartpillbox.LogIn.SelectOpcionRegistrarActivity;

public class MainActivity extends AppCompatActivity {

    Button mButtonIamPaciente;
    Button mButtonIamFamiliar;
    private FireStoreService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButtonIamPaciente = findViewById(R.id.btnGoToLogin);
        mButtonIamFamiliar = findViewById(R.id.btnGoToRegistrarse);

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
        // Check if user is signed in (non-null) and update UI accordingly.
        service = new FireStoreService();
        FirebaseUser currentUser = service.mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(MainActivity.this, MenuActivity.class);
            startActivity(intent);
        }
    }
}
