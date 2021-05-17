package com.optic.Smartpillbox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegisterActivity extends AppCompatActivity {

    Toolbar mToolbar;
    Button mButtonGoToRegisterUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Seleccionar Opción");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mButtonGoToRegisterUser = findViewById(R.id.btnGoToRegisterUser);
        mButtonGoToRegisterUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToOpcion();
            }
        });
    }
    public void goToOpcion(){
        Intent intent = new Intent(RegisterActivity.this, CredencialesActivity.class);
        startActivity(intent);
    }
}
