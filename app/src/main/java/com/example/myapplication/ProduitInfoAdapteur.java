package com.example.myapplication;

import android.content.Context;
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
        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.infoproduit, parent, false);
        }

        Nutriment produit = produits.get(position);


        TextView textName = itemView.findViewById(R.id.textProduit);
        TextView textvaleur = itemView.findViewById(R.id.textView);

        textName.setText(produit.getNom() + ":");
        textvaleur.setText(produit.getValeur());

        return itemView;
    }
}

