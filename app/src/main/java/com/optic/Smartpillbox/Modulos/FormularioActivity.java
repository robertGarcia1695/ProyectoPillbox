package com.optic.Smartpillbox.Modulos;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.optic.Smartpillbox.FirebaseService.FireStoreService;
import com.optic.Smartpillbox.R;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FormularioActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.chk1)
    RadioButton mChk1;
    @BindView(R.id.chk2)
    RadioButton mChk2;
    @BindView(R.id.chk3)
    RadioButton mChk3;
    @BindView(R.id.chk4)
    RadioButton mChk4;
    @BindView(R.id.chk5)
    RadioButton mChk5;
    @BindView(R.id.chk6)
    RadioButton mChk6;
    @BindView(R.id.chk7)
    RadioButton mChk7;
    @BindView(R.id.btnGoToRegisterUser)
    Button mBtnGoToRegisterUser;


    @BindView(R.id.txtTemperatura)
    TextInputEditText mTxtTemperatura;
    @BindView(R.id.txtPeso)
    TextInputEditText mTxtPeso;
    @BindView(R.id.txtPresion)
    TextInputEditText mTxtPresion;
    @BindView(R.id.txtOx1)
    TextInputEditText mTxtOx1;
    @BindView(R.id.txtOx2)
    TextInputEditText mTxtOx2;

    private FireStoreService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        ButterKnife.bind(this);
        service = new FireStoreService();
        Bundle bundle = getIntent().getExtras();
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Formulario COVID-19");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mBtnGoToRegisterUser.setOnClickListener(v -> {
            if(validarCampos()){
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date date = new Date();
                Map<String, Object> datos = new HashMap<>();
                datos.put("temp", mTxtTemperatura.getText().toString());
                datos.put("peso", mTxtPeso.getText().toString());
                datos.put("presion", mTxtPeso.getText().toString());
                datos.put("ox1", mTxtOx1.getText().toString());
                datos.put("ox2", mTxtOx2.getText().toString());
                datos.put("chk1", mChk1.isChecked());
                datos.put("chk2", mChk2.isChecked());
                datos.put("chk3", mChk3.isChecked());
                datos.put("chk4", mChk4.isChecked());
                datos.put("chk5", mChk5.isChecked());
                datos.put("chk6", mChk6.isChecked());
                datos.put("chk7", mChk7.isChecked());
                datos.put("serie", bundle.getString("serie"));
                datos.put("horaReg", formatter.format(date));
                service.reporte_covid().add(datos).addOnCompleteListener(task ->{
                    if(task.isSuccessful()){
                        Toast.makeText(FormularioActivity.this,"Se ha registrado satisfactoriamente.",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(FormularioActivity.this,MenuActivity.class);
                        startActivity(intent);
                    }
                });
            }else{
                Toast.makeText(FormularioActivity.this,"Debe llenar todo los campos de texto.",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validarCampos(){
        boolean[] campos = new boolean[3];
        for(int i = 0; i < campos.length; i++){
            campos[i] = false;
        }


        if(!mTxtTemperatura.getText().toString().equals("")){
            campos[0] = true;
        }else{
            campos[0] = false;
            mTxtTemperatura.setError("Debe ingresar su temperatura.");
        }
        if(!mTxtPeso.getText().toString().equals("")){
            campos[1] = true;
        }else{
            campos[1] = false;
            mTxtPeso.setError("Debe ingresar su peso.");
        }
        if(!mTxtPresion.getText().toString().equals("")){
            campos[2] = true;
        }else{
            campos[2] = false;
            mTxtPresion.setError("Debe ingresar su presion.");
        }

        if (campos[0] == true && campos[1]&& campos[2]) {
            return true;
        }else{
            return false;
        }
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
