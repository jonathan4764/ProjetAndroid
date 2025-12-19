package com.example.myapplication;

import java.io.Serializable;
import java.util.ArrayList;

public class Produit implements Serializable {

    private String name;
    private ArrayList<Nutriment>  nutriment;

    public Produit(String name, ArrayList<Nutriment> nutiment){
        this.name = name;
        this.nutriment = nutiment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Nutriment> getNutriment() {
        return nutriment;
    }

    public void setNutriment(ArrayList<Nutriment> nutriment) {
        this.nutriment = nutriment;
    }
}
