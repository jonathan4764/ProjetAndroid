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


public class ProduitAdapteur2 extends ArrayAdapter {

    private Context context;
    private  ArrayList<Produit> produits;
    private Calendrier calendrier;

    public ProduitAdapteur2(Context context, ArrayList<Produit> produits,Calendrier calendrier) {
        super(context, R.layout.produit, produits);
        this.context = context;
        this.produits = produits;
        this.calendrier = calendrier;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.produit2, parent, false);
        }

        // Récupérer les vues
        TextView textProduit = itemView.findViewById(R.id.textProduit);
        ImageView imageProduit = itemView.findViewById(R.id.imageProduit);
        ImageButton ajouter = itemView.findViewById(R.id.imageButton2);

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

        ajouter.setOnClickListener(v -> {

            // Par exemple, lancer une nouvelle activité avec ce produit
            Intent intent = new Intent(context, VoirProduitAjouter.class);
            intent.putExtra("product_name2",p.getName());
            intent.putExtra("calendrier",calendrier);
            context.startActivity(intent);
        });

        return itemView;
    }
}
