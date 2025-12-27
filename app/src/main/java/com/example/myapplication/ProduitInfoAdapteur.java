package com.example.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ProduitInfoAdapteur extends ArrayAdapter<Nutriment> {

    private Context context;
    private ArrayList<Nutriment> produits;

    public ProduitInfoAdapteur(Context context, ArrayList<Nutriment> produits) {
        super(context, R.layout.infoproduit, produits);
        this.context = context;
        this.produits = produits;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View itemView = convertView;
        Nutriment produit = produits.get(position);
        if (produit.getNom().equals("ingredients") || produit.getNom().equals("allergenes")) {
            itemView = LayoutInflater.from(context)
                    .inflate(R.layout.infoproduit2, parent, false);
        } else {
            itemView = LayoutInflater.from(context)
                    .inflate(R.layout.infoproduit, parent, false);
        }




        TextView textName = itemView.findViewById(R.id.textProduit);
        TextView textValeur = itemView.findViewById(R.id.textView);

        textName.setText(produit.getNom());
        Log.d("NUTRIMENT", "Nom = " + produit.getNom());

        switch (produit.getNom()) {
            case "ingredients":
                textName.setText("Ingrédients");
                textValeur.setText(produit.getValeur());
                break;

            case "allergenes":
                textName.setText("Allergènes");
                textValeur.setText(produit.getValeur());
                break;

            case "proteine":
                textName.setText("Protéines");
                textValeur.setText(produit.getValeur() + " g");
                break;

            case "glucide":
                textName.setText("Glucides");
                textValeur.setText(produit.getValeur() + " g");
                break;

            case "calorie":
                textName.setText("Calories");
                textValeur.setText(produit.getValeur() + " kcal");
                break;

            case "energiekj":
                textName.setText("Énergie (kJ)");
                textValeur.setText(produit.getValeur() + " kJ");
                break;

            case "sel":
                textName.setText("Sel");
                textValeur.setText(produit.getValeur() + " g");
                break;

            case "sodium":
                textName.setText("Sodium");
                textValeur.setText(produit.getValeur() + " mg");
                break;

            case "sucre":
                textName.setText("Sucres");
                textValeur.setText(produit.getValeur() + " g");
                break;

            case "matieregrasse":
                textName.setText("Matières grasses");
                textValeur.setText(produit.getValeur() + " g");
                break;

            case "matieregrassesature":
                textName.setText("Matières grasses saturées");
                textValeur.setText(produit.getValeur() + " g");
                break;

            case "nutriscore":
                textName.setText("Nutri-Score");
                textValeur.setText(produit.getValeur().toUpperCase());
                break;
        }


        return itemView;
    }
}

