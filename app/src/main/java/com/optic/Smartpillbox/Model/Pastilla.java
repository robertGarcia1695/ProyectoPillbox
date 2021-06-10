package com.optic.Smartpillbox.Model;

public class Pastilla {
    private String nom;
    private Integer cantidad;
    private String hora;
    private boolean isLunes;
    private boolean isMartes;
    private boolean isMiercoles;
    private boolean isJueves;
    private boolean isViernes;
    private boolean isSabado;
    private boolean isDomingo;

    public boolean isLunes() {
        return isLunes;
    }

    public void setLunes(boolean lunes) {
        isLunes = lunes;
    }

    public boolean isMartes() {
        return isMartes;
    }

    public void setMartes(boolean martes) {
        isMartes = martes;
    }

    public boolean isMiercoles() {
        return isMiercoles;
    }

    public void setMiercoles(boolean miercoles) {
        isMiercoles = miercoles;
    }

    public boolean isJueves() {
        return isJueves;
    }

    public void setJueves(boolean jueves) {
        isJueves = jueves;
    }

    public boolean isViernes() {
        return isViernes;
    }

    public void setViernes(boolean viernes) {
        isViernes = viernes;
    }

    public boolean isSabado() {
        return isSabado;
    }

    public void setSabado(boolean sabado) {
        isSabado = sabado;
    }

    public boolean isDomingo() {
        return isDomingo;
    }

    public void setDomingo(boolean domingo) {
        isDomingo = domingo;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

}
