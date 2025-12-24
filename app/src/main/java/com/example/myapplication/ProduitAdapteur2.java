package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;


public class ProduitAdapteur2 extends ArrayAdapter<ProduitCalendrier> {
    private Context context;
    private ArrayList<ProduitCalendrier> produits;

    public ProduitAdapteur2(Context context, ArrayList<ProduitCalendrier> produits) {
        super(context, R.layout.produit, produits);
        this.context = context;
        this.produits = produits;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.produit2, parent, false);
        }

        TextView textProduit = itemView.findViewById(R.id.textProduit);
        ImageView imageProduit = itemView.findViewById(R.id.imageProduit);
        ImageButton ajouter = itemView.findViewById(R.id.imageButton2);
        TextView textvaleur = itemView.findViewById(R.id.textValeur);

        ProduitCalendrier pc = produits.get(position);
        Produit p = pc.getProduit();

        Calendrier calendrier = pc.getIdCalendrier(); // c'est déjà le calendrier
        textvaleur.setText(calendrier.getValeur() + "g");


        textProduit.setText(p.getName());

        // Image
        String imageUrl = p.getImage();
        if (imageUrl != null && !imageUrl.isEmpty()) {
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

        ajouter.setOnClickListener(v -> {
            Intent intent = new Intent(context, VoirProduitAjouter.class);
            intent.putExtra("product_name2", p.getName());
            intent.putExtra("id_calendrier", calendrier.getIdcalendrier());
            context.startActivity(intent);
        });

        return itemView;
    }
}

