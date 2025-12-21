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

                String grade = year2021.optString("grade");

                String productName = product.optString("product_name");


                String imageUrl = product.optString("image_front_url");
                Log.d("Produit", "Allergènes : " + imageUrl);
                String ingredientsText = product.optString("ingredients_text");
                Log.d("Produit", "Allergènes : " + ingredientsText);
                String allergensText = product.optString("allergens");
                Log.d("Produit", "Allergènes : " + allergensText);


                ArrayList<Nutriment> listnutiment = new ArrayList<>();
                Nutriment proteine = new Nutriment("Protéines",nutriments.optString("proteins_100g"));
                Nutriment glucide = new Nutriment("Glucides",nutriments.optString("carbohydrates_100g"));
                Nutriment calories = new Nutriment("Calories",nutriments.optString("energy-kcal_100g"));
                Nutriment energiekj = new Nutriment("EnergieKJ",nutriments.optString("energy-kj_100g"));
                Nutriment graise = new Nutriment("MatiereGrase",nutriments.optString("fat_100g"));
                Nutriment sel = new Nutriment("Sel",nutriments.optString("salt_100g"));
                Nutriment graisesaturer = new Nutriment("MatiereGraseSature",nutriments.optString("saturated-fat_100g"));
                Nutriment sodium = new Nutriment("Sodium",nutriments.optString("sodium_100g"));
                Nutriment sucre = new Nutriment("Sucres",nutriments.optString("sugars_100g"));
                Nutriment nutriscore2 = new Nutriment("NutriScore",grade);


                listnutiment.add(proteine);
                listnutiment.add(glucide);
                listnutiment.add(calories);
                listnutiment.add(energiekj);
                listnutiment.add(graise);
                listnutiment.add(sel);
                listnutiment.add(graisesaturer);
                listnutiment.add(sodium);
                listnutiment.add(sucre);
                listnutiment.add(nutriscore2);


                Produit produit = new Produit(productName,imageUrl,listnutiment);

                // Retourner le résultat via callback
                callback.onSuccess(produit);

            } catch (Exception e) {
                callback.onError(e);
            }
        }).start();
    }
}
