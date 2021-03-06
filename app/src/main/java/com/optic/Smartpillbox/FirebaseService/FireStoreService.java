package com.optic.Smartpillbox.FirebaseService;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.util.Map;

public class FireStoreService {
    public FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private FirebaseStorage fileStorage;
    private FirebaseDatabase rtdb;

    public FireStoreService(){
        mAuth = FirebaseAuth.getInstance();
        db  = FirebaseFirestore.getInstance();
        fileStorage = FirebaseStorage.getInstance();
        rtdb = FirebaseDatabase.getInstance();
    }
    public CollectionReference lista_cardio(){
        CollectionReference dato = db.collection("LISTA_CARDIO");
        return dato;
    }
    public CollectionReference lista_pastillas(){
        CollectionReference dato = db.collection("LISTA_PASTILLAS");
        return dato;
    }
    public CollectionReference perfil_usuarios(){
        CollectionReference dato = db.collection("PERFIL_USUARIOS");
        return dato;
    }
    public CollectionReference num_serie_pastilleras(){
        CollectionReference dato = db.collection("NUM_SERIE_PASTILLERAS");
        return dato;
    }
    public CollectionReference pastillero_virtual(){
        CollectionReference dato = db.collection("PASTILLERO_VIRTUAL");
        return dato;
    }
    public CollectionReference reporte_covid(){
        CollectionReference dato = db.collection("REPORTE_COVID");
        return dato;
    }
    public DatabaseReference pastillero_rtdb(){
        DatabaseReference dato = rtdb.getReference("Pastillero");
        return dato;
    }
}
