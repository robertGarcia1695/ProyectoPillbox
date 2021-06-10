package com.optic.Smartpillbox.LogIn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.ConnectivityManager;
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
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.optic.Smartpillbox.FirebaseService.FireStoreService;
import com.optic.Smartpillbox.Model.Enfermedad;
import com.optic.Smartpillbox.Model.Usuario;
import com.optic.Smartpillbox.Model.UsuarioApoyo;
import com.optic.Smartpillbox.R;
import com.optic.Smartpillbox.Services.CheckNetworkStatus;
import com.optic.Smartpillbox.Services.InternetServiceActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    @BindView(R.id.txtPassword2)
    TextInputEditText mTxtPassword2;
    @BindView(R.id.txtEdad)
    TextInputEditText mTxtEdad;
    @BindView(R.id.spinnerEnfermedad)
    Spinner mSpinnerEnfermedad;
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
    private CheckNetworkStatus networkStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        networkStatus = new CheckNetworkStatus((ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE));
        ButterKnife.bind(this);
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
        service.lista_cardio().get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    List<Enfermedad> enfermedads = new ArrayList<>();
                    for(DocumentSnapshot e: task.getResult().getDocuments()){
                        enfermedads.add(e.toObject(Enfermedad.class));
                    }
                    String[] enfNom = new String[enfermedads.size()];
                    for(int i = 0; i < enfNom.length; i++){
                        enfNom[i] = enfermedads.get(i).getNombre();
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(RegisterActivity.this,android.R.layout.simple_spinner_item, enfNom);
                    mSpinnerEnfermedad.setAdapter(adapter);
                    String[] sexo = new String[3];
                    sexo[0] = "Masculino";
                    sexo[1] = "Femenino";
                    sexo[2] = "Otro";
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(RegisterActivity.this,android.R.layout.simple_spinner_item, sexo);
                    mSpinnerSexo.setAdapter(adapter2);
                    mButtonGoToRegisterUser.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(networkStatus.verifyConnectivity()){
                                registrarUsuario(tipoUsuario);
                            }else{
                                Toast.makeText(RegisterActivity.this,"No esta conectado al internet. Verfique su conexión.",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }
    public void registrarUsuario(char tipoUsuario){
        Map<String,Object> userInfo = verficarTipoDeUsuario(tipoUsuario);
        if(validarDatos(tipoUsuario)) {
            createUser(userInfo);
        }
    }
    public Map<String,Object> verficarTipoDeUsuario(char tipoUsuario){
        switch (tipoUsuario){
            case 'u':
                Usuario usuario = new Usuario();
                usuario.setNom(mTxtNom.getText().toString());
                usuario.setEmail(mTxtEmail.getText().toString());
                usuario.setPassword(mTxtPassword.getText().toString());
                try {
                    usuario.setEdad(Integer.parseInt(mTxtEdad.getText().toString()));
                }catch (NumberFormatException n){
                    usuario.setEdad(null);
                }
                usuario.setEnfermedad(mSpinnerEnfermedad.getSelectedItem().toString());
                usuario.setSexo(mSpinnerSexo.getSelectedItem().toString());
                usuario.setInfo(mCheckInfo.isChecked());
                usuario.setSpan(mCheckSpan.isChecked());

                Map<String,Object> usuarioMap = new HashMap();
                usuarioMap.put("nom",usuario.getNom());
                usuarioMap.put("email",usuario.getEmail());
                usuarioMap.put("password",usuario.getPassword());
                usuarioMap.put("edad",usuario.getEdad());
                usuarioMap.put("enfermedad", usuario.getEnfermedad());
                usuarioMap.put("sexo",usuario.getSexo());
                usuarioMap.put("isInfo",usuario.getInfo());
                usuarioMap.put("isSpan",usuario.getSpan());
                //Toast.makeText(this,usuarioMap.toString(),Toast.LENGTH_LONG).show();
                //Lo que sigue es para la base de datos ....
                /*if(validarDatos(tipoUsuario)) {
                    createUser(usuarioMap);
                }*/
                return usuarioMap;
                //break;
            case 'a':
                UsuarioApoyo usuarioApoyo = new UsuarioApoyo();
                usuarioApoyo.setNom(mTxtNom.getText().toString());
                usuarioApoyo.setEmail(mTxtEmail.getText().toString());
                usuarioApoyo.setPassword(mTxtPassword.getText().toString());
                try {
                    usuarioApoyo.setEdad(Integer.parseInt(mTxtEdad.getText().toString()));
                }catch (NumberFormatException n){
                    usuarioApoyo.setEdad(null);
                }
                usuarioApoyo.setParentesco(mTxtParentesco.getText().toString());
                usuarioApoyo.setSexo(mSpinnerSexo.getSelectedItem().toString());
                usuarioApoyo.setInfo(mCheckInfo.isChecked());
                usuarioApoyo.setSpan(mCheckSpan.isChecked());

                Map<String,Object> usuarioApoyoMap = new HashMap();
                usuarioApoyoMap.put("nom",usuarioApoyo.getNom());
                usuarioApoyoMap.put("email",usuarioApoyo.getEmail());
                usuarioApoyoMap.put("password",usuarioApoyo.getPassword());
                usuarioApoyoMap.put("edad",usuarioApoyo.getEdad());
                usuarioApoyoMap.put("parentesco", usuarioApoyo.getParentesco());
                usuarioApoyoMap.put("sexo",usuarioApoyo.getSexo());
                usuarioApoyoMap.put("isInfo",usuarioApoyo.getInfo());
                usuarioApoyoMap.put("isSpan",usuarioApoyo.getSpan());
                //Toast.makeText(this,usuarioApoyoMap.toString(),Toast.LENGTH_LONG).show();
                //Lo que sigue es para la base de datos ....
                /*if(validarDatos(tipoUsuario)) {
                    createUser(usuarioApoyoMap);
                }*/
                return usuarioApoyoMap;
                //break;
            default: Toast.makeText(RegisterActivity.this,"Error en detectar el tipo de usuario",Toast.LENGTH_LONG).show();
            return null;
            //break;

        }
    }
    public void goToLogin(){
        Intent intent = new Intent(RegisterActivity.this, ValidarPastilleraActivity.class);
        startActivity(intent);
        finishAffinity();
    }
    public void createUser(Map<String, Object> userMap){
        //Toast.makeText(RegisterActivity.this,user.get("email").toString() + " " + user.get("password").toString(),Toast.LENGTH_LONG).show();
        service.mAuth.createUserWithEmailAndPassword(userMap.get("email").toString(), userMap.get("password").toString())
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("regStatus", "createUserWithEmail:success");
                            FirebaseUser user = service.mAuth.getCurrentUser();
                            userMap.remove("email");
                            userMap.remove("password");
                            userMap.put("userId",user.getUid());
                            service.perfil_usuarios().add(userMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    if(task.isSuccessful()){
                                        goToLogin();
                                    }else{
                                        user.delete();
                                    }
                                }
                            });
                        } else {
                            Log.w("regStatus", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed." + task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                            if(task.getException().getMessage().equals("The email address is already in use by another account.")){
                                mTxtEmail.setError("El correo ya esta usado.");
                            }
                        }
                    }
                });
    }
    public boolean validarDatos(char tipoUsuario){
        Boolean[] validacion = new Boolean[5];
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
        if(mTxtPassword.getText().toString().length() <= 6){
            mTxtPassword.setError("Su contraseña debe ser major de 6 carateres");
        }else{
            validacion[2] = true;
        }
        if(!mTxtPassword.getText().toString().equals(mTxtPassword2.getText().toString())){
            mTxtPassword.setError("La contraseña no coinsiden.");
            mTxtPassword2.setError("La contraseña no coinsiden.");
        }
        else{
            validacion[2] = true;
        }
        if(mTxtEdad.getText().toString().equals("")){
            mTxtEdad.setError("Debe ingresar su edad.");
        }else{
            validacion[3] = true;
        }
        switch (tipoUsuario){
            case 'u':
                if(mSpinnerEnfermedad.getSelectedItem().toString().equals("")){
                    //mTxtEnfermedad.setError("Debe ingresar la enfermedad que tiene.");
                }else{
                    validacion[4] = true;
                }
                break;
            case 'a':
                if(mTxtParentesco.getText().toString().equals("")){
                    mTxtParentesco.setError("Debe indicar a su parentesco.");
                }else{
                    validacion[4] = true;
                }
                break;
            default: break;
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
    public void onStart() {
        super.onStart();
        if (networkStatus.verifyConnectivity()) {

        } else if (!networkStatus.verifyConnectivity()) {
            startActivity(new Intent(this, InternetServiceActivity.class));
        }
    }
}
