package com.optic.Smartpillbox.Modulos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.optic.Smartpillbox.FirebaseService.FireStoreService;
import com.optic.Smartpillbox.Model.Medicamento;
import com.optic.Smartpillbox.R;
import com.optic.Smartpillbox.Services.GeneralService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterPastilleroActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.spinnerNom)
    Spinner mSpinnerNom;
    @BindView(R.id.txtCantidad)
    TextInputEditText mTxtCantidad;
    @BindView(R.id.lyHoraToma)
    LinearLayout mLyHoraToma;
    @BindView(R.id.txtHoraToma)
    TextInputEditText mTxtHoraToma;
    @BindView(R.id.btnAgregar)
    Button mBtnAgregar;
    @BindView(R.id.chkLunes)
    CheckBox mChkLunes;
    @BindView(R.id.chkMartes)
    CheckBox mChkMartes;
    @BindView(R.id.chkMiercoles)
    CheckBox mChkMiercoles;
    @BindView(R.id.chkJueves)
    CheckBox mChkJueves;
    @BindView(R.id.chkViernes)
    CheckBox mChkViernes;
    @BindView(R.id.chkSabado)
    CheckBox mChkSabado;
    @BindView(R.id.chkDomingo)
    CheckBox mChkDomingo;

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
        service.lista_pastillas().get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    List<Medicamento> medicamentos = new ArrayList<>();
                    for(DocumentSnapshot a: task.getResult().getDocuments()){
                        medicamentos.add(a.toObject(Medicamento.class));
                    }
                    String[] medNom = new String[task.getResult().getDocuments().size()];
                    for(int i = 0; i < medNom.length; i++){
                        medNom[i] = medicamentos.get(i).getNombre();
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(RegisterPastilleroActivity.this,android.R.layout.simple_spinner_item, medNom);
                    mSpinnerNom.setAdapter(adapter);
                    mLyHoraToma.setOnClickListener(v -> {
                        Calendar calendar = Calendar.getInstance();
                        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                        int currentMinute = calendar.get(Calendar.MINUTE);
                        TimePickerDialog timePickerDialog = new TimePickerDialog(RegisterPastilleroActivity.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                if(minute > 9 && hourOfDay > 9){
                                    mTxtHoraToma.setText( hourOfDay + ":" + minute);
                                }else{
                                    String hour = "", min = "";
                                    if(hourOfDay <= 9){
                                        hour = "0" + hourOfDay;
                                    }else{
                                        hour = hourOfDay + "";
                                    }
                                    if(minute <= 9){
                                        min = "0" + minute;
                                    }else{
                                        min = minute + "";
                                    }
                                    mTxtHoraToma.setText( hour + ":" + min);
                                }
                            }
                        },currentHour,currentMinute,true
                        );
                        timePickerDialog.setTitle("Select Time");
                        timePickerDialog.show();
                    });
                    mBtnAgregar.setOnClickListener(v ->{
                        if(validarDatos()){
                            Map<String,Object> pastilla = new HashMap<>();
                            pastilla.put("nom",mSpinnerNom.getSelectedItem().toString());
                            pastilla.put("cantidad",Integer.parseInt(mTxtCantidad.getText().toString()));
                            pastilla.put("hora",mTxtHoraToma.getText().toString());
                            pastilla.put("serie",bundle.getString("serie"));
                            pastilla.put("isLunes",mChkLunes.isChecked());
                            pastilla.put("isMartes",mChkMartes.isChecked());
                            pastilla.put("isMiercoles",mChkMiercoles.isChecked());
                            pastilla.put("isJueves",mChkJueves.isChecked());
                            pastilla.put("isViernes",mChkViernes.isChecked());
                            pastilla.put("isSabado",mChkSabado.isChecked());
                            pastilla.put("isDomingo",mChkDomingo.isChecked());
                            service.pastillero_virtual().add(pastilla).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    final Intent intent = new Intent(RegisterPastilleroActivity.this, GeneralService.class);
                                    ServiceCaller(intent,pastilla.get("hora").toString());
                                    finish();
                                }
                            });
                        }
                    });
                }
            }
        });


    }

    private void ServiceCaller(Intent intent, String tiempo){

        stopService(intent);

        Integer alarmHour = Integer.parseInt(tiempo.substring(0,2));
        Integer alarmMinute = Integer.parseInt(tiempo.substring(3,5));
        //Toast.makeText(RegisterPastilleroActivity.this,alarmHour + "",Toast.LENGTH_SHORT).show();
        //Toast.makeText(RegisterPastilleroActivity.this,alarmMinute + "",Toast.LENGTH_SHORT).show();
        intent.putExtra("alarmHour", alarmHour);
        intent.putExtra("alarmMinute", alarmMinute);

        startService(intent);
    }
    public boolean validarDatos(){
        Boolean[] validacion = new Boolean[2];
        boolean confirmacion = false;
        for(int i = 0; i < validacion.length; i++){
            validacion[i] = false;
        }
        if(mSpinnerNom.getSelectedItem().toString().equals("")){
            //getSelectedItem().setError("Debe ingresar el nombre de la pastilla.");
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