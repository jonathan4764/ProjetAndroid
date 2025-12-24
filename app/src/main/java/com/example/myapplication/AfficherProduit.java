package com.example.myapplication;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

        Button button = findViewById(R.id.bntprofil);
        Button button2 = findViewById(R.id.btnCalandrier);
        Button button3 = findViewById(R.id.btnRepas);
        Button button4 = findViewById(R.id.btnHistorique);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AfficherProduit.this,ModifProfil2.class);
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AfficherProduit.this,CalandrierActivity.class);
                startActivity(intent);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AfficherProduit.this,RapportJourneeActivity.class);
                startActivity(intent);
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AfficherProduit.this,AnalyseAliment.class);
                startActivity(intent);
            }
        });

        Produit produit = (Produit) getIntent().getSerializableExtra("product_name");


        nomProduitText.setText(produit.getName());

        ArrayList<Nutriment> listeNutriments = new ArrayList<>();

        listeNutriments.add(new Nutriment("proteine", String.valueOf(produit.getProteine())));
        listeNutriments.add(new Nutriment("glucide", String.valueOf(produit.getGlucide())));
        listeNutriments.add(new Nutriment("calorie", String.valueOf(produit.getCalorie())));
        listeNutriments.add(new Nutriment("energiekj", String.valueOf(produit.getEnergiekj())));
        listeNutriments.add(new Nutriment("sel", String.valueOf(produit.getSel())));
        listeNutriments.add(new Nutriment("sodium", String.valueOf(produit.getSodium())));
        listeNutriments.add(new Nutriment("sucre", String.valueOf(produit.getSucre())));
        listeNutriments.add(new Nutriment("matieregrasse", String.valueOf(produit.getMatieregrasse())));
        listeNutriments.add(new Nutriment("matieregrassesature", String.valueOf(produit.getMatieregrassesature())));
        listeNutriments.add(new Nutriment("nutriscore", produit.getNutriscore()));
        listeNutriments.add(new Nutriment("ingredients", produit.getIngrediants()));
        listeNutriments.add(new Nutriment("allergenes", produit.getAllergenes()));



        ProduitInfoAdapteur adapter = new ProduitInfoAdapteur(this,listeNutriments);
        listView.setAdapter(adapter);


    }
}
