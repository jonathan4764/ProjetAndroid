package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
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
        String date_journee = getIntent().getStringExtra("date_journee");
        titre.setText(date_journee);

        Helper helper = Helper.getInstance(this);

        // Crée la liste des produits avec idCalendrier
        ArrayList<ProduitCalendrier> listeProduitsCalendrier = new ArrayList<>();
        ArrayList<Long> idsCalendrier = helper.getProduitCalendrier(date, date_journee);
        for (long idCal : idsCalendrier) {
            Calendrier calendrier = helper.getCalendrierById(idCal);
            long idProduit = helper.getProduitIdFromCalendrier(idCal);
            Produit produit = helper.getProductById(idProduit);
            listeProduitsCalendrier.add(new ProduitCalendrier(produit, calendrier));
        }

        // Passe la liste à l'adapter
        ProduitAdapteur2 adapter = new ProduitAdapteur2(this, listeProduitsCalendrier);
        listView.setAdapter(adapter);

        // Scanner le produit
        button2.setOnClickListener(view -> {
            GmsBarcodeScannerOptions options = new GmsBarcodeScannerOptions.Builder()
                    .setBarcodeFormats(Barcode.FORMAT_EAN_13)
                    .build();

            GmsBarcodeScanner scanner = GmsBarcodeScanning.getClient(this);

            scanner.startScan()
                    .addOnSuccessListener(barcode -> {
                        codefinal = barcode.getRawValue();
                        API api = new API(codefinal);

                        api.callAPI(new APICallback() {
                            @Override
                            public void onSuccess(Produit product) {
                                runOnUiThread(() -> {
                                    // Vérifie si le produit est déjà dans le repas
                                    ArrayList<Long> produitsDuRepas = helper.getProduitCalendrier(date, date_journee);
                                    boolean dejaPresent = false;
                                    for (long idProduit : produitsDuRepas) {
                                        Produit produitExistant = helper.getProductById(idProduit);
                                        if (produitExistant != null &&
                                                produitExistant.getName().equals(product.getName())) {
                                            dejaPresent = true;
                                            break;
                                        }
                                    }

                                    if (dejaPresent) {
                                        Toast.makeText(
                                                AjouterProduit.this,
                                                "Produit déjà ajouté, vous pouvez modifier la quantité",
                                                Toast.LENGTH_SHORT
                                        ).show();
                                        return;
                                    }

                                    // Insère le produit si nécessaire
                                    long idProduit = helper.getProduitIdByName(product.getName());
                                    if (idProduit == -1) {
                                        idProduit = helper.insertProduit(product);
                                    }

                                    // Insère le produit dans le calendrier
                                    long idCalendrier = helper.insertCalendrier(idProduit, date, date_journee, 0);
                                    Calendrier calendrier = helper.getCalendrierById(idCalendrier);

                                    // Ajoute à la liste locale pour l'adapter
                                    listeProduitsCalendrier.add(new ProduitCalendrier(product, calendrier));
                                    adapter.notifyDataSetChanged();
                                });
                            }

                            @Override
                            public void onError(Exception e) {
                                e.printStackTrace();
                            }
                        });
                    })
                    .addOnCanceledListener(() -> {
                        // Scan annulé
                    })
                    .addOnFailureListener(e -> {
                        e.printStackTrace();
                    });
        });
    }
}
