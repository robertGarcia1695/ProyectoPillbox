package com.optic.Smartpillbox.Model;

import com.google.firebase.firestore.DocumentSnapshot;

import javax.annotation.Nullable;

public class Usuario{
    private String nom;
    private String email;
    private String password;
    @Nullable
    private Integer edad;
    private String serie;
    private String enfermedad;
    private String sexo;
    private Boolean isSpan;
    private Boolean isInfo;

    public Boolean getSpan() {
        return isSpan;
    }

    public void setSpan(Boolean span) {
        isSpan = span;
    }

    public Boolean getInfo() {
        return isInfo;
    }

    public void setInfo(Boolean info) {
        isInfo = info;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getEnfermedad() {
        return enfermedad;
    }

    public void setEnfermedad(String enfermedad) {
        this.enfermedad = enfermedad;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
}
