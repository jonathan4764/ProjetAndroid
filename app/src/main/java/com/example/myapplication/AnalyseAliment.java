package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
            ListView listView = findViewById(R.id.listeproduit);




            ArrayList<String> listeProduits = new ArrayList<>();

            ProduitAdapteur adapter = new ProduitAdapteur(this,listeProduits);
            listView.setAdapter(adapter);


            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // Récupérer l'élément cliqué
                    String produit = (String) parent.getItemAtPosition(position);

                    // Afficher un toast
                    Toast.makeText(AnalyseAliment.this, "Produit : " + produit, Toast.LENGTH_SHORT).show();

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
                                        public void onSuccess(String productName) {
                                            runOnUiThread(() -> {
                                                // Ajouter à la liste
                                                listeProduits.add(productName);
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

