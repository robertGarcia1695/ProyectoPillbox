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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.optic.Smartpillbox.FirebaseService.FireStoreService;
import com.optic.Smartpillbox.MenuActivity;
import com.optic.Smartpillbox.R;

import butterknife.BindView;

public class CredencialesActivity extends AppCompatActivity {

    Toolbar mToolbar;
    Button mButtonGoToLogin;

    @BindView(R.id.TextInputEmail)
    TextInputEditText mTxtEmail;
    @BindView(R.id.TextInputPassword)
    TextInputEditText mTxtPassword;
    private FireStoreService service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credenciales);
        service = new FireStoreService();
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Log In");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mButtonGoToLogin = findViewById(R.id.btnCredencial);
        mButtonGoToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToMenu();
            }
        });
    }
    public void goToMenu(){
        Intent intent = new Intent(CredencialesActivity.this, MenuActivity.class);
        startActivity(intent);
    }
    public void LogIn(){
        service.mAuth.signInWithEmailAndPassword(mTxtEmail.getText().toString(), mTxtPassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("regStatus", "signInWithEmail:success");
                            FirebaseUser user = service.mAuth.getCurrentUser();
                            Intent intent = new Intent(CredencialesActivity.this,MenuActivity.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("regStatus", "signInWithEmail:failure", task.getException());
                            Toast.makeText(CredencialesActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
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
}