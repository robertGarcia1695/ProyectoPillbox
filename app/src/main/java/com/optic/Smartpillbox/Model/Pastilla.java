package com.optic.Smartpillbox.Model;

public class Pastilla {
    private String nom;
    private Integer cantidad;
    private String hora;
    private boolean isAlarma;
    private boolean isDiario;

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

    public boolean isAlarma() {
        return isAlarma;
    }

    public void setAlarma(boolean alarma) {
        isAlarma = alarma;
    }

    public boolean isDiario() {
        return isDiario;
    }

    public void setDiario(boolean diario) {
        isDiario = diario;
    }
}
