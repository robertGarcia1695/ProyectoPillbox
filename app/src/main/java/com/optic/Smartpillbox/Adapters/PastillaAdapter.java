package com.optic.Smartpillbox.Adapters;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
import com.optic.Smartpillbox.FirebaseService.FireStoreService;
import com.optic.Smartpillbox.Model.Pastilla;
import com.optic.Smartpillbox.Modulos.MenuActivity;
import com.optic.Smartpillbox.R;

import java.util.ArrayList;


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
        /*try {
            Toast.makeText(holder.itemView.getContext(), model.getDiasSemana().get(0) + "", Toast.LENGTH_LONG).show();
        }catch (NullPointerException e){

        }*/
        service.pastillero_rtdb().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Object isLunes = dataSnapshot.child("Lunes").getValue();
                Object isMartes = dataSnapshot.child("Martes").getValue();
                Object isMiercoles = dataSnapshot.child("Miercoles").getValue();
                Object isJueves = dataSnapshot.child("Jueves").getValue();
                Object isViernes = dataSnapshot.child("Viernes").getValue();
                Object isSabado = dataSnapshot.child("Sabado").getValue();
                Object isDomingo = dataSnapshot.child("Domingo").getValue();
                if(model.getDiasSemana().get(0)) {
                    if (isLunes.toString().equals("true")) {
                        holder.mTxtTomaLunes.setText("\uD83D\uDC8A");
                        holder.mTxtTomaLunes.setTextColor(Color.RED);
                        holder.mTxtTomaLunes.setTextSize(24);
                    } else {
                        holder.mTxtTomaLunes.setText("✔️");
                        holder.mTxtTomaLunes.setTextColor(Color.GREEN);
                        holder.mTxtTomaLunes.setTextSize(24);
                    }
                }else{
                    holder.mTxtTomaLunes.setText("-");
                    holder.mTxtTomaLunes.setTextColor(Color.BLACK);
                    holder.mTxtTomaLunes.setTextSize(24);
                }
                if(model.getDiasSemana().get(1)) {
                    if (isMartes.toString().equals("true")) {
                        holder.mTxtTomaMartes.setText("\uD83D\uDC8A");
                        holder.mTxtTomaMartes.setTextColor(Color.RED);
                        holder.mTxtTomaMartes.setTextSize(24);
                    } else {
                        holder.mTxtTomaMartes.setText("✔️");
                        holder.mTxtTomaMartes.setTextColor(Color.GREEN);
                        holder.mTxtTomaMartes.setTextSize(24);
                    }
                }else{
                    holder.mTxtTomaMartes.setText("-");
                    holder.mTxtTomaMartes.setTextColor(Color.BLACK);
                    holder.mTxtTomaMartes.setTextSize(24);
                }
                if(model.getDiasSemana().get(2)) {
                    if (isMiercoles.toString().equals("true")) {
                        holder.mTxtTomaMiercoles.setText("\uD83D\uDC8A");
                        holder.mTxtTomaMiercoles.setTextColor(Color.RED);
                        holder.mTxtTomaMiercoles.setTextSize(24);
                    } else {
                        holder.mTxtTomaMiercoles.setText("✔️");
                        holder.mTxtTomaMiercoles.setTextColor(Color.GREEN);
                        holder.mTxtTomaMiercoles.setTextSize(24);
                    }
                }else{
                    holder.mTxtTomaMiercoles.setText("-");
                    holder.mTxtTomaMiercoles.setTextColor(Color.BLACK);
                    holder.mTxtTomaMiercoles.setTextSize(24);
                }
                if(model.getDiasSemana().get(3)) {
                    if (isJueves.toString().equals("true")) {
                        holder.mTxtTomaJueves.setText("\uD83D\uDC8A");
                        holder.mTxtTomaJueves.setTextColor(Color.RED);
                        holder.mTxtTomaJueves.setTextSize(24);
                    } else {
                        holder.mTxtTomaJueves.setText("✔️");
                        holder.mTxtTomaJueves.setTextColor(Color.GREEN);
                        holder.mTxtTomaJueves.setTextSize(24);
                    }
                }else{
                    holder.mTxtTomaJueves.setText("-");
                    holder.mTxtTomaJueves.setTextColor(Color.BLACK);
                    holder.mTxtTomaJueves.setTextSize(24);
                }
                if(model.getDiasSemana().get(4)) {
                    if (isViernes.toString().equals("true")) {
                        holder.mTxtTomaViernes.setText("\uD83D\uDC8A");
                        holder.mTxtTomaViernes.setTextColor(Color.RED);
                        holder.mTxtTomaViernes.setTextSize(24);
                    } else {
                        holder.mTxtTomaViernes.setText("✔️");
                        holder.mTxtTomaViernes.setTextColor(Color.GREEN);
                        holder.mTxtTomaViernes.setTextSize(24);
                    }
                }else{
                    holder.mTxtTomaViernes.setText("-");
                    holder.mTxtTomaViernes.setTextColor(Color.BLACK);
                    holder.mTxtTomaViernes.setTextSize(24);
                }
                if(model.getDiasSemana().get(5)) {
                    if (isSabado.toString().equals("true")) {
                        holder.mTxtTomaSabado.setText("\uD83D\uDC8A");
                        holder.mTxtTomaSabado.setTextColor(Color.RED);
                        holder.mTxtTomaSabado.setTextSize(24);
                    } else {
                        holder.mTxtTomaSabado.setText("✔️");
                        holder.mTxtTomaSabado.setTextColor(Color.GREEN);
                        holder.mTxtTomaSabado.setTextSize(24);
                    }
                }else{
                    holder.mTxtTomaSabado.setText("-");
                    holder.mTxtTomaSabado.setTextColor(Color.BLACK);
                    holder.mTxtTomaSabado.setTextSize(24);
                }
                if(model.getDiasSemana().get(6)) {
                    if (isDomingo.toString().equals("true")) {
                        holder.mTxtTomaDomingo.setText("\uD83D\uDC8A");
                        holder.mTxtTomaDomingo.setTextColor(Color.RED);
                        holder.mTxtTomaDomingo.setTextSize(24);
                    } else {
                        holder.mTxtTomaDomingo.setText("✔️");
                        holder.mTxtTomaDomingo.setTextColor(Color.GREEN);
                        holder.mTxtTomaDomingo.setTextSize(24);
                    }
                }else{
                    holder.mTxtTomaDomingo.setText("-");
                    holder.mTxtTomaDomingo.setTextColor(Color.BLACK);
                    holder.mTxtTomaDomingo.setTextSize(24);
                }
                int cantidadPastillas = 0;
                int cantidadPastillasMaxima = 0;
                ArrayList<Boolean> diasSemanaIoT = new ArrayList<>();
                diasSemanaIoT.add(Boolean.parseBoolean(isDomingo.toString()));
                diasSemanaIoT.add(Boolean.parseBoolean(isLunes.toString()));
                diasSemanaIoT.add(Boolean.parseBoolean(isMartes.toString()));
                diasSemanaIoT.add(Boolean.parseBoolean(isMiercoles.toString()));
                diasSemanaIoT.add(Boolean.parseBoolean(isJueves.toString()));
                diasSemanaIoT.add(Boolean.parseBoolean(isViernes.toString()));
                diasSemanaIoT.add(Boolean.parseBoolean(isSabado.toString()));
                for(int i = 0; i < model.getDiasSemana().size(); i++){
                    if(model.getDiasSemana().get(i)){
                        if(diasSemanaIoT.get(i)){
                            cantidadPastillas+= model.getCantidad();
                        }
                        cantidadPastillasMaxima+=model.getCantidad();
                    }
                }
                holder.mTxtCantidad.setText( cantidadPastillas + "/" + cantidadPastillasMaxima);
            }
            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
        holder.mBtnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

                builder.setTitle("Confirm");
                builder.setMessage("¿Deseas eliminar esta alarma?");
                builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        service.pastillero_virtual().get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    service.pastillero_virtual().document(task.getResult().getDocuments().get(position).getId()).delete();
                                }
                            }
                        });

                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
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
        Button mBtnEliminar;
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
            mBtnEliminar = itemView.findViewById(R.id.btnEliminar);
        }
    }
}
