package com.example.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner;
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning;

import java.util.ArrayList;
import java.util.List;

public class RapportJourneeActivity extends AppCompatActivity {

    String codefinal;

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

        Button button2 = findViewById(R.id.btnScanner);

        TextView proteine = findViewById(R.id.textValueCalories);
        EditText quantite = findViewById(R.id.editQuantite);


        PieChart pieChart = findViewById(R.id.pieChartNutrition);

// Exemple : pourcentage des macros consommées
        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(40f, "Protéines"));
        entries.add(new PieEntry(30f, "Glucides"));
        entries.add(new PieEntry(20f, "Lipides"));
        entries.add(new PieEntry(10f, "Fibres"));

// Création du dataset
        PieDataSet dataSet = new PieDataSet(entries, "Répartition des nutriments");
        dataSet.setColors(new int[] { Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW });
        dataSet.setValueTextSize(12f);

// Création des données du chart
        PieData pieData = new PieData(dataSet);
        pieChart.setData(pieData);
        pieChart.invalidate(); // rafraîchit le graphique


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

                                            ArrayList<Nutriment> nutriment = product.getNutriment();
                                            System.out.println("test1");
                                            for(Nutriment n : nutriment){
                                                System.out.println("test2");
                                                if(n.getNom().equals("Proteine")){
                                                    Double q = Double.parseDouble(quantite.getText().toString());
                                                    Double result = (q*Double.parseDouble(n.getValeur()))/100;
                                                    proteine.setText(result.toString());
                                                    System.out.println("test3:" + n.getValeur());
                                                }
                                            }


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
