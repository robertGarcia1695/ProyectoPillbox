package com.optic.Smartpillbox.LogIn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
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
                goToOpcion();
                finish();
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
                Toast.makeText(this,usuarioMap.toString(),Toast.LENGTH_LONG).show();
                //Lo que sigue es para la base de datos ....
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
                Toast.makeText(this,usuarioApoyoMap.toString(),Toast.LENGTH_LONG).show();
                //Lo que sigue es para la base de datos ....
                break;
            default: Toast.makeText(this,"Error en detectar el tipo de usuario",Toast.LENGTH_LONG).show();
                break;

        }
    }
    public void goToOpcion(){
        Intent intent = new Intent(RegisterActivity.this, CredencialesActivity.class);
        startActivity(intent);
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
