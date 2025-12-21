package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner;
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning;

import java.util.ArrayList;

public class AnalyseAliment extends AppCompatActivity{

    String codefinal;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Log.i("TAG", "passage dans le onCreate");
            EdgeToEdge.enable(this);
            setContentView(R.layout.analysealimentactivity);
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });

            Button  button2 = findViewById(R.id.buttonScanner);
            Button  button3 = findViewById(R.id.bntprofil);
            Button  button4 = findViewById(R.id.bntresumer);
            Button  button5 = findViewById(R.id.bntcalandrier);
            ListView listView = findViewById(R.id.listeproduit);

            Helper helper = Helper.getInstance(AnalyseAliment.this);

            ArrayList<Produit> listeProduits = new ArrayList<>();

            Cursor cursor = helper.getAllProducts();

            while (cursor.moveToNext()) {
                long id = cursor.getLong(cursor.getColumnIndexOrThrow("id"));
                String nom = cursor.getString(cursor.getColumnIndexOrThrow("nom"));
                String img = cursor.getString(cursor.getColumnIndexOrThrow("image"));

                ArrayList<Nutriment> nutriments = helper.getNutrimentsByProduitId(id);

                Produit p = new Produit(nom,img,nutriments);

                listeProduits.add(p);
            }
            cursor.close();

            ArrayList<ProduitNutriment> listeProduits2 = new ArrayList<>();

            ProduitAdapteur adapter = new ProduitAdapteur(this,listeProduits);
            listView.setAdapter(adapter);

            button3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AnalyseAliment.this,ModifProfilActivity.class);
                    startActivity(intent);
                }
            });

            button5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AnalyseAliment.this,CalandrierActivity.class);
                    startActivity(intent);
                }
            });

            button4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AnalyseAliment.this,RapportJourneeActivity.class);
                    startActivity(intent);
                }
            });


            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // Récupérer l'élément cliqué
                    Produit produit = (Produit)parent.getItemAtPosition(position);

                    // Ou lancer une nouvelle activité avec ce produit
                    Intent intent = new Intent(AnalyseAliment.this, AfficherProduit.class);
                    intent.putExtra("product_name", produit);
                    startActivity(intent);
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

                                                // Vérifie si le produit existe déjà
                                                Produit p = helper.getProductByName(product.getName());
                                                if (p != null) {
                                                    Toast.makeText(AnalyseAliment.this, "Vous avez déjà scanné ce produit", Toast.LENGTH_SHORT).show();
                                                    return;
                                                }

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
                                                adapter.notifyDataSetChanged();

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
            });


        }



        @Override
        protected void onStart(){
            super.onStart();
            Log.i("TAG", "passage dans le onStart");
        }

        @Override
        protected void onResume(){
            super.onResume();
            Log.i("TAG", "passage dans le onResume");
        }

        @Override
        protected void onPause(){
            super.onPause();
            Log.i("TAG", "passage dans le onPause");
        }

        @Override
        protected void onStop(){
            super.onStop();
            Log.i("TAG", "passage dans le onStop");
        }

        @Override
        protected void onRestart(){
            super.onRestart();
            Log.i("TAG", "passage dans le onRestart");
        }

        @Override
        protected void onDestroy(){
            super.onDestroy();
            Log.i("TAG", "passage dans le onDestroy");
        }
    }

