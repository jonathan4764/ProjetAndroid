package com.example.myapplication;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RepasAdapter extends RecyclerView.Adapter<RepasAdapter.ProduitViewHolder> {

    private ArrayList<Produit> listeProduits;


    public RepasAdapter(ArrayList<Produit> listeProduits) {
        this.listeProduits = listeProduits;
    }

    @NonNull
    @Override
    public ProduitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.repas, parent, false);
        return new ProduitViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProduitViewHolder holder, int position) {
        System.out.println("test");
        Produit produit = listeProduits.get(position);
        System.out.println("test");

        holder.textNom.setText(produit.getName());
        System.out.println("test");

        ArrayList<Nutriment> listeNutiment = produit.getNutriment();
        System.out.println("test");

        // On initialise à 0 ou vide
        holder.textProteines.setText("Protéines: 0 g");
        holder.textLipides.setText("Lipides: 0 g");
        holder.textGlucides.setText("Glucides: 0 g");
        holder.textCalories.setText("Calories: 0 kcal");
        holder.textQuantite.setText("Quantité: 0 g");

        System.out.println("test");

        for (Nutriment n : listeNutiment) {
            switch (n.getNom()) {
                case "Proteine":
                    holder.textProteines.setText("Protéines: " + n.getValeur() + " g");
                    System.out.println("test1");
                    break;
                case "Lipide":
                    holder.textLipides.setText("Lipides: " + n.getValeur() + " g");
                    System.out.println("test2");
                    break;
                case "Glucide":
                    holder.textGlucides.setText("Glucides: " + n.getValeur() + " g");
                    System.out.println("test3");
                    break;
                case "Calories":
                    holder.textCalories.setText("Calories: " + n.getValeur() + " kcal");
                    System.out.println("test4");
                    break;

            }
        }

    }

    // Permet de mettre à jour la liste
    public void setListeRepas(ArrayList<Produit> nouvelleListe) {
        System.out.println("test");
        if (nouvelleListe != null) {
            listeProduits.clear();
            listeProduits.addAll(nouvelleListe);
            System.out.println("test");
        } else {
            listeProduits.clear();
            System.out.println("test20");
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listeProduits.size();
    }

    // 🔹 ViewHolder
    static class ProduitViewHolder extends RecyclerView.ViewHolder {

        TextView textNom, textQuantite, textCalories, textProteines, textLipides, textGlucides;

        public ProduitViewHolder(@NonNull View itemView) {
            super(itemView);

            textNom = itemView.findViewById(R.id.textNomAliment);
            textQuantite = itemView.findViewById(R.id.textQuantite);
            textCalories = itemView.findViewById(R.id.textCalories);
            textProteines = itemView.findViewById(R.id.textProteines);
            textLipides = itemView.findViewById(R.id.textLipides);
            textGlucides = itemView.findViewById(R.id.textGlucides);
        }
    }
}

