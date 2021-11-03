package com.optic.Smartpillbox.Modulos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.optic.Smartpillbox.R;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetalleReporteCovidActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.txtPeso)
    TextView mTxtPeso;
    @BindView(R.id.txtPresion)
    TextView mTxtPresion;
    @BindView(R.id.txtTemp)
    TextView mTxtTemp;

    @BindView(R.id.chk1)
    TextView mChk1;
    @BindView(R.id.chk2)
    TextView mChk2;
    @BindView(R.id.chk3)
    TextView mChk3;
    @BindView(R.id.chk4)
    TextView mChk4;
    @BindView(R.id.chk5)
    TextView mChk5;
    @BindView(R.id.chk6)
    TextView mChk6;
    @BindView(R.id.chk7)
    TextView mChk7;

    @BindView(R.id.txtOx1)
    TextView mTxtOx1;
    @BindView(R.id.txtOx2)
    TextView mTxtOx2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_reporte_covid);
        Bundle bundle = getIntent().getExtras();
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        try {
            JSONObject json =  new JSONObject(bundle.getString("model"));
            getSupportActionBar().setTitle("Reporte del " + json.getString("horaReg"));
            mTxtPeso.append(" " + json.getString("peso"));
            mTxtPresion.append(" " + json.getString("presion"));
            mTxtTemp.append(" " + json.getString("temp"));
            mTxtOx1.append(" " + json.getString("ox1"));
            mTxtOx2.append(" " + json.getString("ox2"));
            if(json.getBoolean("chk1")){
                mChk1.setText("Si");
            }else{
                mChk1.setText("No");
            }
            if(json.getBoolean("chk2")){
                mChk2.setText("Si");
            }else{
                mChk2.setText("No");
            }
            if(json.getBoolean("chk3")){
                mChk3.setText("Si");
            }else{
                mChk3.setText("No");
            }
            if(json.getBoolean("chk4")){
                mChk4.setText("Si");
            }else{
                mChk4.setText("No");
            }
            if(json.getBoolean("chk5")){
                mChk5.setText("Si");
            }else{
                mChk5.setText("No");
            }
            if(json.getBoolean("chk6")){
                mChk6.setText("Si");
            }else{
                mChk6.setText("No");
            }
            if(json.getBoolean("chk7")){
                mChk7.setText("Si");
            }else{
                mChk7.setText("No");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
