package com.optic.Smartpillbox.Modulos;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;
import com.optic.Smartpillbox.FirebaseService.FireStoreService;
import com.optic.Smartpillbox.Model.Usuario;
import com.optic.Smartpillbox.Model.UsuarioApoyo;
import com.optic.Smartpillbox.R;
import androidx.appcompat.widget.Toolbar;


import butterknife.BindView;
import butterknife.ButterKnife;

public class PerfilActivity extends AppCompatActivity {

    @BindView(R.id.txtNom)
    TextView mTxtNom;
    @BindView(R.id.txtEmail)
    TextView mTxtEmail;
    @BindView(R.id.txtEdad)
    TextView mTxtEdad;
    @BindView(R.id.lblTipoUsuario)
    TextView mLblTipoUsuario;
    @BindView(R.id.txtDesTipUsuario)
    TextView mTxtDesTipUsuario;
    @BindView(R.id.txtPersonasAsociadas)
    TextView mTxtPersonasAsociadas;
    @BindView(R.id.btnGoToActualizarPerfil)
    Button mBtnGoToActualizarPerfil;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.imgPerfil)
    ImageView mImgPerfil;

    private FireStoreService service;
    private FirebaseUser user;
    private ListenerRegistration eventPerfil;
    private ListenerRegistration eventAsociados;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        ButterKnife.bind(this);
        service = new FireStoreService();
        user = service.mAuth.getCurrentUser();
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Perfil");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        eventPerfil = service.perfil_usuarios().whereEqualTo("userId",user.getUid()).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value.getDocuments().get(0).getString("parentesco") == null){
                    Usuario doc = value.getDocuments().get(0).toObject(Usuario.class);
                    llenarCamposPerfil(doc,value.getDocuments().get(0).getId());
                }else if(value.getDocuments().get(0).getString("enfermedad") == null){
                    UsuarioApoyo doc = value.getDocuments().get(0).toObject(UsuarioApoyo.class);
                    llenarCamposPerfil(doc,value.getDocuments().get(0).getId());
                }
                eventAsociados = service.perfil_usuarios().whereEqualTo("serie", value.getDocuments().get(0).getString("serie")).addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        for(DocumentSnapshot d: value.getDocuments()){
                            if(!d.getString("userId").equals(service.mAuth.getUid())) {
                                mTxtPersonasAsociadas.append(d.getString("nom") + "\n");
                            }
                        }
                    }
                });
            }
        });
    }
    public void llenarCamposPerfil(Object usuarioInfo, String id){
        try {
            Usuario usuario = (Usuario) usuarioInfo;
            mTxtNom.setText(usuario.getNom());
            mTxtEmail.setText(user.getEmail());
            mTxtEdad.setText(usuario.getEdad().toString());
            mLblTipoUsuario.setText("Enfermedad: ");
            mTxtDesTipUsuario.setText(usuario.getEnfermedad());
            mImgPerfil.setImageDrawable(getResources().getDrawable(R.drawable.fotoperfil));
            mBtnGoToActualizarPerfil.setOnClickListener(v ->  {
                Intent intent = new Intent(PerfilActivity.this, ActualizarPerfilActivity.class)
                        .putExtra("id",id);
                startActivity(intent);
                finish();
            });
        }catch (ClassCastException c){
            UsuarioApoyo usuario = (UsuarioApoyo) usuarioInfo;
            mTxtNom.setText(usuario.getNom());
            mTxtEmail.setText(user.getEmail());
            mTxtEdad.setText(usuario.getEdad().toString());
            mLblTipoUsuario.setText("Parentesco: ");
            mTxtDesTipUsuario.setText(usuario.getParentesco());
            mImgPerfil.setImageDrawable(getResources().getDrawable(R.drawable.apoyo234));
            mBtnGoToActualizarPerfil.setOnClickListener(v ->  {
                Intent intent = new Intent(PerfilActivity.this, ActualizarPerfilActivity.class)
                        .putExtra("id",id);
                startActivity(intent);
                finish();
            });
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        eventPerfil.remove();
        eventAsociados.remove();
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