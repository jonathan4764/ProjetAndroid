package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RapportJourneeActivity extends AppCompatActivity {

    String codefinal;

    ArrayList<Produit> listeProduits = new ArrayList<>();
    ArrayList<Utilisateur> listeUtilisateurs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("TAG", "passage dans le onCreate");
        EdgeToEdge.enable(this);
        setContentView(R.layout.rapportjourneeactivity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        Button button5 = findViewById(R.id.btnHistorique);
        Button button3 = findViewById(R.id.bntProfil);
        ImageButton buttonpetitdejeuner = findViewById(R.id.btnPetitDejeune);
        ImageButton buttondejeuner = findViewById(R.id.btnDejeune);
        ImageButton buttondiner = findViewById(R.id.btnDiner);
        ImageButton buttonencas = findViewById(R.id.btnEncas);

        TextView textpetitdejeuner = findViewById(R.id.textpetitdejeuner);
        TextView textdejeuner = findViewById(R.id.textdejeuner);
        TextView textdiner = findViewById(R.id.textdiner);
        TextView textencas = findViewById(R.id.textencas);

        Helper helper = Helper.getInstance(this);

        Cursor cursor = helper.getAllUsers();

        while (cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow("id"));
            String sexe = cursor.getString(cursor.getColumnIndexOrThrow("sexe"));
            double poids = cursor.getDouble(cursor.getColumnIndexOrThrow("poids"));
            double taille = cursor.getDouble(cursor.getColumnIndexOrThrow("taille"));
            int age = cursor.getInt(cursor.getColumnIndexOrThrow("age"));
            String activite = cursor.getString(cursor.getColumnIndexOrThrow("activite"));


            Utilisateur utilisateur = new Utilisateur(sexe, age, taille, poids, activite);

            listeUtilisateurs.add(utilisateur);
        }
        cursor.close();

        if (listeUtilisateurs.isEmpty()) {
            Intent intent = new Intent(RapportJourneeActivity.this, ModifProfilActivity.class);
            startActivity(intent);
        }


        Button calendrier = findViewById(R.id.bntCalandrier);
        TextView date = findViewById(R.id.txtTitle);

        String date1 = getIntent().getStringExtra("date");
        String datefinal = " ";

        if (date1 == null || date1.isEmpty()) {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE dd MMMM yyyy", Locale.FRANCE);
            String dateFormatee = sdf.format(calendar.getTime());
            dateFormatee = dateFormatee.substring(0, 1).toUpperCase() + dateFormatee.substring(1);
            date.setText(dateFormatee);
            datefinal = dateFormatee;
        } else {
            SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE);
            SimpleDateFormat sdfOutput = new SimpleDateFormat("EEEE dd MMMM yyyy", Locale.FRANCE);

            try {
                Date date3 = sdfInput.parse(date1);
                String dateFormatee = sdfOutput.format(date3);
                dateFormatee = dateFormatee.substring(0, 1).toUpperCase() + dateFormatee.substring(1);
                date.setText(dateFormatee);
                datefinal = dateFormatee;
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

        ArrayList<String> listerepas = new ArrayList<>();
        listerepas.add("Petit déjeuner");
        listerepas.add("Déjeuner");
        listerepas.add("Dîner");
        listerepas.add("En-cas");

        String finalDatefinal = datefinal;
        ArrayList<Long> listeproduitPetitDejeuner;
        StringBuilder listproduit;

        for (String repas : listerepas) {
            switch (repas) {
                case "Petit déjeuner":
                    listeproduitPetitDejeuner = helper.getProduitCalendrier(finalDatefinal, "Petit déjeuner");

                    if (listeproduitPetitDejeuner.isEmpty()) {
                        textpetitdejeuner.setText("Aucun produit ajouté");
                        break;
                    }

                    listproduit = new StringBuilder();

                    for (Long listeproduit : listeproduitPetitDejeuner) {
                        Produit produit = helper.getProductById(listeproduit);
                        if (listproduit.length() > 0) {
                            listproduit.append(", ");
                        }
                        listproduit.append(produit.getName());
                    }
                    textpetitdejeuner.setText(listproduit.toString());
                    listeproduitPetitDejeuner.clear();
                    break;
                case "Déjeuner":
                    listeproduitPetitDejeuner = helper.getProduitCalendrier(finalDatefinal, "Déjeuner");

                    if (listeproduitPetitDejeuner.isEmpty()) {
                        textdejeuner.setText("Aucun produit ajouté");
                        break;
                    }

                    listproduit = new StringBuilder();

                    for (Long listeproduit : listeproduitPetitDejeuner) {
                        Produit produit = helper.getProductById(listeproduit);
                        System.out.println(produit.getName());
                        if (listproduit.length() > 0) {
                            listproduit.append(", ");
                        }
                        listproduit.append(produit.getName());
                    }
                    textdejeuner.setText(listproduit.toString());
                    listeproduitPetitDejeuner.clear();
                    break;
                case "Dîner":
                    listeproduitPetitDejeuner = helper.getProduitCalendrier(finalDatefinal, "Dîner");

                    if (listeproduitPetitDejeuner.isEmpty()) {
                        textdiner.setText("Aucun produit ajouté");
                        break;
                    }

                    listproduit = new StringBuilder();

                    for (Long listeproduit : listeproduitPetitDejeuner) {
                        Produit produit = helper.getProductById(listeproduit);
                        if (listproduit.length() > 0) {
                            listproduit.append(", ");
                        }
                        listproduit.append(produit.getName());
                    }
                    textdiner.setText(listproduit.toString());
                    listeproduitPetitDejeuner.clear();
                    break;
                case "En-cas":
                    listeproduitPetitDejeuner = helper.getProduitCalendrier(finalDatefinal, "En-cas");

                    if (listeproduitPetitDejeuner.isEmpty()) {
                        textencas.setText("Aucun produit ajouté");
                        break;
                    }

                    listproduit = new StringBuilder();

                    for (Long listeproduit : listeproduitPetitDejeuner) {
                        Produit produit = helper.getProductById(listeproduit);
                        if (listproduit.length() > 0) {
                            listproduit.append(", ");
                        }
                        listproduit.append(produit.getName());
                    }
                    textencas.setText(listproduit.toString());
                    listeproduitPetitDejeuner.clear();
                    break;

            }
        }




        calendrier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RapportJourneeActivity.this,CalandrierActivity.class);
                startActivity(intent);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RapportJourneeActivity.this,ModifProfil2.class);
                startActivity(intent);
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RapportJourneeActivity.this,AnalyseAliment.class);
                startActivity(intent);
            }
        });


        buttonpetitdejeuner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RapportJourneeActivity.this,AjouterProduit.class);
                intent.putExtra("date_journee", "Petit déjeuner");
                intent.putExtra("date", finalDatefinal);
                startActivity(intent);
            }
        });

        buttondejeuner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RapportJourneeActivity.this,AjouterProduit.class);
                intent.putExtra("date_journee", "Déjeuner");
                intent.putExtra("date", finalDatefinal);
                startActivity(intent);
            }
        });

        buttondiner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RapportJourneeActivity.this,AjouterProduit.class);
                intent.putExtra("date_journee", "Dîner");
                intent.putExtra("date", finalDatefinal);
                startActivity(intent);
            }
        });

        buttonencas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RapportJourneeActivity.this,AjouterProduit.class);
                intent.putExtra("date_journee", "En-cas");
                intent.putExtra("date", finalDatefinal);
                startActivity(intent);
            }
        });

        PieChart pieChartProteine = findViewById(R.id.pieChartProteine);

        ArrayList<Produit> listeproduit = helper.getProduitsByDate(finalDatefinal);

        double sommeProteine = 0;
        double sommeGlucide = 0;
        double sommeCalorie = 0;

        for(Produit p : listeproduit){
            ArrayList<Nutriment> listeNutriments = new ArrayList<>();
                listeNutriments.add(new Nutriment("proteine", String.valueOf(p.getProteine())));
                listeNutriments.add(new Nutriment("glucide", String.valueOf(p.getGlucide())));
                listeNutriments.add(new Nutriment("calorie", String.valueOf(p.getCalorie())));

                for(Nutriment n : listeNutriments){
                    switch (n.getNom()){
                        case "proteine":
                            sommeProteine = sommeProteine + Double.parseDouble(n.getValeur());
                            break;
                        case "glucide":
                            sommeGlucide = sommeGlucide + Double.parseDouble(n.getValeur());
                            break;
                        case "calorie":
                            sommeCalorie = sommeCalorie + Double.parseDouble(n.getValeur());

                    }

                }

        }

        Utilisateur utilisateur = new Utilisateur();

        Cursor cursor2 = helper.getAllUsers();

        while (cursor2.moveToNext()) {

            // Récupérer toutes les valeurs depuis le Cursor
            String sexe2 = cursor2.getString(cursor2.getColumnIndexOrThrow("sexe"));
            String poids2 = cursor2.getString(cursor2.getColumnIndexOrThrow("poids"));
            String taille2 = cursor2.getString(cursor2.getColumnIndexOrThrow("taille"));
            String age2 = cursor2.getString(cursor2.getColumnIndexOrThrow("age"));
            String activite2 = cursor2.getString(cursor2.getColumnIndexOrThrow("activite"));

            utilisateur.setActivite(activite2);
            utilisateur.setAge(Integer.parseInt(age2));
            utilisateur.setPoids(Double.parseDouble(poids2));
            utilisateur.setTaille(Double.parseDouble(taille2));
            utilisateur.setSexe(sexe2);

        }

        cursor2.close();

        if(utilisateur.getSexe() != null){
            CalculBesoin besoin = new CalculBesoin(utilisateur);

            double besoinMinProteine = besoin.getMinProteine(utilisateur);


            double besoinMinGlucide = besoin.getMinGlucides();
            System.out.println(besoinMinGlucide);

            double finalSommeProteine = sommeProteine;
            pieChartProteine.post(() -> {

                List<PieEntry> entries = new ArrayList<>();

                entries.add(new PieEntry((float)(besoinMinProteine-finalSommeProteine), ""));
                entries.add(new PieEntry((float) finalSommeProteine, ""));

                PieDataSet dataSet = new PieDataSet(entries, "");
                dataSet.setColors(new int[]{
                        Color.RED,
                        Color.GREEN
                });
                dataSet.setValueTextSize(12f);

                PieData pieData = new PieData(dataSet);

                dataSet.setValueFormatter(new ValueFormatter() {
                    @Override
                    public String getFormattedValue(float value) {
                        return String.format("%.1f g", value);
                    }
                });

                pieChartProteine.setData(pieData);
                pieChartProteine.getDescription().setEnabled(false);
                pieChartProteine.setUsePercentValues(false);
                pieChartProteine.getLegend().setEnabled(false);
                pieChartProteine.invalidate();
            });

            PieChart pieChartGlucides = findViewById(R.id.pieChartGlucide);

            double finalSommeGlucide = sommeGlucide;
            System.out.println(finalSommeGlucide);
            pieChartGlucides.post(() -> {

                List<PieEntry> entries = new ArrayList<>();

                entries.add(new PieEntry((float)(besoinMinGlucide-finalSommeGlucide), ""));
                entries.add(new PieEntry((float) finalSommeGlucide, ""));

                PieDataSet dataSet = new PieDataSet(entries, "Glucides");
                dataSet.setColors(new int[]{
                        Color.RED,
                        Color.GREEN
                });
                dataSet.setValueTextSize(12f);

                PieData pieData = new PieData(dataSet);

                dataSet.setValueFormatter(new ValueFormatter() {
                    @Override
                    public String getFormattedValue(float value) {
                        return String.format("%.1f g", value);
                    }
                });

                pieChartGlucides.setData(pieData);
                pieChartGlucides.getDescription().setEnabled(false);
                pieChartGlucides.setUsePercentValues(false);
                pieChartGlucides.getLegend().setEnabled(false);
                pieChartGlucides.invalidate();
            });

            PieChart pieChartCalories = findViewById(R.id.pieChartCalories);

            pieChartCalories.post(() -> {

                List<PieEntry> entries = new ArrayList<>();

                entries.add(new PieEntry(40f, "Restant"));
                entries.add(new PieEntry(30f, "Mangées"));

                PieDataSet dataSet = new PieDataSet(entries, "Calories");
                dataSet.setColors(new int[]{
                        Color.RED,
                        Color.GREEN
                });
                dataSet.setValueTextSize(12f);

                PieData pieData = new PieData(dataSet);

                dataSet.setValueFormatter(new ValueFormatter() {
                    @Override
                    public String getFormattedValue(float value) {
                        return String.format("%.1f g", value);
                    }
                });

                pieChartCalories.setData(pieData);
                pieChartCalories.getDescription().setEnabled(false);
                pieChartCalories.setUsePercentValues(false);
                pieChartCalories.getLegend().setEnabled(false);
                pieChartCalories.invalidate();
            });
        }



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
    }
}
