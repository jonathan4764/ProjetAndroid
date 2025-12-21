package com.example.myapplication;

import java.io.Serializable;

public class ProduitNutriment implements Serializable {

    private String valeur;



    public ProduitNutriment(String valeur){
        this.valeur = valeur;
    }


    public String getNom() {
        return valeur;
    }

    public void setNom(String nom) {
        this.valeur = nom;
    }


}
