package com.example.myapplication;

public class ProduitCalendrier {
    private Produit produit;
    private Calendrier Calendrier;

    public ProduitCalendrier(Produit produit, Calendrier idCalendrier) {
        this.produit = produit;
        this.Calendrier = idCalendrier;
    }

    public Produit getProduit() {
        return produit;
    }

    public Calendrier getIdCalendrier() {
        return Calendrier;
    }
}
