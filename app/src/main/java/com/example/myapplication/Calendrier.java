package com.example.myapplication;

import java.io.Serializable;

public class Calendrier implements Serializable {

    private String date;
    private long idcalendrier;
    private String repas;
    private long id;
    private double valeur;

    public Calendrier(long id, long idcalendrier, String date,String repas,double valeur){
        this.date = date;
        this.idcalendrier = idcalendrier;
        this.id = id;
        this.repas = repas;
        this.valeur = valeur;
    }

    public Calendrier(String date,String repas){
        this.date = date;
        this.repas = repas;
    }


    public Calendrier(){}

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRepas() {
        return repas;
    }

    public void setRepas(String repas) {
        this.repas = repas;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getValeur() {
        return valeur;
    }

    public void setValeur(double valeur) {
        this.valeur = valeur;
    }

    public long getIdcalendrier() {
        return idcalendrier;
    }

    public void setIdcalendrier(long idcalendrier) {
        this.idcalendrier = idcalendrier;
    }
}
