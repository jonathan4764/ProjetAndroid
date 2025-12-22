package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class Helper extends SQLiteOpenHelper {
    private static Helper instance;

    // Nom et version de la base
    private static final String DATABASE_NAME = "nutrition";
    private static final int DATABASE_VERSION = 1;

    // Info Table Utilisateur
    public static final String TABLE_USERS = "users";
    public static final String COL_ID_USERS = "id";
    public static final String COL_SEXE = "sexe";
    public static final String COL_AGE = "age";
    public static final String COL_TAILLE = "taille";
    public static final String COL_POIDS = "poids";
    public static final String COL_ACTIVITE = "activite";

    // Info Table Produit
    public static final String TABLE_PRODUIT = "produit";
    public static final String COL_ID_PRODUIT = "id";
    public static final String COL_NAME = "nom";
    public static final String COL_IMAGE = "image";
    public static final String COL_PROTEINE = "proteine";
    public static final String COL_GLUCIDE = "glucide";
    public static final String COL_CALORIE = "calorie";
    public static final String COL_ENERGIE_KJ = "energiekj";
    public static final String COL_SEL = "sel";
    public static final String COL_SODIUM = "sodium";
    public static final String COL_SUCRE = "sucre";
    public static final String COL_MATIERE_GRASSE = "matieregrasse";
    public static final String COL_MATIERE_GRASSE_SATURE = "matieregrassesature";
    public static final String COL_NUTRISCORE = "nutriscore";
    public static final String COL_INGREDIENTS = "ingredients";
    public static final String COL_ALLERGENES = "allergenes";




    // Info Table User_Produit
    public static final String TABLE_USER_PRODUIT = "user_produit";
    public static final String COL_ID_USER_PRODUIT = "iduser";
    public static final String COL_ID_PRODUIT_USER = "idproduit";



    private static final String CREATE_USERS =
            "CREATE TABLE IF NOT EXISTS " + TABLE_USERS + " (" +
                    COL_ID_USERS + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_SEXE + " TEXT, " +
                    COL_AGE + " REAL, " +
                    COL_TAILLE + " REAL, " +
                    COL_POIDS + " REAL, " +
                    COL_ACTIVITE + " TEXT)";




    private static final String CREATE_USER_PRODUIT =
            "CREATE TABLE IF NOT EXISTS " + TABLE_USER_PRODUIT + " (" +
                    COL_ID_USER_PRODUIT + " INTEGER," +
                    COL_ID_PRODUIT_USER + " INTEGER," +
                    "PRIMARY KEY (" + COL_ID_USER_PRODUIT + "," + COL_ID_PRODUIT_USER + "), " +
                    "FOREIGN KEY (" + COL_ID_USER_PRODUIT + ") REFERENCES " +  TABLE_USERS + "(" + COL_ID_USERS + ")ON DELETE CASCADE, " +
                    "FOREIGN KEY (" + COL_ID_PRODUIT_USER + ") REFERENCES " + TABLE_PRODUIT + "(" + COL_ID_PRODUIT + ") "+ "ON DELETE CASCADE)";

    private static final String CREATE_TABLE_PRODUIT =
            "CREATE TABLE " + TABLE_PRODUIT + " (" +
                    COL_ID_PRODUIT + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_NAME + " TEXT UNIQUE, " +
                    COL_IMAGE + " TEXT, " +
                    COL_PROTEINE + " REAL, " +
                    COL_GLUCIDE + " REAL, " +
                    COL_CALORIE + " REAL, " +
                    COL_ENERGIE_KJ + " REAL, " +
                    COL_SEL + " REAL, " +
                    COL_SODIUM + " REAL, " +
                    COL_SUCRE + " REAL, " +
                    COL_MATIERE_GRASSE + " REAL, " +
                    COL_MATIERE_GRASSE_SATURE + " REAL, " +
                    COL_NUTRISCORE + " TEXT, " +
                    COL_INGREDIENTS + " TEXT, " +
                    COL_ALLERGENES + " TEXT" +
                    ")";

    public Helper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Méthode pour récupérer l’instance unique
    public static synchronized Helper getInstance(Context context) {
        if (instance == null) {
            instance = new Helper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys=ON");
        db.execSQL(CREATE_USERS);
        db.execSQL(CREATE_TABLE_PRODUIT);
        db.execSQL(CREATE_USER_PRODUIT);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_PRODUIT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUIT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }




    public long insertProduit(Produit product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("nom", product.getName());
        values.put("image", product.getImage());
        values.put("proteine", product.getProteine());
        values.put("glucide", product.getGlucide());
        values.put("calorie", product.getCalorie());
        values.put("energiekj", product.getEnergiekj());
        values.put("sel", product.getSel());
        values.put("sodium", product.getSodium());
        values.put("sucre", product.getSucre());
        values.put("matieregrasse", product.getMatieregrasse());
        values.put("matieregrassesature", product.getMatieregrassesature());
        values.put("nutriscore", product.getNutriscore());
        values.put("ingredients", product.getIngrediants());
        values.put("allergenes", product.getAllergenes());

        // Insérer le produit dans la table et récupérer l'ID généré
        long idProduit = db.insert("produit", null, values);
        db.close();
        return idProduit;
    }





    public long insertUser(String sexe, int age, double poids, double taille, String activite ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_SEXE, sexe);
        values.put(COL_AGE, age);
        values.put(COL_TAILLE, taille);
        values.put(COL_POIDS, poids);
        values.put(COL_ACTIVITE, activite);

        return db.insert(TABLE_USERS, null, values);
    }




    public Produit getProductByName(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Produit produit = null;

        // Requête pour récupérer le produit par son nom
        String query = "SELECT * FROM produit WHERE nom = ?";
        Cursor cursor = db.rawQuery(query, new String[]{name});

        if (cursor.moveToFirst()) { // Si le produit existe

            // Récupération des valeurs depuis le Cursor
            String image = cursor.getString(cursor.getColumnIndexOrThrow("image"));
            double proteine = cursor.getDouble(cursor.getColumnIndexOrThrow("proteine"));
            double glucide = cursor.getDouble(cursor.getColumnIndexOrThrow("glucide"));
            double calorie = cursor.getDouble(cursor.getColumnIndexOrThrow("calorie"));
            double energiekj = cursor.getDouble(cursor.getColumnIndexOrThrow("energiekj"));
            double sel = cursor.getDouble(cursor.getColumnIndexOrThrow("sel"));
            double sodium = cursor.getDouble(cursor.getColumnIndexOrThrow("sodium"));
            double sucre = cursor.getDouble(cursor.getColumnIndexOrThrow("sucre"));
            double matieregrasse = cursor.getDouble(cursor.getColumnIndexOrThrow("matieregrasse"));
            double matieregrassesature = cursor.getDouble(cursor.getColumnIndexOrThrow("matieregrassesature"));
            String nutriscore = cursor.getString(cursor.getColumnIndexOrThrow("nutriscore"));
            String ingrediants = cursor.getString(cursor.getColumnIndexOrThrow("ingredients"));
            String allergenes = cursor.getString(cursor.getColumnIndexOrThrow("allergenes"));

            // Construire le produit avec les valeurs récupérées
            produit = new Produit(name, image, proteine, glucide,calorie, energiekj, sel, sodium, sucre,
                    matieregrasse, matieregrassesature, nutriscore, ingrediants, allergenes
            );
        }

        cursor.close();
        return produit;
    }

    public long getProduitIdByName(String nomProduit) {
        SQLiteDatabase db = this.getReadableDatabase();
        long id = -1;

        Cursor cursor = db.rawQuery(
                "SELECT id FROM produit WHERE nom = ?",
                new String[]{nomProduit}
        );

        if (cursor.moveToFirst()) {
            id = cursor.getLong(0); // récupère la première colonne → id
        }

        cursor.close();
        return id;
    }

    public Cursor getAllUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_USERS, null);
    }

    public Cursor getAllProducts() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_PRODUIT, null);
    }

}
