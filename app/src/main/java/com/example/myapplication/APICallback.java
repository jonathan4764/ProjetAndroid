package com.example.myapplication;

public interface APICallback {
    void onSuccess(Produit productName);
    void onError(Exception e);
}
