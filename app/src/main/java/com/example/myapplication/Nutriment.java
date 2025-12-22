package com.example.myapplication;

public class Nutriment {
    private String nom;
    private String valeur;

    public Nutriment(String nom, String valeur) {
        this.nom = nom;
        this.valeur = valeur;
    }

    public String getNom() { return nom; }
    public String getValeur() { return valeur; }
}
