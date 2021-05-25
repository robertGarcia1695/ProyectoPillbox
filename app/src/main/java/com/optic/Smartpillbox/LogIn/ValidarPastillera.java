package com.optic.Smartpillbox.LogIn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.optic.Smartpillbox.FirebaseService.FireStoreService;
import com.optic.Smartpillbox.MenuActivity;
import com.optic.Smartpillbox.R;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ValidarPastillera extends AppCompatActivity {

    @BindView(R.id.txtSerie)
    TextInputEditText mTxtSerie;
    @BindView(R.id.btnConfirmarCodigo)
    Button mBtnConfirmarCodigo;
    FireStoreService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validar_pastillera);
        ButterKnife.bind(this);
        service = new FireStoreService();
        mBtnConfirmarCodigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                service.num_serie_pastilleras().document(mTxtSerie.getText().toString()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            if(task.getResult().exists()){
                                service.perfil_usuarios().whereEqualTo("userId",service.mAuth.getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()){
                                            DocumentSnapshot doc = task.getResult().getDocuments().get(0);
                                            Map<String,Object> docMap = doc.getData();
                                            docMap.put("serie",mTxtSerie.getText().toString());
                                            service.perfil_usuarios().document(doc.getId()).update(docMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful()){
                                                        startActivity(new Intent(ValidarPastillera.this, MenuActivity.class));
                                                        finishAffinity();
                                                    }else{
                                                        Toast.makeText(ValidarPastillera.this,"No se pudo registrar el codigo.",Toast.LENGTH_LONG).show();
                                                        service.mAuth.signOut();
                                                        startActivity(new Intent(ValidarPastillera.this, CredencialesActivity.class));
                                                        finishAffinity();
                                                    }
                                                }
                                            });
                                        }else{
                                            Toast.makeText(ValidarPastillera.this,"No se pudo validar la pastillera. Verfique su conexion o intente más tarde.",Toast.LENGTH_LONG).show();
                                            service.mAuth.signOut();
                                            startActivity(new Intent(ValidarPastillera.this, CredencialesActivity.class));
                                            finishAffinity();
                                        }
                                    }
                                });
                            }else{
                                mBtnConfirmarCodigo.setError("Numero de pastillero inválido.");
                                service.mAuth.signOut();
                                startActivity(new Intent(ValidarPastillera.this, CredencialesActivity.class));
                                finishAffinity();
                            }
                        }else{
                            Toast.makeText(ValidarPastillera.this,"No se pudo validar la pastillera. Verfique su conexion o intente más tarde.",Toast.LENGTH_LONG).show();
                            service.mAuth.signOut();
                            startActivity(new Intent(ValidarPastillera.this, CredencialesActivity.class));
                            finishAffinity();
                        }
                    }
                });
            }
        });
    }
}