package com.optic.Smartpillbox.Adapters;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.optic.Smartpillbox.FirebaseService.FireStoreService;
import com.optic.Smartpillbox.Model.Pastilla;
import com.optic.Smartpillbox.Model.ReportesCovid;
import com.optic.Smartpillbox.Modulos.DetalleReporteCovidActivity;
import com.optic.Smartpillbox.R;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;


public class ReportesCovidAdapter extends FirestoreRecyclerAdapter<ReportesCovid, ReportesCovidAdapter.ReportesCovidHolder> {
    public ReportesCovidAdapter(@NonNull  FirestoreRecyclerOptions<ReportesCovid> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull  ReportesCovidAdapter.ReportesCovidHolder holder, int position, @NonNull ReportesCovid model) {
        holder.mTxtPeso.setText("Peso: " + model.getPeso());
        holder.mTxtPresion.setText("Presión: " + model.getPresion());
        holder.mTxtTemp.setText("Temperatura: " + model.getTemp());
        holder.mTxtFecReg.setText("Hora de Registro: " + model.getHoraReg());
        Gson gson = new Gson();
        holder.mBtnDetalleCovid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), DetalleReporteCovidActivity.class)
                        .putExtra("model", gson.toJson(model));
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public ReportesCovidHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_reportes_covid,parent,false);
        return new ReportesCovidHolder(v);
    }

    class ReportesCovidHolder extends RecyclerView.ViewHolder{
        TextView mTxtPeso;
        TextView mTxtPresion;
        TextView mTxtTemp;
        TextView mTxtFecReg;
        Button mBtnDetalleCovid;
        public ReportesCovidHolder(@NonNull View itemView) {
            super(itemView);
            mTxtPeso = itemView.findViewById(R.id.txtPeso);
            mTxtPresion = itemView.findViewById(R.id.txtPresion);
            mTxtTemp = itemView.findViewById(R.id.txtTemp);
            mTxtFecReg = itemView.findViewById(R.id.txtFecReg);
            mBtnDetalleCovid = itemView.findViewById(R.id.btnDetalleCovid);
        }
    }
}
