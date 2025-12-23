package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class VoirProduitAjouter extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("TAG", "passage dans le onCreate");
        EdgeToEdge.enable(this);
        setContentView(R.layout.ajouterproduit);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Calendrier canlendrier = (Calendrier)getIntent().getSerializableExtra("calendrier");

        TextView titre = findViewById(R.id.textView3);

        Button ajouter = findViewById(R.id.btnAdd);

        Helper helper = Helper.getInstance(VoirProduitAjouter.this);

        String nom = getIntent().getStringExtra("product_name2");

        titre.setText(nom);

        ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                helper.insertCalendrier(canlendrier.getId(),canlendrier.getDate(),canlendrier.getRepas(),canlendrier.getValeur());



                Intent intent = new Intent(VoirProduitAjouter.this,RapportJourneeActivity.class);
                startActivity(intent);
            }
        });
}}
