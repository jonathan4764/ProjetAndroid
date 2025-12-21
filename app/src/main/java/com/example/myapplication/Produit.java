package com.example.myapplication;

import java.io.Serializable;
import java.util.ArrayList;

public class Produit implements Serializable {

    private String name;
    private String image;
    private ArrayList<Nutriment>  nutriment;

    public Produit(String name, String image,ArrayList<Nutriment> nutiment){
        this.name = name;
        this.image = image;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


}
