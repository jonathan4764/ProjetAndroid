package com.example.myapplication;

public interface APICallback {
    void onSuccess(String productName);
    void onError(Exception e);
}
