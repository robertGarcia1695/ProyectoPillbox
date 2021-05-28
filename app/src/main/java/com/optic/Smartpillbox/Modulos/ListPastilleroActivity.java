package com.optic.Smartpillbox.Modulos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import com.google.firebase.firestore.Query;
import com.optic.Smartpillbox.FirebaseService.FireStoreService;
import com.optic.Smartpillbox.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListPastilleroActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.listPastillera)
    RecyclerView mListPastillera;
    @BindView(R.id.btnAgregar)
    Button mBtnAgregar;

    FireStoreService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_pastillero);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Pastillero Virtual");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        service = new FireStoreService();
        Query query = service.lista_pastillera().whereEqualTo("userId",service.mAuth.getUid());
        mBtnAgregar.setOnClickListener(v-> {
            startActivity(new Intent(ListPastilleroActivity.this,RegisterPastilleroActivity.class));
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