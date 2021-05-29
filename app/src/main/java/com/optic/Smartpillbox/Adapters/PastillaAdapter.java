package com.optic.Smartpillbox.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.textfield.TextInputEditText;
import com.optic.Smartpillbox.Model.Pastilla;
import com.optic.Smartpillbox.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PastillaAdapter extends FirestoreRecyclerAdapter<Pastilla, PastillaAdapter.PastillaHolder> {

    public PastillaAdapter(@NonNull  FirestoreRecyclerOptions<Pastilla> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull  PastillaAdapter.PastillaHolder holder, int position, @NonNull Pastilla model) {
        holder.mTxtNomPastilla.setText(model.getNom());
        holder.mTxtCantidad.setText(model.getCantidad().toString());
        holder.mTxtHoraToma.setText(model.getHora());
    }

    @NonNull
    @Override
    public PastillaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_pastillero,parent,false);
        return new PastillaHolder(v);
    }

    class PastillaHolder extends RecyclerView.ViewHolder{
        TextView mTxtNomPastilla;
        TextView mTxtCantidad;
        TextView mTxtHoraToma;
        TextView mTxtToma;
        public PastillaHolder(@NonNull View itemView) {
            super(itemView);
            mTxtNomPastilla = itemView.findViewById(R.id.txtNomPastilla);
            mTxtCantidad = itemView.findViewById(R.id.txtCantidad);
            mTxtHoraToma = itemView.findViewById(R.id.txtHoraToma);
            mTxtToma= itemView.findViewById(R.id.txtToma);
        }
    }
}
