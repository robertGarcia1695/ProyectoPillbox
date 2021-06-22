package com.optic.Smartpillbox.Model;


import java.util.ArrayList;

public class Pastilla {
    private String nom;
    private Integer cantidad;
    private Integer cantidadTotal;
    private String hora;
    private ArrayList<Boolean> diasSemana;

    public ArrayList<Boolean> getDiasSemana() {
        return diasSemana;
    }

    public void setDiasSemana(ArrayList<Boolean> diasSemana) {
        this.diasSemana = diasSemana;
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

    public Integer getCantidadTotal() {
        return cantidadTotal;
    }

    public void setCantidadTotal(Integer cantidadTotal) {
        this.cantidadTotal = cantidadTotal;
    }
}
