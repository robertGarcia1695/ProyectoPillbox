package com.optic.Smartpillbox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.os.Bundle;

import android.widget.Button;

public class OptionApoyoActivity extends AppCompatActivity {

    Toolbar mToolbar;
    Button mButtonGoToCredencial;
    Button mButtonGoToRegister;
    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Seleccionar Opción");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mButtonGoToCredencial =findViewById(R.id.btnCredencial);
        mButtonGoToRegister = findViewById(R.id.btnGoToRegister);


    }

}