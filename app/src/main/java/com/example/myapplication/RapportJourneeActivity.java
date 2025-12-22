package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner;
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning;

import java.util.ArrayList;
import java.util.List;

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

        Helper helper = Helper.getInstance(this);

        Cursor cursor = helper.getAllUsers();

        while (cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow("id"));
            String sexe = cursor.getString(cursor.getColumnIndexOrThrow("sexe"));
            double poids = cursor.getDouble(cursor.getColumnIndexOrThrow("poids"));
            double taille = cursor.getDouble(cursor.getColumnIndexOrThrow("taille"));
            int age = cursor.getInt(cursor.getColumnIndexOrThrow("age"));
            String activite = cursor.getString(cursor.getColumnIndexOrThrow("activite"));



            Utilisateur utilisateur = new Utilisateur(sexe,age,taille,poids,activite);

            listeUtilisateurs.add(utilisateur);
        }
        cursor.close();

        if(listeUtilisateurs.isEmpty()){
            Intent intent = new Intent(RapportJourneeActivity.this,ModifProfilActivity.class);
            startActivity(intent);
        }



        ImageButton calendrier = findViewById(R.id.btnCalendrier);
        TextView date = findViewById(R.id.txtTitle);
        date.setText(getIntent().getStringExtra("date"));

        calendrier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RapportJourneeActivity.this,CalandrierActivity.class);
                startActivity(intent);
            }
        });



/*
        Button button2 = findViewById(R.id.btnScanner);
        Button button = findViewById(R.id.button);

        TextView proteine = findViewById(R.id.textValueProteines);
        TextView calorie = findViewById(R.id.textValueCalorie);
        TextView glucide = findViewById(R.id.textValueGlucides);
        TextView sel = findViewById(R.id.textValueSel);
        TextView sucre = findViewById(R.id.textValueSucres);
        TextView sodium = findViewById(R.id.textValueSodium);
        TextView matieregrasse = findViewById(R.id.textValueGraise);
        TextView matieregrasesature = findViewById(R.id.textValueSatures);
        TextView energiekj = findViewById(R.id.textValueEnergiekj);

        EditText quantite = findViewById(R.id.editQuantite);


        Helper helper = Helper.getInstance(RapportJourneeActivity.this);

        /*PieChart pieChart = findViewById(R.id.pieChartNutrition);

        pieChart.post(() -> {

            List<PieEntry> entries = new ArrayList<>();

            entries.add(new PieEntry(40f, "Protéines"));
            entries.add(new PieEntry(30f, "Glucides"));
            entries.add(new PieEntry(20f, "Lipides"));
            entries.add(new PieEntry(10f, "Fibres"));

            PieDataSet dataSet = new PieDataSet(entries, "Répartition des nutriments");
            dataSet.setColors(new int[]{
                    Color.RED,
                    Color.BLUE,
                    Color.GREEN,
                    Color.YELLOW
            });
            dataSet.setValueTextSize(12f);

            PieData pieData = new PieData(dataSet);

            pieChart.setData(pieData);
            pieChart.getDescription().setEnabled(false);
            pieChart.setUsePercentValues(true);
            pieChart.invalidate();
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String qStr = quantite.getText().toString().trim();

                if (qStr.isEmpty()) {
                    Toast.makeText(RapportJourneeActivity.this, "Veuillez entrer une quantité", Toast.LENGTH_SHORT).show();
                    return;
                }
                double q = Double.parseDouble(qStr);

                if(nutriment.isEmpty()){
                    Toast.makeText(RapportJourneeActivity.this, "Vous avez pas scanner de produit", Toast.LENGTH_SHORT).show();
                    return;
                }
                for(Nutriment n : nutriment){

                    double valeur = safeParseDouble(n.getValeur());
                    double result = (q * valeur) / 100;
                    double valeurs = 0;

                    switch (n.getNom()){
                        case "Protéines":
                            valeurs = safeParseDouble(proteine.getText().toString());
                            proteine.setText(String.valueOf(result + valeurs));
                            break;
                        case "Glucides":
                            valeurs = safeParseDouble(glucide.getText().toString());
                            glucide.setText(String.valueOf(result + valeurs));
                            break;
                        case "Calories":
                            valeurs = safeParseDouble(calorie.getText().toString());
                            calorie.setText(String.valueOf(result + valeurs));
                            break;
                        case "EnergieKJ":
                            valeurs = safeParseDouble(energiekj.getText().toString());
                            energiekj.setText(String.valueOf(result + valeurs));
                            break;
                        case "MatiereGrase":
                            valeurs = safeParseDouble(matieregrasse.getText().toString());
                            matieregrasse.setText(String.valueOf(result + valeurs));
                            break;
                        case "Sel":
                            valeurs = safeParseDouble(sel.getText().toString());
                            sel.setText(String.valueOf(result + valeurs));
                            break;
                        case "MatiereGraseSature":
                            valeurs = safeParseDouble(matieregrasesature.getText().toString());
                            matieregrasesature.setText(String.valueOf(result + valeurs));
                            break;
                        case "Sodium":
                            valeurs = safeParseDouble(sodium.getText().toString());
                            sodium.setText(String.valueOf(result + valeurs));
                            break;
                        case "Sucres":
                            valeurs = safeParseDouble(sucre.getText().toString());
                            sucre.setText(String.valueOf(result + valeurs));
                            break;

                    }

                }
                listeProduits.clear();
                nutriment.clear();
            }
        });


        button2.setOnClickListener(view -> {
            GmsBarcodeScannerOptions options = new GmsBarcodeScannerOptions.Builder()
                    .setBarcodeFormats(
                            Barcode.FORMAT_EAN_13)
                    .build();

            GmsBarcodeScanner scanner = GmsBarcodeScanning.getClient(this);

            scanner
                    .startScan()
                    .addOnSuccessListener(
                            barcode -> {
                                // Task completed successfully
                                String rawValue = barcode.getRawValue();
                                codefinal = rawValue;
                                API api = new API(codefinal);
                                api.callAPI(new APICallback() {
                                    @Override
                                    public void onSuccess(Produit product) {
                                        runOnUiThread(() -> {
                                            nutriment = product.getNutriment();
                                            // Insère le produit
                                            long idProduit = helper.insertProduit(product.getName(), product.getImage());

                                            for (Nutriment n : product.getNutriment()) {
                                                // Récupère l'ID du nutriment existant
                                                long idNutriment = helper.getNutrimentsByName(n.getNom());

                                                // On ne fait rien si le nutriment n’existe pas dans la table nutriment
                                                if (idNutriment != -1) {
                                                    helper.insertProduitNutriment(idProduit, idNutriment,n.getNom() ,n.getValeur());
                                                } else {
                                                    // Optionnel : loguer un avertissement si le nutriment n'existe pas
                                                    Log.w("DB", "Nutriment non trouvé dans la table: " + n.getNom());
                                                }
                                            }

                                            listeProduits.add(product);


                                        });
                                    }

                                    @Override
                                    public void onError(Exception e) {
                                        e.printStackTrace();
                                    }
                                });

                            })
                    .addOnCanceledListener(
                            () -> {
                                // Task canceled
                            })
                    .addOnFailureListener(
                            e -> {
                                // Task failed with an exception
                            });
        });*/
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
