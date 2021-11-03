package com.optic.Smartpillbox.Model;

import javax.annotation.Nullable;

public class UsuarioApoyo {
    private String nom;
    private String email;
    private String password;
    @Nullable
    private String edad;
    private String serie;
    private String parentesco;
    private String sexo;
    private Boolean isSpan;
    private Boolean isInfo;
    private String dni;

    @Nullable
    public String getEdad() {
        return edad;
    }

    public void setEdad(@Nullable String edad) {
        this.edad = edad;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

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


    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
}
