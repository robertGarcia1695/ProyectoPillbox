package com.optic.Smartpillbox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText mTextInputEmail;
    TextInputEditText mTextInputPassword;
    Button mButtonLogin;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mTextInputEmail = findViewById(R.id.TextInputEmail);
        mTextInputPassword = findViewById(R.id.TextInputPassword);
        mButtonLogin = findViewById(R.id.btnLogin);

        //Instancaimos las dependencias
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                login();
            }
        });

    }

    private void login(){
        String email = mTextInputEmail.getText().toString();
        String password = mTextInputPassword.getText().toString();
        if(!email.isEmpty() && !password.isEmpty()){
            //Determinamos la longitud la contraseña por sugerencía de farebase
            if(password.length() >= 6){
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if( task.isSuccessful()){
                            Toast.makeText( LoginActivity.this, "La validación se realizo correctamente", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText( LoginActivity.this, "La Contraseña or el Password son incorrectos", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            } else {

                Toast.makeText(LoginActivity.this, "La Contraseña debe de tener más de 6 caracteres", Toast.LENGTH_SHORT).show();
            }
        }
            else{

            Toast.makeText( LoginActivity.this, "La Contraseña y el Email son obligatorio", Toast.LENGTH_SHORT).show();
        }

    }
}
