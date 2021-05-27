package com.optic.Smartpillbox.Modulos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentSnapshot;
import com.optic.Smartpillbox.FirebaseService.FireStoreService;
import com.optic.Smartpillbox.R;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActualizarPerfilActivity extends AppCompatActivity {
    @BindView(R.id.txtNom)
    TextInputEditText mTxtNom;
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
        getSupportActionBar().setTitle("Actualizar Perfil");
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
        Map<String,Object> usuarioMap = new HashMap<>();
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
    }
    public boolean validarDatos(){
        Boolean[] validacion = new Boolean[2];
        boolean confirmacion = false;
        for(int i = 0; i < validacion.length; i++){
            validacion[i] = false;
        }
        if(mTxtNom.getText().toString().equals("")){
            mTxtNom.setError("Debe ingresar su nombre.");
        }else{
            validacion[0] = true;
        }

        if(mTxtEdad.getText().toString().equals("")){
            mTxtEdad.setError("Debe ingresar su edad.");
        }else{
            validacion[1] = true;
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }
}