package com.example.myapplication;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.InputStream;
import java.util.ArrayList;


public class AfficherProduit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("TAG", "passage dans le onCreate");
        EdgeToEdge.enable(this);
        setContentView(R.layout.afficherproduitactivity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        TextView nomProduitText = findViewById(R.id.textView3);
        ListView listView = findViewById(R.id.listeproduit);

        Produit produit = (Produit) getIntent().getSerializableExtra("product_name");


        nomProduitText.setText(produit.getName());

        ArrayList<Nutriment> listeNutriments = new ArrayList<>();

        listeNutriments.add(new Nutriment("Protéines", String.valueOf(produit.getProteine())));
        listeNutriments.add(new Nutriment("Glucides", String.valueOf(produit.getGlucide())));
        listeNutriments.add(new Nutriment("Calories", String.valueOf(produit.getCalorie())));
        listeNutriments.add(new Nutriment("Énergie KJ", String.valueOf(produit.getEnergiekj())));
        listeNutriments.add(new Nutriment("Sel", String.valueOf(produit.getSel())));
        listeNutriments.add(new Nutriment("Sodium", String.valueOf(produit.getSodium())));
        listeNutriments.add(new Nutriment("Sucres", String.valueOf(produit.getSucre())));
        listeNutriments.add(new Nutriment("Matière grasse", String.valueOf(produit.getMatieregrasse())));
        listeNutriments.add(new Nutriment("Graisses saturées", String.valueOf(produit.getMatieregrassesature())));
        listeNutriments.add(new Nutriment("NutriScore", produit.getNutriscore()));
        listeNutriments.add(new Nutriment("Ingrédients", produit.getIngrediants()));
        listeNutriments.add(new Nutriment("Allergènes", produit.getAllergenes()));



        ProduitInfoAdapteur adapter = new ProduitInfoAdapteur(this,listeNutriments);
        listView.setAdapter(adapter);


    }
}
