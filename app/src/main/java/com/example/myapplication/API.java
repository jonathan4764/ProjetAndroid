package com.example.myapplication;

import org.json.JSONObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class API {

    String codebar;

    public API(String codebar) {
        this.codebar = codebar;
    }

    public void callAPI(APICallback callback) {
        String url = "https://world.openfoodfacts.net/api/v2/product/" + codebar + "?fields=product_name";

        OkHttpClient client = new OkHttpClient();

        new Thread(() -> {
            try {
                Request request = new Request.Builder().url(url).build();
                Response response = client.newCall(request).execute();
                String json = response.body().string();

                // Parse JSON
                JSONObject jsonObject = new JSONObject(json);
                JSONObject product = jsonObject.getJSONObject("product");
                String productName = product.getString("product_name");

                // Retourner le résultat via callback
                callback.onSuccess(productName);

            } catch (Exception e) {
                callback.onError(e);
            }
        }).start();
    }
}
