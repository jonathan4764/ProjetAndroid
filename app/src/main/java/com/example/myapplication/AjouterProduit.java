package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
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
    private ListView listViewRecherche;
    private ArrayList<ProduitCalendrier> produitsRecherche;


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
        TextView recherche = findViewById(R.id.editName);
        Button button2 = findViewById(R.id.buttonAction);
        ListView listView = findViewById(R.id.listViewItems);
        ListView listViewRecherche = findViewById(R.id.listViewRecherche);

        Button button = findViewById(R.id.bntprofil);
        Button button5 = findViewById(R.id.btnCalandrier);
        Button button3 = findViewById(R.id.btnRepas);
        Button button4 = findViewById(R.id.btnHistorique);

        ArrayList<Produit> produits = new ArrayList<>();

        String date = getIntent().getStringExtra("date");
        String date_journee = getIntent().getStringExtra("date_journee");
        titre.setText(date_journee);

        Helper helper = Helper.getInstance(this);

        // Crée la liste des produits avec idCalendrier
        ArrayList<ProduitCalendrier> listeProduitsCalendrier = new ArrayList<>();

        Log.d("TEST", "date = " + date + ", repas = " + date_journee);
        ArrayList<Long> idsCalendrier = helper.getCalendrierIds(date, date_journee);

        Cursor cursor = helper.getAllProducts();

        while (cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow("id"));
            String nom = cursor.getString(cursor.getColumnIndexOrThrow("nom"));

            System.out.println("id:" + id);
            System.out.println("nom:" + nom);
        }
        cursor.close();


        ArrayList<Calendrier> calendriers =
                helper.getCalendriersByDateAndRepas(date, date_journee);

        for (Calendrier calendrier : calendriers) {
            Produit produit = helper.getProductById(calendrier.getId());
            listeProduitsCalendrier.add(new ProduitCalendrier(produit, calendrier));
        }

        // Passe la liste à l'adapter
        ProduitAdapteur2 adapter = new ProduitAdapteur2(this, listeProduitsCalendrier);
        listView.setAdapter(adapter);

        // Liste recherche OpenFoodFacts
        produitsRecherche = new ArrayList<>();

        RechercheAdapteur adapterRecherche = new RechercheAdapteur(this, produitsRecherche);
        listViewRecherche.setAdapter(adapterRecherche);

        recherche.setOnEditorActionListener((v, actionId, event) -> {
            // actionId correspond aux actions du clavier, par ex. IME_ACTION_SEARCH ou IME_ACTION_DONE
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE) {
                String query = v.getText().toString().trim();
                if (!query.isEmpty()) {
                    listView.setVisibility(View.GONE);
                    listViewRecherche.setVisibility(View.VISIBLE);
                    API api = new API(recherche.getText().toString());
                    api.rechercherProduitOpenFoodFacts(new APICallback() {
                        @Override
                        public void onSuccess(Produit product) {
                            runOnUiThread(() -> {
                                produitsRecherche.clear();
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
                                produitsRecherche.add(new ProduitCalendrier(product, calendrier));
                                adapterRecherche.notifyDataSetChanged();
                                listViewRecherche.setVisibility(View.VISIBLE);

                                Cursor cursor = helper.getAllProductsCalendrier();

                                while (cursor.moveToNext()) {

                                    // Récupérer toutes les valeurs depuis le Cursor
                                    String id = cursor.getString(cursor.getColumnIndexOrThrow("id"));
                                    String idproduit = cursor.getString(cursor.getColumnIndexOrThrow("idproduit"));
                                    String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
                                    String repas = cursor.getString(cursor.getColumnIndexOrThrow("repas"));
                                    String quantite = cursor.getString(cursor.getColumnIndexOrThrow("quantite"));


                                    System.out.println(id);
                                    System.out.println(idproduit);
                                    System.out.println(date);
                                    System.out.println(repas);
                                    System.out.println(quantite);


                                }

                                cursor.close();

                            });
                        }

                        @Override
                        public void onError(Exception e) {
                            e.printStackTrace();
                        }
                    });
                }else{
                    listViewRecherche.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                }
                return true; // on a géré l'action
            }
            return false;
        });

        recherche.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().isEmpty()) {
                    listViewRecherche.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                }
            }
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AjouterProduit.this,CalandrierActivity.class);
                startActivity(intent);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AjouterProduit.this,RapportJourneeActivity.class);
                startActivity(intent);
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AjouterProduit.this,AnalyseAliment.class);
                startActivity(intent);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AjouterProduit.this,ModifProfil2.class);
                startActivity(intent);
            }
        });

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
                                runOnUiThread(() -> {
                                            Toast.makeText(AjouterProduit.this, "Erreur scan réessayez", Toast.LENGTH_SHORT).show();
                                });
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
