package com.example.myapplication;

public class Utilisateur {

    private String sexe;
    private int age;
    private float taille;
    private float poids;
    private String activite;

    public Utilisateur(String sexe, int age, float taille, float poids, String activite){
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

    public float getTaille() {
        return taille;
    }

    public void setTaille(float taille) {
        this.taille = taille;
    }

    public float getPoids() {
        return poids;
    }

    public void setPoids(float poids) {
        this.poids = poids;
    }

    public String getActivite() {
        return activite;
    }

    public void setActivite(String activite) {
        this.activite = activite;
    }
}
