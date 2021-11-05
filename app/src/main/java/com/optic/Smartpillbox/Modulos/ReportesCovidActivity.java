package com.optic.Smartpillbox.Modulos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;
import com.optic.Smartpillbox.Adapters.PastillaAdapter;
import com.optic.Smartpillbox.Adapters.ReportesCovidAdapter;
import com.optic.Smartpillbox.FirebaseService.FireStoreService;
import com.optic.Smartpillbox.Model.Pastilla;
import com.optic.Smartpillbox.Model.ReportesCovid;
import com.optic.Smartpillbox.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReportesCovidActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.listReporteCovid)
    RecyclerView mListReporteCovid;
    private FireStoreService service;
    private ReportesCovidAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportes_covid);
        Bundle bundle = getIntent().getExtras();
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Lista de reportes COVID-19");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        service = new FireStoreService();

        Query query = service.reporte_covid().whereEqualTo("serie",bundle.getString("serie")).orderBy("horaReg", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<ReportesCovid> options = new FirestoreRecyclerOptions.Builder<ReportesCovid>()
                .setQuery(query, ReportesCovid.class).build();
        adapter = new ReportesCovidAdapter(options);
        mListReporteCovid.setHasFixedSize(true);
        mListReporteCovid.setLayoutManager(new LinearLayoutManager(this));
        mListReporteCovid.setAdapter(adapter);
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
