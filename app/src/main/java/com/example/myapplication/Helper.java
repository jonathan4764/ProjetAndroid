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

    // Info Table NUTRIMENT
    public static final String TABLE_NUTRIMENT = "nutriment";
    public static final String COL_ID_NUTRIMENT = "id";
    public static final String COL_NOM = "nom";


    // Info Table Produit_Nutriment
    public static final String TABLE_PRODUIT_NUTRIMENT = "produit_nutriment";
    public static final String COL_ID_NUTRIMENT_PRODUIT = "idproduit";
    public static final String COL_ID_PRODUIT_NUTRIMENT = "idnutriment";
    public static final String COL_VALEUR = "valeur";
    public static final String COL_NOMNUTRIMENT = "valeur";


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

    private static final String CREATE_PRODUITS =
            "CREATE TABLE IF NOT EXISTS " + TABLE_PRODUIT + " (" +
                    COL_ID_PRODUIT + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_NAME + " TEXT, " +
                    COL_IMAGE + " TEXT)";

    private static final String CREATE_NUTRIMENTS =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NUTRIMENT + " (" +
                    COL_ID_NUTRIMENT + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_NOM + " TEXT UNIQUE )";



    private static final String CREATE_PRODUIT_NUTRIMENT =
            "CREATE TABLE IF NOT EXISTS " + TABLE_PRODUIT_NUTRIMENT + " (" +
                    COL_ID_NUTRIMENT_PRODUIT + " INTEGER," +
                    COL_ID_PRODUIT_NUTRIMENT + " INTEGER," +
                    COL_VALEUR + " REAL," +
                    "PRIMARY KEY (" + COL_ID_NUTRIMENT_PRODUIT + "," + COL_ID_PRODUIT_NUTRIMENT + "), " +
                    "FOREIGN KEY (" + COL_ID_NUTRIMENT_PRODUIT + ") REFERENCES " +  TABLE_PRODUIT + "(" + COL_ID_PRODUIT + ")ON DELETE CASCADE, " +
                    "FOREIGN KEY (" + COL_ID_PRODUIT_NUTRIMENT + ") REFERENCES " + TABLE_NUTRIMENT + "(" + COL_ID_NUTRIMENT + ") "+ "ON DELETE CASCADE)";

    private static final String CREATE_USER_PRODUIT =
            "CREATE TABLE IF NOT EXISTS " + TABLE_USER_PRODUIT + " (" +
                    COL_ID_USER_PRODUIT + " INTEGER," +
                    COL_ID_PRODUIT_USER + " INTEGER," +
                    "PRIMARY KEY (" + COL_ID_USER_PRODUIT + "," + COL_ID_PRODUIT_USER + "), " +
                    "FOREIGN KEY (" + COL_ID_USER_PRODUIT + ") REFERENCES " +  TABLE_USERS + "(" + COL_ID_USERS + ")ON DELETE CASCADE, " +
                    "FOREIGN KEY (" + COL_ID_PRODUIT_USER + ") REFERENCES " + TABLE_PRODUIT + "(" + COL_ID_PRODUIT + ") "+ "ON DELETE CASCADE)";



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
        db.execSQL(CREATE_PRODUITS);
        db.execSQL(CREATE_NUTRIMENTS);
        db.execSQL(CREATE_PRODUIT_NUTRIMENT);
        db.execSQL(CREATE_USER_PRODUIT);
        prefillNutriments(db);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_PRODUIT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUIT_NUTRIMENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NUTRIMENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUIT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    private void prefillNutriments(SQLiteDatabase db) {
        ContentValues values = new ContentValues();

        // Exemple nutriments
        values.put(COL_NOM, "Protéines");
        db.insert(TABLE_NUTRIMENT, null, values);

        values.put(COL_NOM, "Glucides");
        db.insert(TABLE_NUTRIMENT, null, values);

        values.put(COL_NOM, "Calories");
        db.insert(TABLE_NUTRIMENT, null, values);

        values.put(COL_NOM, "EnergieKJ");
        db.insert(TABLE_NUTRIMENT, null, values);

        values.put(COL_NOM, "Sucres");
        db.insert(TABLE_NUTRIMENT, null, values);

        values.put(COL_NOM, "Sel");
        db.insert(TABLE_NUTRIMENT, null, values);

        values.put(COL_NOM, "MatiereGrase");
        db.insert(TABLE_NUTRIMENT, null, values);

        values.put(COL_NOM, "Sodium");
        db.insert(TABLE_NUTRIMENT, null, values);

        values.put(COL_NOM, "MatiereGraseSature");
        db.insert(TABLE_NUTRIMENT, null, values);

        values.put(COL_NOM, "NutriScore");
        db.insert(TABLE_NUTRIMENT, null, values);

    }


    public long insertProduit(String name, String image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_NAME, name);
        values.put(COL_IMAGE, image);


        return db.insert(TABLE_PRODUIT, null, values);
    }



    public long insertProduitNutriment(long idnutriment, long idproduit, String nom,String valeur) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_ID_NUTRIMENT_PRODUIT, idnutriment);
        values.put(COL_VALEUR,valeur);
        values.put(COL_ID_PRODUIT_NUTRIMENT,idproduit);
        values.put(COL_NOMNUTRIMENT,nom);

        return db.insert(TABLE_PRODUIT_NUTRIMENT, null, values);
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
    public long getNutrimentsByName(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        long id = 0;

        String query = "SELECT id " +
                "FROM " + TABLE_NUTRIMENT +
                " WHERE " + COL_NOM + "= ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(name)});

        while (cursor.moveToNext()) {
            id = cursor.getLong(cursor.getColumnIndexOrThrow("id"));

        }

        cursor.close();
        return id;
    }

    public ArrayList<Nutriment> getNutrimentsByProduitId(long produitId) {
        ArrayList<Nutriment> liste = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT n.id, n.nom, pn.valeur " +
                "FROM nutriment n " +
                "INNER JOIN produit_nutriment pn ON n.id = pn.idnutriment " +
                "WHERE pn.idproduit = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(produitId)});

        while (cursor.moveToNext()) {
            String nom = cursor.getString(cursor.getColumnIndexOrThrow("nom"));
            String valeur = cursor.getString(cursor.getColumnIndexOrThrow("valeur"));
            liste.add(new Nutriment(nom, valeur));
        }

        cursor.close();
        return liste;
    }

    public Produit getProductByName(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Produit produit = null;

        // Requête pour récupérer le produit par son nom
        String query = "SELECT * FROM " + TABLE_PRODUIT + " WHERE " + COL_NAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{name});

        if (cursor.moveToFirst()) { // Si le produit existe
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(COL_ID_PRODUIT));
            String nom = cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME));
            String image = cursor.getString(cursor.getColumnIndexOrThrow(COL_IMAGE));

            // Récupérer les nutriments associés
            ArrayList<Nutriment> nutriments = getNutrimentsByProduitId(id);

            produit = new Produit(nom, image, nutriments);
        }

        cursor.close();
        return produit;
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
