package com.optic.Smartpillbox.Modulos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.optic.Smartpillbox.FirebaseService.FireStoreService;
import com.optic.Smartpillbox.R;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActualizarPerfilActivity extends AppCompatActivity {
    @BindView(R.id.txtNom)
    TextInputEditText mTxtNom;
    @BindView(R.id.txtEmail)
    TextInputEditText mTxtEmail;
    @BindView(R.id.txtEdad)
    TextInputEditText mTxtEdad;
    @BindView(R.id.spinnerSexo)
    Spinner mSpinnerSexo;
    @BindView(R.id.btnActualizarPerfil)
    Button mBtnActualizarPerfil;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private FireStoreService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_perfil);
        ButterKnife.bind(this);
        service = new FireStoreService();
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Log In");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle bundle = getIntent().getExtras();
        String[] sexo = new String[3];
        sexo[0] = "Masculino";
        sexo[1] = "Femenino";
        sexo[2] = "Otro";
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, sexo);
        service.perfil_usuarios().document(bundle.getString("id")).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    mSpinnerSexo.setAdapter(adapter);
                    mTxtNom.setText(task.getResult().getString("nom"));
                    mTxtEmail.setText(service.mAuth.getCurrentUser().getEmail());
                    mTxtEdad.setText(task.getResult().getLong("edad").toString());
                    switch (task.getResult().getString("sexo")){
                        case "Masculino": mSpinnerSexo.setSelection(0);break;
                        case "Femenino": mSpinnerSexo.setSelection(1);break;
                        case "Otro": mSpinnerSexo.setSelection(2);break;
                    }
                    //Toast.makeText(this,bundle.getString("id"),Toast.LENGTH_LONG).show();
                    mBtnActualizarPerfil.setOnClickListener(v ->  {
                        if(validarDatos()) {
                            ActualizarInformacion(bundle.getString("id"));
                        }
                    });
                }else {

                }
            }
        });

    }
    public void ActualizarInformacion(String id){
        service.mAuth.getCurrentUser().updateEmail(mTxtEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Map<String,Object> usuarioMap = new HashMap<String,Object>();
                    usuarioMap.put("nom",mTxtNom.getText().toString());
                    usuarioMap.put("edad",Integer.parseInt(mTxtEdad.getText().toString()));
                    usuarioMap.put("sexo", mSpinnerSexo.getSelectedItem().toString());
                    service.perfil_usuarios().document(id).update(usuarioMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull  Task<Void> task) {
                            if(task.isSuccessful()){
                                startActivity(new Intent(ActualizarPerfilActivity.this,MenuActivity.class));
                                finish();
                            }
                        }
                    });
                }else{
                    mTxtEmail.setError("Intente con otro correo");
                }
            }
        });
    }
    public boolean validarDatos(){
        Boolean[] validacion = new Boolean[3];
        boolean confirmacion = false;
        for(int i = 0; i < validacion.length; i++){
            validacion[i] = false;
        }
        if(mTxtNom.getText().toString().equals("")){
            mTxtNom.setError("Debe ingresar su nombre.");
        }else{
            validacion[0] = true;
        }

        if(mTxtEmail.getText().toString().equals("") || !mTxtEmail.getText().toString().contains("@")){
            mTxtEmail.setError("Debe ingresar un correo valido.");
        }else{
            validacion[1] = true;
        }

        if(mTxtEdad.getText().toString().equals("")){
            mTxtEdad.setError("Debe ingresar su edad.");
        }else{
            validacion[2] = true;
        }

        for(int i = 0; i < validacion.length;i++){
            if(validacion[i] == true){
                confirmacion = true;
            }else{
                confirmacion = false;
                break;
            }
        }
        return confirmacion;
    }
}