package com.example.myapplication;

public class Utilisateur {

    private String sexe;
    private int age;
    private double taille;
    private double poids;
    private String activite;

    public Utilisateur(String sexe, int age, double taille, double poids, String activite){
        this.activite = activite;
        this.taille = taille;
        this.age = age;
        this.poids = poids;
        this.sexe = sexe;

    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getTaille() {
        return taille;
    }

    public void setTaille(double taille) {
        this.taille = taille;
    }

    public double getPoids() {
        return poids;
    }

    public void setPoids(double poids) {
        this.poids = poids;
    }

    public String getActivite() {
        return activite;
    }

    public void setActivite(String activite) {
        this.activite = activite;
    }
}
