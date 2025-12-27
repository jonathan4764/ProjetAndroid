package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

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

        TextView titre = findViewById(R.id.textView3);

        Button ajouter = findViewById(R.id.btnAdd);

        EditText valeur = findViewById(R.id.editValue);

        ListView listView = findViewById(R.id.listeproduit);

        Helper helper = Helper.getInstance(VoirProduitAjouter.this);

        String nom = getIntent().getStringExtra("product_name2");

        titre.setText(nom);

        long idCalendrier = getIntent().getLongExtra("id_calendrier", -1);

        if (idCalendrier == -1) {
            Toast.makeText(this, "Erreur calendrier", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        String dateSelectionnee = helper.getCalendrierById(idCalendrier).getDate();
        String dateISO = "";

        SimpleDateFormat sdfFR =
                new SimpleDateFormat("EEEE dd MMMM yyyy", Locale.FRANCE);
        SimpleDateFormat sdfISO =
                new SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE);

        try {
            Date dateObj = sdfFR.parse(dateSelectionnee);
            dateISO = sdfISO.format(dateObj);


        } catch (ParseException e) {
            e.printStackTrace();
        }

        long p = helper.getProduitIdByName(nom);

        valeur.setText(String.valueOf(helper.getCalendrierById(idCalendrier).getValeur()));

        Produit produit = helper.getProductById(p);

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





        ProduitInfoAdapteur adapter = new ProduitInfoAdapteur(this,listeNutriments);
        listView.setAdapter(adapter);

        String finalDateISO = dateISO;
        ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String qStr = valeur.getText().toString().trim();

                        if (qStr.isEmpty()) {
                            Toast.makeText(VoirProduitAjouter.this, "Veuillez entrer une quantité", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        double q = Double.parseDouble(qStr);

                        long idproduit = helper.getProduitIdByName(produit.getName());


                        for(Nutriment n : listeNutriments){

                            double valeur = safeParseDouble(n.getValeur());
                            double result = (q * valeur) / 100;


                            helper.updateProduit(idproduit,result,n.getNom());

                        }

                float qte = Float.parseFloat(valeur.getText().toString());

                Calendrier cal = helper.getCalendrierById(idCalendrier);
                cal.setValeur(qte);

                helper.updateCalendrier(cal);


                Cursor cursor = helper.getAllProductsCalendrier();

                while (cursor.moveToNext()) {

                    // Récupérer toutes les valeurs depuis le Cursor
                    String id = cursor.getString(cursor.getColumnIndexOrThrow("id"));
                    String idproduit2 = cursor.getString(cursor.getColumnIndexOrThrow("idproduit"));
                    String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
                    String repas = cursor.getString(cursor.getColumnIndexOrThrow("repas"));
                    String quantite = cursor.getString(cursor.getColumnIndexOrThrow("quantite"));



                    System.out.println(id);
                    System.out.println(idproduit2);
                    System.out.println(date);
                    System.out.println(repas);
                    System.out.println(quantite);


                }

                cursor.close();

                Intent intent = new Intent(VoirProduitAjouter.this,RapportJourneeActivity.class);
                intent.putExtra("date", finalDateISO);
                startActivity(intent);
            }

        });

}
    private double safeParseDouble(String value) {
        if (value == null) return 0.0;

        value = value.trim();

        if (value.isEmpty()) return 0.0;

        // Remplace la virgule par un point (OpenFoodFacts le fait parfois)
        value = value.replace(",", ".");

        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            Log.w("PARSE", "Valeur invalide : " + value);
            return 0.0;
        }
    }}
