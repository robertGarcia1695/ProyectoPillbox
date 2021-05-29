package com.optic.Smartpillbox.Modulos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;
import com.optic.Smartpillbox.Adapters.PastillaAdapter;
import com.optic.Smartpillbox.FirebaseService.FireStoreService;
import com.optic.Smartpillbox.Model.Pastilla;
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

    private FireStoreService service;
    private PastillaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_pastillero);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Pastillero Virtual");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        service = new FireStoreService();
        Query query = service.pastillero_virtual().whereEqualTo("userId",service.mAuth.getUid());
        FirestoreRecyclerOptions<Pastilla> options = new FirestoreRecyclerOptions.Builder<Pastilla>()
                .setQuery(query,Pastilla.class).build();
        adapter = new PastillaAdapter(options);
        mListPastillera.setHasFixedSize(true);
        mListPastillera.setLayoutManager(new LinearLayoutManager(this));
        mListPastillera.setAdapter(adapter);
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
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}