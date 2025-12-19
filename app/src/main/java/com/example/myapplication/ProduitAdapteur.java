package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class ProduitAdapteur extends ArrayAdapter {

    private Context context;
    private  ArrayList<String> produits;

    public ProduitAdapteur(Context context, ArrayList<String> produits) {
        super(context, R.layout.produit, produits);
        this.context = context;
        this.produits = produits;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.produit, parent, false);
        }

        TextView textProduit = itemView.findViewById(R.id.textProduit);
        //ImageView imageProduit = itemView.findViewById(R.id.imageProduit);

        String productName = produits.get(position);
        textProduit.setText(productName);

        //imageProduit.setImageResource(R.drawable.ic_autre_image);

        return itemView;
    }
}
