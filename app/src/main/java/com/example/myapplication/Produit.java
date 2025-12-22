package com.example.myapplication;

import java.io.Serializable;
import java.util.ArrayList;

public class Produit implements Serializable {

    private String name;
    private String image;
    private double proteine;
    private double glucide;
    private double calorie;
    private double energiekj;
    private double sel;
    private double sodium;
    private double sucre;
    private double matieregrasse;
    private double matieregrassesature;
    private String nutriscore;
    private String ingrediants;
    private String allergenes;

    public Produit(String name, String image, double proteine, double glucide, double calorie, double energiekj, double sel, double sodium, double sucre, double matieregrasse, double matieregrassesature, String nutriscore, String ingrediants, String allergenes) {
        this.name = name;
        this.image = image;
        this.proteine = proteine;
        this.glucide = glucide;
        this.calorie = calorie;
        this.energiekj = energiekj;
        this.sel = sel;
        this.sodium = sodium;
        this.sucre = sucre;
        this.matieregrasse = matieregrasse;
        this.matieregrassesature = matieregrassesature;
        this.nutriscore = nutriscore;
        this.ingrediants = ingrediants;
        this.allergenes = allergenes;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getProteine() {
        return proteine;
    }

    public void setProteine(double proteine) {
        this.proteine = proteine;
    }

    public double getGlucide() {
        return glucide;
    }

    public void setGlucide(double glucide) {
        this.glucide = glucide;
    }

    public double getCalorie() {
        return calorie;
    }

    public void setCalorie(double calorie) {
        this.calorie = calorie;
    }

    public double getEnergiekj() {
        return energiekj;
    }

    public void setEnergiekj(double energiekj) {
        this.energiekj = energiekj;
    }

    public String getAllergenes() {
        return allergenes;
    }

    public void setAllergenes(String allergenes) {
        this.allergenes = allergenes;
    }

    public String getIngrediants() {
        return ingrediants;
    }

    public void setIngrediants(String ingrediants) {
        this.ingrediants = ingrediants;
    }

    public String getNutriscore() {
        return nutriscore;
    }

    public void setNutriscore(String nutriscore) {
        this.nutriscore = nutriscore;
    }

    public double getMatieregrassesature() {
        return matieregrassesature;
    }

    public void setMatieregrassesature(double matieregrassesature) {
        this.matieregrassesature = matieregrassesature;
    }

    public double getMatieregrasse() {
        return matieregrasse;
    }

    public void setMatieregrasse(double matieregrasse) {
        this.matieregrasse = matieregrasse;
    }

    public double getSucre() {
        return sucre;
    }

    public void setSucre(double sucre) {
        this.sucre = sucre;
    }

    public double getSodium() {
        return sodium;
    }

    public void setSodium(double sodium) {
        this.sodium = sodium;
    }

    public double getSel() {
        return sel;
    }

    public void setSel(double sel) {
        this.sel = sel;
    }
}
