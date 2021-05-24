package com.optic.Smartpillbox.LogIn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseUser;
import com.optic.Smartpillbox.FirebaseService.FireStoreService;
import com.optic.Smartpillbox.MainActivity;
import com.optic.Smartpillbox.MenuActivity;
import com.optic.Smartpillbox.R;

public class SelectOpcionRegistrarActivity extends AppCompatActivity {

    Toolbar mToolbar;
    Button mButtonGoToUsuario;
    Button mButtonGoToApoyoUsuario;
    private FireStoreService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_opcion_registrar);
        service = new FireStoreService();
        mToolbar = findViewById(R.id.toolbar);
        mButtonGoToUsuario = findViewById(R.id.btnGoToUsuario);
        mButtonGoToApoyoUsuario = findViewById(R.id.btnGoToApoyoUsuario);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Seleccionar Opción");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mButtonGoToUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoToUsuario();
            }
        });
        mButtonGoToApoyoUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoToApoyoUsuario();
            }
        });
    }
    public void GoToUsuario(){
        Intent intent = new Intent(SelectOpcionRegistrarActivity.this, RegisterActivity.class)
                .putExtra("tipo",'u');
        startActivity(intent);
    }
    public void GoToApoyoUsuario(){
        Intent intent = new Intent(SelectOpcionRegistrarActivity.this, RegisterActivity.class)
                .putExtra("tipo",'a');
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = service.mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(SelectOpcionRegistrarActivity.this, MenuActivity.class);
            startActivity(intent);
            finish();
        }
    }
}