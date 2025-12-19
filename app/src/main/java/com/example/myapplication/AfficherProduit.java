package com.example.myapplication;


import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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


        ProduitInfoAdapteur adapter = new ProduitInfoAdapteur(this,produit.getNutriment());
        listView.setAdapter(adapter);


    }
}
