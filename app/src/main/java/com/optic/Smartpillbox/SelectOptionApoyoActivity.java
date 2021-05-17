package com.optic.Smartpillbox;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class SelectOptionApoyoActivity extends AppCompatActivity {
    Toolbar mToolbar;
    Button mButtonGoToCredencial;
    Button mButtonGoToRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_option_apoyo);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Seleccionar Opción");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mButtonGoToCredencial =findViewById(R.id.btnCredencial);
        mButtonGoToCredencial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToLoginA();
            }
        });


        mButtonGoToRegister = findViewById(R.id.btnGoToRegister);
        mButtonGoToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToRegisterA();
            }
        });
    }
    public void goToLoginA(){
        Intent intent = new Intent(SelectOptionApoyoActivity.this, CredencialesActivity.class);
        startActivity(intent);
    }


    public void goToRegisterA(){
        Intent intent = new Intent(SelectOptionApoyoActivity.this, ApoyoActivity.class);
        startActivity(intent);
    }


}