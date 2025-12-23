package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
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

public class AjouterProduit extends AppCompatActivity {
    String codefinal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("TAG", "passage dans le onCreate");
        EdgeToEdge.enable(this);
        setContentView(R.layout.afficherproduitajouter);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView titre = findViewById(R.id.textTitle);
        Button button2 = findViewById(R.id.buttonAction);
        ListView listView = findViewById(R.id.listViewItems);

        String date = getIntent().getStringExtra("date");



        ArrayList<Produit> listeProduits = new ArrayList<>();

        Calendrier calendrier = new Calendrier();

        Helper helper = Helper.getInstance(AjouterProduit.this);

        String date_journee = getIntent().getStringExtra("date_journee");
        titre.setText(date_journee);

        ProduitAdapteur2 adapter = new ProduitAdapteur2(this,listeProduits,calendrier);
        listView.setAdapter(adapter);






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
                                                Toast.makeText(AjouterProduit.this, "Vous avez déjà scanné ce produit", Toast.LENGTH_SHORT).show();
                                                return;
                                            }

                                            // Insère le produit
                                            helper.insertProduit(product);
                                            listeProduits.add(product);

                                            long id = helper.getProduitIdByName(product.getName());

                                            calendrier.setDate(date);
                                            calendrier.setId(id);
                                            calendrier.setRepas(date_journee);
                                            calendrier.setValeur(0);
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
}
