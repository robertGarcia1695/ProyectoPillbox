package com.optic.Smartpillbox.FirebaseService;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.util.Map;

public class FireStoreService {
    public FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private FirebaseStorage fileStorage;

    public FireStoreService(){
        mAuth = FirebaseAuth.getInstance();
        db  = FirebaseFirestore.getInstance();
        fileStorage = FirebaseStorage.getInstance();
    }

    public CollectionReference perfil_usuarios(){
        CollectionReference dato = db.collection("PERFIL_USUARIOS");
        return dato;
    }
    public CollectionReference num_serie_pastilleras(){
        CollectionReference dato = db.collection("NUM_SERIE_PASTILLERAS");
        return dato;
    }
    public CollectionReference lista_pastillera(){
        CollectionReference dato = db.collection("LISTA_PASTILLERA");
        return dato;
    }

}
