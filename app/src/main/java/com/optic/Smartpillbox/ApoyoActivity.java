package com.optic.Smartpillbox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.optic.Smartpillbox.LogIn.CredencialesActivity;

public class ApoyoActivity extends AppCompatActivity {

    Toolbar mToolbar;
    Button mButtonGoToRegisterApoyo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apoyo);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Seleccionar Opción");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mButtonGoToRegisterApoyo = findViewById(R.id.btnGoToRegisterApoyo);
        mButtonGoToRegisterApoyo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToOpcionApoyo();
            }
        });
    }
    public void goToOpcionApoyo(){
        Intent intent = new Intent(ApoyoActivity.this, CredencialesActivity.class);
        startActivity(intent);
    }
}