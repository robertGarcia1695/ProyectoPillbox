package com.optic.Smartpillbox.FirebaseService;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.util.Map;

public class FireStoreService {
    public FirebaseAuth mAuth;
    public FirebaseFirestore db;
    public FirebaseStorage fileStorage;

    public FireStoreService(){
        mAuth = FirebaseAuth.getInstance();
        db  = FirebaseFirestore.getInstance();
        fileStorage = FirebaseStorage.getInstance();
    }

    public CollectionReference infoUsuario(){
        CollectionReference dato = db.collection("infoUsuarios");
        return dato;
    }
}
