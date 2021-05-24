package com.optic.Smartpillbox.LogIn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.optic.Smartpillbox.FirebaseService.FireStoreService;
import com.optic.Smartpillbox.MenuActivity;
import com.optic.Smartpillbox.Model.Usuario;
import com.optic.Smartpillbox.Model.UsuarioApoyo;
import com.optic.Smartpillbox.R;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.btnGoToRegisterUser)
    Button mButtonGoToRegisterUser;

    @BindView(R.id.txtNom)
    TextInputEditText mTxtNom;
    @BindView(R.id.txtEmail)
    TextInputEditText mTxtEmail;
    @BindView(R.id.txtPassword)
    TextInputEditText mTxtPassword;
    @BindView(R.id.txtEdad)
    TextInputEditText mTxtEdad;
    @BindView(R.id.txtSerie)
    TextInputEditText mTxtSerie;
    @BindView(R.id.txtEnfermedad)
    TextInputEditText mTxtEnfermedad;
    @BindView(R.id.txtParentesco)
    TextInputEditText mTxtParentesco;
    @BindView(R.id.spinnerSexo)
    Spinner mSpinnerSexo;
    @BindView(R.id.checkSpan)
    CheckBox mCheckSpan;
    @BindView(R.id.checkInfo)
    CheckBox mCheckInfo;

    @BindView(R.id.lyEnfermedad)
    LinearLayout mLyEnfermedad;
    @BindView(R.id.lyParentesco)
    LinearLayout mLyParentesco;
    private FireStoreService service;

    /*private FirebaseAuth mAuth;
    FirebaseFirestore db;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        /*mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();*/
        service = new FireStoreService();
        Bundle bundle = getIntent().getExtras();
        setSupportActionBar(mToolbar);
        char tipoUsuario = bundle.getChar("tipo");
        switch (tipoUsuario){
            case 'u': getSupportActionBar().setTitle("Usuario");
            mLyParentesco.setVisibility(View.GONE);
            break;
            case 'a': getSupportActionBar().setTitle("Usuario de Apoyo");
            mLyEnfermedad.setVisibility(View.GONE);
            break;
            default: getSupportActionBar().setTitle("Unknown");
            break;
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String[] sexo = new String[3];
        sexo[0] = "Masculino";
        sexo[1] = "Femenino";
        sexo[2] = "Otro";
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, sexo);
        mSpinnerSexo.setAdapter(adapter);
        mButtonGoToRegisterUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarUsuario(tipoUsuario);
            }
        });
    }
    public void registrarUsuario(char tipoUsuario){
        switch (tipoUsuario){
            case 'u':
                Usuario usuario = new Usuario();
                usuario.setNom(mTxtNom.getText().toString());
                usuario.setEmail(mTxtEmail.getText().toString());
                usuario.setPassword(mTxtPassword.getText().toString());
                usuario.setEdad(Integer.parseInt(mTxtEdad.getText().toString()));
                usuario.setSerie(mTxtSerie.getText().toString());
                usuario.setEnfermedad(mTxtEnfermedad.getText().toString());
                usuario.setSexo(mSpinnerSexo.getSelectedItem().toString());
                usuario.setInfo(mCheckInfo.isChecked());
                usuario.setSpan(mCheckSpan.isChecked());

                Map<String,Object> usuarioMap = new HashMap();
                usuarioMap.put("nom",usuario.getNom());
                usuarioMap.put("email",usuario.getEmail());
                usuarioMap.put("password",usuario.getPassword());
                usuarioMap.put("edad",usuario.getEdad());
                usuarioMap.put("serie",usuario.getSerie());
                usuarioMap.put("enfermedad", usuario.getEnfermedad());
                usuarioMap.put("sexo",usuario.getSexo());
                usuarioMap.put("isInfo",usuario.getInfo());
                usuarioMap.put("isSpan",usuario.getSpan());
                //Toast.makeText(this,usuarioMap.toString(),Toast.LENGTH_LONG).show();
                //Lo que sigue es para la base de datos ....
                createUser(usuarioMap);
                break;
            case 'a':
                UsuarioApoyo usuarioApoyo = new UsuarioApoyo();
                usuarioApoyo.setNom(mTxtNom.getText().toString());
                usuarioApoyo.setEmail(mTxtEmail.getText().toString());
                usuarioApoyo.setPassword(mTxtPassword.getText().toString());
                usuarioApoyo.setEdad(Integer.parseInt(mTxtEdad.getText().toString()));
                usuarioApoyo.setSerie(mTxtSerie.getText().toString());
                usuarioApoyo.setParentesco(mTxtParentesco.getText().toString());
                usuarioApoyo.setSexo(mSpinnerSexo.getSelectedItem().toString());
                usuarioApoyo.setInfo(mCheckInfo.isChecked());
                usuarioApoyo.setSpan(mCheckSpan.isChecked());

                Map<String,Object> usuarioApoyoMap = new HashMap();
                usuarioApoyoMap.put("nom",usuarioApoyo.getNom());
                usuarioApoyoMap.put("email",usuarioApoyo.getEmail());
                usuarioApoyoMap.put("password",usuarioApoyo.getPassword());
                usuarioApoyoMap.put("edad",usuarioApoyo.getEdad());
                usuarioApoyoMap.put("serie",usuarioApoyo.getSerie());
                usuarioApoyoMap.put("parentesco", usuarioApoyo.getParentesco());
                usuarioApoyoMap.put("sexo",usuarioApoyo.getSexo());
                usuarioApoyoMap.put("isInfo",usuarioApoyo.getInfo());
                usuarioApoyoMap.put("isSpan",usuarioApoyo.getSpan());
                //Toast.makeText(this,usuarioApoyoMap.toString(),Toast.LENGTH_LONG).show();
                //Lo que sigue es para la base de datos ....
                createUser(usuarioApoyoMap);
                break;
            default: Toast.makeText(this,"Error en detectar el tipo de usuario",Toast.LENGTH_LONG).show();
                break;

        }
    }
    public void goToMenu(){
        Intent intent = new Intent(RegisterActivity.this, CredencialesActivity.class);
        finish();
        startActivity(intent);
    }
    public void createUser(Map<String, Object> userMap){
        //Toast.makeText(RegisterActivity.this,user.get("email").toString() + " " + user.get("password").toString(),Toast.LENGTH_LONG).show();
        service.mAuth.createUserWithEmailAndPassword(userMap.get("email").toString(), userMap.get("password").toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("regStatus", "createUserWithEmail:success");
                            FirebaseUser user = service.mAuth.getCurrentUser();
                            service.mAuth.signOut();
                            userMap.remove("email");
                            userMap.remove("password");
                            userMap.put("userId",user.getUid());
                            service.infoUsuario()
                                    .add(userMap)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Log.d("regStatus", "DocumentSnapshot added with ID: " + documentReference.getId());
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w("regStatus", "Error adding document", e);
                                        }
                                    });
                            goToMenu();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("regStatus", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
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
