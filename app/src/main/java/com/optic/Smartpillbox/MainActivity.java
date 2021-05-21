package com.optic.Smartpillbox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.optic.Smartpillbox.LogIn.CredencialesActivity;
import com.optic.Smartpillbox.LogIn.SelectOpcionRegistrarActivity;

public class MainActivity extends AppCompatActivity {

    Button mButtonIamPaciente;
    Button mButtonIamFamiliar;
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
}
