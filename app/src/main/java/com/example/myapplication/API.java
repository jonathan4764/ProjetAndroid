package com.example.myapplication;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

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
                Log.d("Produit", "nutriscore2 : " + nutriscore2);
                String productName = product.optString("product_name");


                String imageUrl = product.optString("image_front_url");
                Log.d("Produit", "imageUrl : " + imageUrl);
                String ingredientsText = safeString(product.optString("ingredients_text"));
                Log.d("Produit", "ingredientsText : " + ingredientsText);
                String allergensText = safeString(product.optString("allergens"));
                Log.d("Produit", "allergensText : " + allergensText);


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


    public void rechercherProduitOpenFoodFacts(APICallback callback) {

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS) // temps pour établir la connexion
                .writeTimeout(60, TimeUnit.SECONDS)   // temps pour envoyer la requête
                .readTimeout(60, TimeUnit.SECONDS)    // temps pour lire la réponse
                .build();

        String url = "https://world.openfoodfacts.org/cgi/search.pl" +
                "?search_terms=" + Uri.encode(codebar) +
                "&search_simple=1" +
                "&action=process" +
                "&json=1" +
                "&page_size=10";

        System.out.println(url);

        new Thread(() -> {
            try {
                Request request = new Request.Builder().url(url).build();
                Response response = client.newCall(request).execute();

                if (!response.isSuccessful()) {
                    callback.onError(new IOException("HTTP error code: " + response.code()));
                    return;
                }

                String json = response.body().string();
                JSONObject root = new JSONObject(json);
                JSONArray productsArray = root.optJSONArray("products");

                Log.d("API", "JSON brut: " + json);

                if (productsArray == null || productsArray.length() == 0) {
                    callback.onError(new Exception("Aucun produit trouvé"));
                    System.out.println(("testnull"));
                    return;
                }

                // On prend le premier produit
                JSONObject product = productsArray.getJSONObject(0);
                JSONObject nutriments = product.optJSONObject("nutriments");
                JSONObject nutriscore = product.optJSONObject("nutriscore");
                JSONObject year2021 = nutriscore.getJSONObject("2021");

                String nutriscore2 = safeString(year2021.optString("grade"));
                Log.d("Produit", "nutriscore2 : " + nutriscore2);
                String productName = product.optString("product_name");

                String imageUrl = product.optString("image_front_url");
                Log.d("Produit", "imageUrl : " + imageUrl);
                String ingredientsText = safeString(product.optString("ingredients_text"));
                Log.d("Produit", "ingredientsText : " + ingredientsText);
                String allergensText = safeString(product.optString("allergens"));
                Log.d("Produit", "allergensText : " + allergensText);


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

                System.out.println(produit.getName());

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
    private String safeString(String value) {
        if (value == null) return "Aucune information";

        value = value.trim().toLowerCase(); // enlève espaces, retours chariot en début/fin

        if (value.isEmpty() || value.equals("unknown")) return "Aucune information";

        return value; // renvoie le texte original (sans trim si besoin)
    }

}
