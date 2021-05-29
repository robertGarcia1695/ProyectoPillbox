package com.optic.Smartpillbox.Modulos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TimePicker;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.optic.Smartpillbox.FirebaseService.FireStoreService;
import com.optic.Smartpillbox.R;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterPastilleroActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.txtNomPastilla)
    TextInputEditText mTxtNomPastilla;
    @BindView(R.id.txtCantidad)
    TextInputEditText mTxtCantidad;
    @BindView(R.id.lyHoraToma)
    LinearLayout mLyHoraToma;
    @BindView(R.id.txtHoraToma)
    TextInputEditText mTxtHoraToma;
    @BindView(R.id.btnAgregar)
    Button mBtnAgregar;

    private FireStoreService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_pastillero);
        ButterKnife.bind(this);
        service = new FireStoreService();
        Bundle bundle = getIntent().getExtras();
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Pastillero Virtual");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mTxtHoraToma.setText("00:00");
        mLyHoraToma.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
            int currentMinute = calendar.get(Calendar.MINUTE);
            TimePickerDialog timePickerDialog = new TimePickerDialog(RegisterPastilleroActivity.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    mTxtHoraToma.setText(hourOfDay + ":" + minute);
                }
            },currentHour,currentMinute,true
            );
            timePickerDialog.setTitle("Select Time");
            timePickerDialog.show();
        });
        mBtnAgregar.setOnClickListener(v ->{
            if(validarDatos()){
                Map<String,Object> pastilla = new HashMap<>();
                pastilla.put("nom",mTxtNomPastilla.getText().toString());
                pastilla.put("cantidad",Integer.parseInt(mTxtCantidad.getText().toString()));
                pastilla.put("hora",mTxtHoraToma.getText().toString());
                pastilla.put("serie",bundle.getString("serie"));
                service.pastillero_virtual().add(pastilla).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        finish();
                    }
                });
            }
        });
    }

    public boolean validarDatos(){
        Boolean[] validacion = new Boolean[2];
        boolean confirmacion = false;
        for(int i = 0; i < validacion.length; i++){
            validacion[i] = false;
        }
        if(mTxtNomPastilla.getText().toString().equals("")){
            mTxtNomPastilla.setError("Debe ingresar el nombre de la pastilla.");
        }else{
            validacion[0] = true;
        }
        if(mTxtCantidad.getText().toString().equals("")){
            mTxtCantidad.setError("Debe ingresar la cantidad.");
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