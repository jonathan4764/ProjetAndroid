package com.example.myapplication;

import android.util.Log;

import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class API {

    String codebar;

    public API(String codebar) {
        this.codebar = codebar;
    }

    public void callAPI(APICallback callback) {
        String url = "https://world.openfoodfacts.net/api/v2/product/" + codebar;

        OkHttpClient client = new OkHttpClient();

        new Thread(() -> {
            try {
                Request request = new Request.Builder().url(url).build();
                Response response = client.newCall(request).execute();
                String json = response.body().string();

                // Parse JSON
                JSONObject jsonObject = new JSONObject(json);
                JSONObject product = jsonObject.getJSONObject("product");
                JSONObject nutriments = product.getJSONObject("nutriments");
                JSONObject nutriscore = product.getJSONObject("nutriscore");
                JSONObject year2021 = nutriscore.getJSONObject("2021");

                String nutriscore2 = year2021.optString("grade");

                String productName = product.optString("product_name");


                String imageUrl = product.optString("image_front_url");
                Log.d("Produit", "Allergènes : " + imageUrl);
                String ingredientsText = product.optString("ingredients_text");
                Log.d("Produit", "Allergènes : " + ingredientsText);
                String allergensText = product.optString("allergens");
                Log.d("Produit", "Allergènes : " + allergensText);


                double proteine = safeParseDouble(nutriments.optString("proteins_100g"));
                double glucide = safeParseDouble(nutriments.optString("carbohydrates_100g"));
                double calories = safeParseDouble(nutriments.optString("energy-kcal_100g"));
                double energiekj = safeParseDouble(nutriments.optString("energy-kj_100g"));
                double graise = safeParseDouble(nutriments.optString("fat_100g"));
                double sel = safeParseDouble(nutriments.optString("salt_100g"));
                double graisesaturer = safeParseDouble(nutriments.optString("saturated-fat_100g"));
                double sodium = safeParseDouble(nutriments.optString("sodium_100g"));
                double sucre = safeParseDouble(nutriments.optString("sugars_100g"));




                Produit produit = new Produit(
                        productName,
                        imageUrl,
                        proteine,
                        glucide,
                        calories,
                        energiekj,
                        sel,
                        sodium,
                        sucre,
                        graise,
                        graisesaturer,
                        nutriscore2,
                        ingredientsText,
                        allergensText
                );

                // Retourner le résultat via callback
                callback.onSuccess(produit);

            } catch (Exception e) {
                callback.onError(e);
            }
        }).start();
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
