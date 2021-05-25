package com.optic.Smartpillbox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.QuerySnapshot;
import com.optic.Smartpillbox.FirebaseService.FireStoreService;
import com.optic.Smartpillbox.LogIn.CredencialesActivity;
import com.optic.Smartpillbox.LogIn.ValidarPastillera;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuActivity extends AppCompatActivity {

    @BindView(R.id.btnSignout)
    Button mBtnSignOut;
    private FireStoreService service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        service = new FireStoreService();
        ButterKnife.bind(this);
        verficarPastillero(service.mAuth.getCurrentUser());
    }
    public void verficarPastillero(FirebaseUser user){
        service.perfil_usuarios().whereEqualTo("userId",user.getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    try {
                        Map<String, Object> userProfile = task.getResult().getDocuments().get(0).getData();
                        if(userProfile.get("serie").equals(null)){
                            Intent intent = new Intent(MenuActivity.this, ValidarPastillera.class);
                            startActivity(intent);
                            finishAffinity();
                        }else{
                            mBtnSignOut.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    service.mAuth.signOut();
                                    Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finishAffinity();
                                }
                            });
                        }
                    }catch (NullPointerException n){
                        Intent intent = new Intent(MenuActivity.this, ValidarPastillera.class);
                        startActivity(intent);
                        finishAffinity();
                    }
                }
            }
        });
    }

}