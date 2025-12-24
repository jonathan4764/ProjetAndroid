package com.example.myapplication;

import java.util.HashMap;
import java.util.Map;

public class CalculBesoin {

    private Utilisateur utilisateur;
    private double mb;
    private double bet;


    public CalculBesoin(Utilisateur utilistaeur){
        this.utilisateur = utilistaeur;
        this.mb = calculMB(utilisateur);
        this.bet = calculFacteurActivite(utilisateur);
    }


    public double calculMB(Utilisateur utilisateur){

        if(utilisateur.getSexe().equals("Homme")){
            mb = 10*utilisateur.getPoids() + 6.25 * utilisateur.getTaille() - 5*utilisateur.getAge()+5;
        }else{
            mb = 10*utilisateur.getPoids() + 6.25 * utilisateur.getTaille() - 5*utilisateur.getAge() - 161;;
        }
        return mb;
    }

    public double calculFacteurActivite(Utilisateur utilisateur){
        switch (utilisateur.getActivite()){
            case "Sédentaire":
                bet = mb *1.2;
            case "Léger":
                bet = mb *1.375;
            case "Modéré":
                bet = mb *1.55;
            case "Intense":
                bet = mb *1.725;
            case "Très Intense":
                bet = mb *1.9;
        }
        return bet;
    }

    public double getMinProteine(Utilisateur utilisateur){
        return 1*utilisateur.getPoids();
    }


    public double getSel(){
        return 5.0;
    }

    public double getMinFibre(){
        return 25;
    }

    public double getMaxFibre(){
        return 30;
    }

    // 1g --> 9kcal
    public double getMinLipides(){
        return (0.30*bet)/9;
    }

    // 1g --> 9kcal
    public double getMaxLipides(){
        return (0.35*bet)/9;
    }

    // 1g --> 4kcal
    public double getMinGlucides(){
        return (0.50*bet)/4;
    }

    // 1g --> 4kcal




    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }
}
