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
import java.net.URL;
import java.util.List;

public class RechercheAdapteur extends ArrayAdapter<ProduitCalendrier> {

    private final Context context;
    private final List<ProduitCalendrier> produits;

    public RechercheAdapteur(Context context, List<ProduitCalendrier> produits) {
        super(context, 0, produits);
        this.context = context;
        this.produits = produits;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.produit2, parent, false);
        }

        ProduitCalendrier pc = produits.get(position);
        Produit p = pc.getProduit();

        Calendrier calendrier = pc.getIdCalendrier();


        TextView txtNom = convertView.findViewById(R.id.textProduit);
        ImageButton ajouter = convertView.findViewById(R.id.imageButton2);
        ImageView imgProduit = convertView.findViewById(R.id.imageProduit);

        txtNom.setText(p.getName());

        // Charger l'image si disponible
        String imageUrl = p.getImage();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            new Thread(() -> {
                try {
                    InputStream input = new URL(imageUrl).openStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(input);
                    imgProduit.post(() -> imgProduit.setImageBitmap(bitmap));
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

        return convertView;
    }
}

