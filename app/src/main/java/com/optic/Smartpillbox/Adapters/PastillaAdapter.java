package com.optic.Smartpillbox.Adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.optic.Smartpillbox.FirebaseService.FireStoreService;
import com.optic.Smartpillbox.Model.Pastilla;
import com.optic.Smartpillbox.Modulos.MenuActivity;
import com.optic.Smartpillbox.R;


public class PastillaAdapter extends FirestoreRecyclerAdapter<Pastilla, PastillaAdapter.PastillaHolder> {

    private FireStoreService service = new FireStoreService();
    public PastillaAdapter(@NonNull  FirestoreRecyclerOptions<Pastilla> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull  PastillaAdapter.PastillaHolder holder, int position, @NonNull Pastilla model) {
        holder.mTxtNomPastilla.setText(model.getNom());
        holder.mTxtCantidad.setText(model.getCantidad().toString());
        holder.mTxtHoraToma.setText(model.getHora());
        service.pastillero_rtdb().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Object isLunes = dataSnapshot.child("Lunes").getValue();
                Object isMartes = dataSnapshot.child("Martes").getValue();
                if(isLunes.toString().equals("true")){
                    holder.mTxtTomaLunes.setText("☹️");
                    holder.mTxtTomaLunes.setTextColor(Color.RED);
                    holder.mTxtTomaLunes.setTextSize(24);
                }else{
                    holder.mTxtTomaLunes.setText("\uD83D\uDE00");
                    holder.mTxtTomaLunes.setTextColor(Color.GREEN);
                    holder.mTxtTomaLunes.setTextSize(24);
                }
                if(isMartes.toString().equals("true")){
                    holder.mTxtTomaMartes.setText("☹️");
                    holder.mTxtTomaMartes.setTextColor(Color.RED);
                    holder.mTxtTomaMartes.setTextSize(24);
                }else{
                    holder.mTxtTomaMartes.setText("\uD83D\uDE00");
                    holder.mTxtTomaMartes.setTextColor(Color.GREEN);
                    holder.mTxtTomaMartes.setTextSize(24);
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
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
        TextView mTxtTomaLunes;
        TextView mTxtTomaMartes;
        TextView mTxtTomaMiercoles;
        TextView mTxtTomaJueves;
        TextView mTxtTomaViernes;
        TextView mTxtTomaSabado;
        TextView mTxtTomaDomingo;
        public PastillaHolder(@NonNull View itemView) {
            super(itemView);
            mTxtNomPastilla = itemView.findViewById(R.id.txtNomPastilla);
            mTxtCantidad = itemView.findViewById(R.id.txtCantidad);
            mTxtHoraToma = itemView.findViewById(R.id.txtHoraToma);
            mTxtTomaLunes = itemView.findViewById(R.id.txtTomaLunes);
            mTxtTomaMartes = itemView.findViewById(R.id.txtTomaMartes);
            mTxtTomaMiercoles = itemView.findViewById(R.id.txtTomaMiercoles);
            mTxtTomaJueves = itemView.findViewById(R.id.txtTomaJueves);
            mTxtTomaViernes = itemView.findViewById(R.id.txtTomaViernes);
            mTxtTomaSabado = itemView.findViewById(R.id.txtTomaSabado);
            mTxtTomaDomingo = itemView.findViewById(R.id.txtTomaDomingo);
        }
    }
}
