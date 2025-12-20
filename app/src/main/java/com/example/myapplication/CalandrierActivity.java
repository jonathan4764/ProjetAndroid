package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CalandrierActivity extends AppCompatActivity {

    Map<String,ArrayList<Produit>> calandrier = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("TAG", "passage dans le onCreate");
        EdgeToEdge.enable(this);
        setContentView(R.layout.calandrieractivity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        Button  button4 = findViewById(R.id.btnProfil);
        Button  button5 = findViewById(R.id.bnthistorique);
        Button  button6 = findViewById(R.id.bntjournee);
        RecyclerView recyclerView = findViewById(R.id.recyclerRepas);
        CalendarView cv = findViewById(R.id.calendarHistorique);

        ArrayList<Produit> produit = new ArrayList<>();




        RepasAdapter adapteur = new RepasAdapter(produit);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapteur);

        cv.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            month += 1;
            String dateSelectionnee = String.format("%04d-%02d-%02d", year, month, dayOfMonth);

            ArrayList<Produit> listeDuJour = calandrier.get(dateSelectionnee);
            if (listeDuJour == null) {
                // Crée une nouvelle liste vide si aucun produit pour ce jour
                listeDuJour = new ArrayList<>();
                calandrier.put(dateSelectionnee, listeDuJour);
            }

            // Exemple d'ajout de produit uniquement si c'est le 19 décembre 2025
            if(dateSelectionnee.equals("2025-12-19")) {
                ArrayList<Nutriment> nutriments = new ArrayList<>();
                nutriments.add(new Nutriment("Proteine","10"));
                nutriments.add(new Nutriment("Glucide","10"));
                nutriments.add(new Nutriment("Lipide","10"));
                nutriments.add(new Nutriment("Calories","10"));

                Produit p = new Produit("Test", nutriments);
                listeDuJour.add(p);
            }

            adapteur.setListeRepas(listeDuJour);
        });


        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalandrierActivity.this,ModifProfilActivity.class);
                startActivity(intent);
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalandrierActivity.this,AnalyseAliment.class);
                startActivity(intent);
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalandrierActivity.this,RapportJourneeActivity.class);
                startActivity(intent);
            }
        });

}
}

