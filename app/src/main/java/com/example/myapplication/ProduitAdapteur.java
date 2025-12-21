package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;


public class ProduitAdapteur extends ArrayAdapter {

    private Context context;
    private  ArrayList<Produit> produits;

    public ProduitAdapteur(Context context, ArrayList<Produit> produits) {
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

        // Récupérer les vues
        TextView textProduit = itemView.findViewById(R.id.textProduit);
        ImageView imageProduit = itemView.findViewById(R.id.imageProduit);

        // Récupérer le produit à la position actuelle
        Produit p = produits.get(position);

        // Nom du produit
        textProduit.setText(p.getName());

        // Image du produit (URL)
        String imageUrl = p.getImage();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            // Exemple simple sans Glide : télécharger en arrière-plan
            new Thread(() -> {
                try {
                    InputStream input = new java.net.URL(imageUrl).openStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(input);
                    imageProduit.post(() -> imageProduit.setImageBitmap(bitmap));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }

        return itemView;
    }
}
