package com.optic.Smartpillbox.LogIn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.QuerySnapshot;
import com.optic.Smartpillbox.FirebaseService.FireStoreService;
import com.optic.Smartpillbox.MenuActivity;
import com.optic.Smartpillbox.R;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CredencialesActivity extends AppCompatActivity {



    @BindView(R.id.TextInputEmail)
    TextInputEditText mTxtEmail;
    @BindView(R.id.TextInputPassword)
    TextInputEditText mTxtPassword;
    @BindView(R.id.btnCredencial)
    Button mButtonGoToLogin;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private FireStoreService service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credenciales);
        ButterKnife.bind(this);
        service = new FireStoreService();
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Log In");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mButtonGoToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogIn();
            }
        });
    }
    public void verficarPastillero(FirebaseUser user){
        service.perfil_usuarios().whereEqualTo("userId",user.getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    try {
                        Map<String, Object> userProfile = task.getResult().getDocuments().get(0).getData();
                        if(userProfile.get("serie").equals(null)){
                            Intent intent = new Intent(CredencialesActivity.this, ValidarPastillera.class);
                            startActivity(intent);
                            finishAffinity();
                        }else{
                            Intent intent = new Intent(CredencialesActivity.this, MenuActivity.class);
                            startActivity(intent);
                            finishAffinity();
                        }
                    }catch (NullPointerException n){
                        Intent intent = new Intent(CredencialesActivity.this, ValidarPastillera.class);
                        startActivity(intent);
                        finishAffinity();
                    }
                }
            }
        });
    }
    public void LogIn(){
        service.mAuth.signInWithEmailAndPassword(mTxtEmail.getText().toString(), mTxtPassword.getText().toString())
                .addOnCompleteListener(task ->  {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("regStatus", "signInWithEmail:success");
                        FirebaseUser user = service.mAuth.getCurrentUser();
                        verficarPastillero(user);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("regStatus", "signInWithEmail:failure", task.getException());
                        Toast.makeText(CredencialesActivity.this, "Error de Autenticación! Verifique su usuario y/o contraseña.",
                                Toast.LENGTH_LONG).show();
                    }
                });
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
            Intent intent = new Intent(CredencialesActivity.this, MenuActivity.class);
            startActivity(intent);
            finish();
        }
    }
}