package com.example.myapplication;

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
                String productName = product.getString("product_name");


                ArrayList<Nutriment> listnutiment = new ArrayList<>();
                Nutriment proteine = new Nutriment("Proteine",nutriments.getString("proteins_100g") + "g");
                Nutriment glucide = new Nutriment("Glucide",nutriments.getString("carbohydrates_100g") + "g");
                Nutriment calories = new Nutriment("Calories",nutriments.getString("energy-kcal_100g") + "kcal");

                listnutiment.add(proteine);
                listnutiment.add(glucide);
                listnutiment.add(calories);

                Produit produit = new Produit(productName,listnutiment);

                // Retourner le résultat via callback
                callback.onSuccess(produit);

            } catch (Exception e) {
                callback.onError(e);
            }
        }).start();
    }
}
