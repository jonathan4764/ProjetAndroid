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

    // Info Table Calendrier
    public static final String TABLE_CALENDRIER = "calendrier";
    public static final String COL_ID_CALENDRIER = "id";
    public static final String COL_ID_PRODUITS = "idproduit";
    public static final String COL_DATE = "date";
    public static final String COL_REPAS = "repas";
    public static final String COL_QUANTITE = "quantite";

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

    // Info Table Produit
    public static final String TABLE_PRODUIT_100g = "produit100g";
    public static final String COL_ID_PRODUIT_100g = "id";
    public static final String COL_NAME_100g = "nom";
    public static final String COL_IMAGE_100g = "image";
    public static final String COL_PROTEINE_100g = "proteine";
    public static final String COL_GLUCIDE_100g = "glucide";
    public static final String COL_CALORIE_100g = "calorie";
    public static final String COL_ENERGIE_KJ_100g = "energiekj";
    public static final String COL_SEL_100g = "sel";
    public static final String COL_SODIUM_100g = "sodium";
    public static final String COL_SUCRE_100g = "sucre";
    public static final String COL_MATIERE_GRASSE_100g = "matieregrasse";
    public static final String COL_MATIERE_GRASSE_SATURE_100g = "matieregrassesature";
    public static final String COL_NUTRISCORE_100g = "nutriscore";
    public static final String COL_INGREDIENTS_100g = "ingredients";
    public static final String COL_ALLERGENES_100g = "allergenes";




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

    private static final String CREATE_CALENDRIER =
            "CREATE TABLE IF NOT EXISTS " + TABLE_CALENDRIER + " (" +
                    COL_ID_CALENDRIER + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_ID_PRODUITS + " INTEGER NOT NULL, " +
                    COL_DATE + " TEXT NOT NULL, " +
                    COL_REPAS + " TEXT NOT NULL, " +
                    COL_QUANTITE + " REAL, " +
                    "FOREIGN KEY(" + COL_ID_PRODUITS + ") REFERENCES produit(id))";




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

    private static final String CREATE_TABLE_PRODUIT_100g =
            "CREATE TABLE " + TABLE_PRODUIT_100g + " (" +
                    COL_ID_PRODUIT_100g + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_NAME_100g + " TEXT UNIQUE, " +
                    COL_IMAGE_100g + " TEXT, " +
                    COL_PROTEINE_100g + " REAL, " +
                    COL_GLUCIDE_100g + " REAL, " +
                    COL_CALORIE_100g + " REAL, " +
                    COL_ENERGIE_KJ_100g + " REAL, " +
                    COL_SEL_100g + " REAL, " +
                    COL_SODIUM_100g + " REAL, " +
                    COL_SUCRE_100g + " REAL, " +
                    COL_MATIERE_GRASSE_100g + " REAL, " +
                    COL_MATIERE_GRASSE_SATURE_100g + " REAL, " +
                    COL_NUTRISCORE_100g + " TEXT, " +
                    COL_INGREDIENTS_100g + " TEXT, " +
                    COL_ALLERGENES_100g + " TEXT" +
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
        db.execSQL(CREATE_CALENDRIER);
        db.execSQL(CREATE_TABLE_PRODUIT_100g);
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

    public long insertProduit100g(Produit product) {
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
        long idProduit = db.insert("produit100g", null, values);
        db.close();
        return idProduit;
    }

    public int updateProduit(long idProduit, double valeur, String nom) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(nom, valeur);

        int rows = db.update(
                "produit",
                values,
                "id = ?",
                new String[]{String.valueOf(idProduit)}
        );

        db.close();
        return rows;
    }

    public ArrayList<Long> getCalendrierIds(String date, String repas) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Long> ids = new ArrayList<>();

        Cursor cursor = db.rawQuery(
                "SELECT id FROM calendrier WHERE date = ? AND repas = ?",
                new String[]{date, repas}
        );

        if (cursor.moveToFirst()) {
            do {
                ids.add(cursor.getLong(0));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return ids;
    }

    public void deleteCalendrierById(long idCalendrier) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("calendrier", "id = ?", new String[]{String.valueOf(idCalendrier)});
    }


    public long insertCalendrier(long idProduit, String date, String repas, double quantite) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_ID_PRODUITS, idProduit);
        values.put(COL_DATE, date);
        values.put(COL_REPAS, repas);
        values.put(COL_QUANTITE, quantite);

        // Insère et retourne l'ID de la ligne créée
        return db.insert(TABLE_CALENDRIER, null, values);
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

    public int updateUser(Utilisateur utilisateur) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_SEXE, utilisateur.getSexe());
        values.put(COL_AGE, utilisateur.getAge());
        values.put(COL_POIDS, utilisateur.getPoids());
        values.put(COL_TAILLE, utilisateur.getTaille());
        values.put(COL_ACTIVITE, utilisateur.getActivite());


        return db.update(TABLE_USERS, values, null, null);
    }

    public int updateCalendrier(Calendrier calendrier) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_QUANTITE, calendrier.getValeur());

        // WHERE id_calendrier = ?
        return db.update(
                TABLE_CALENDRIER,
                values,
                COL_ID_CALENDRIER + " = ?",
                new String[]{String.valueOf(calendrier.getIdcalendrier())}
        );
    }

    public long getProduitIdFromCalendrier(long idCalendrier) {
        SQLiteDatabase db = this.getReadableDatabase();
        long idProduit = -1;

        Cursor cursor = db.rawQuery(
                "SELECT " + COL_ID_PRODUITS + " FROM " + TABLE_CALENDRIER + " WHERE " + COL_ID_CALENDRIER + " = ?",
                new String[]{String.valueOf(idCalendrier)}
        );

        if (cursor.moveToFirst()) {
            idProduit = cursor.getLong(0);
        }

        cursor.close();
        return idProduit;
    }

    public long getCalendrierId(long idProduit, String date, String repas) {
        SQLiteDatabase db = this.getReadableDatabase();
        long idCalendrier = -1;

        Cursor cursor = db.rawQuery(
                "SELECT " + COL_ID_CALENDRIER + " FROM " + TABLE_CALENDRIER +
                        " WHERE " + COL_ID_PRODUITS + " = ? AND " + COL_DATE + " = ? AND " + COL_REPAS + " = ?",
                new String[]{String.valueOf(idProduit), date, repas}
        );

        if (cursor.moveToFirst()) {
            idCalendrier = cursor.getLong(0);
        }

        cursor.close();
        return idCalendrier;
    }

    public ArrayList<Produit> getProduitsByDate(String date) {
        ArrayList<Produit> produits = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT " + COL_ID_PRODUITS + " FROM " + TABLE_CALENDRIER + " WHERE " + COL_DATE + " = ?",
                new String[]{ date }
        );

        if (cursor.moveToFirst()) {
            do {
                long idProduit = cursor.getLong(cursor.getColumnIndexOrThrow(COL_ID_PRODUITS));
                Produit produit = getProductById(idProduit);
                if (produit != null) {
                    produits.add(produit);
                }
            } while (cursor.moveToNext());
        }

        cursor.close();
        return produits;
    }

    public Calendrier getCalendrierById(long id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(
                "SELECT * FROM calendrier WHERE id = ?",
                new String[]{String.valueOf(id)}
        );

        if (c.moveToFirst()) {
            Calendrier cal = new Calendrier();
            cal.setIdcalendrier(c.getLong(0));
            cal.setId(c.getLong(1));
            cal.setDate(c.getString(2));
            cal.setRepas(c.getString(3));
            cal.setValeur(c.getFloat(4));
            c.close();
            return cal;
        }
        c.close();
        return null;
    }

    public ArrayList<Calendrier> getCalendriersByDateAndRepas(String date, String repas) {
        ArrayList<Calendrier> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.rawQuery(
                "SELECT * FROM calendrier WHERE date = ? AND repas = ?",
                new String[]{date, repas}
        );

        if (c.moveToFirst()) {
            do {
                Calendrier cal = new Calendrier();
                cal.setIdcalendrier(c.getLong(c.getColumnIndexOrThrow("id")));
                cal.setId(c.getLong(c.getColumnIndexOrThrow("idproduit")));
                cal.setDate(c.getString(c.getColumnIndexOrThrow("date")));
                cal.setRepas(c.getString(c.getColumnIndexOrThrow("repas")));
                cal.setValeur(c.getFloat(c.getColumnIndexOrThrow("quantite")));
                list.add(cal);
            } while (c.moveToNext());
        }

        c.close();
        return list;
    }

    public Calendrier getCalendrierByProduitIdAndDate(long produitId, String date, String repas) {
        SQLiteDatabase db = this.getReadableDatabase();
        Calendrier calendrier = null;

        Cursor cursor = db.rawQuery(
                "SELECT * FROM calendrier WHERE idproduit = ? AND date = ? AND repas = ?",
                new String[]{String.valueOf(produitId), date, repas}
        );

        if (cursor.moveToFirst()) {
            calendrier = new Calendrier();
            calendrier.setId(cursor.getLong(cursor.getColumnIndexOrThrow("idproduit")));
            calendrier.setDate(cursor.getString(cursor.getColumnIndexOrThrow("date")));
            calendrier.setRepas(cursor.getString(cursor.getColumnIndexOrThrow("repas")));
            calendrier.setValeur(cursor.getFloat(cursor.getColumnIndexOrThrow("quantite")));
            calendrier.setIdcalendrier(cursor.getLong(cursor.getColumnIndexOrThrow("id")));
        }

        cursor.close();
        return calendrier;
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



    public Produit getProductById(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Produit produit = null;

        // Requête pour récupérer le produit par son nom
        String query = "SELECT * FROM produit WHERE id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});

        if (cursor.moveToFirst()) { // Si le produit existe

            // Récupération des valeurs depuis le Cursor
            String nom = cursor.getString(cursor.getColumnIndexOrThrow("nom"));
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
            produit = new Produit(nom, image, proteine, glucide,calorie, energiekj, sel, sodium, sucre,
                    matieregrasse, matieregrassesature, nutriscore, ingrediants, allergenes
            );
        }

        cursor.close();
        return produit;
    }

    public Produit getProductById100g(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Produit produit = null;

        // Requête pour récupérer le produit par son nom
        String query = "SELECT * FROM produit100g WHERE id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});

        if (cursor.moveToFirst()) { // Si le produit existe

            // Récupération des valeurs depuis le Cursor
            String nom = cursor.getString(cursor.getColumnIndexOrThrow("nom"));
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
            produit = new Produit(nom, image, proteine, glucide,calorie, energiekj, sel, sodium, sucre,
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

    public long getProduitIdByName100g(String nomProduit) {
        SQLiteDatabase db = this.getReadableDatabase();
        long id = -1;

        Cursor cursor = db.rawQuery(
                "SELECT id FROM produit100g WHERE nom = ?",
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

    public Cursor getAllProducts100g() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_PRODUIT_100g, null);
    }

    public Cursor getAllProductsCalendrier() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_CALENDRIER, null);
    }

    public ArrayList<Long> getProduitCalendrier(String date, String repas) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Long> id = new ArrayList<>();


        Cursor cursor = db.rawQuery(
                "SELECT * FROM calendrier WHERE TRIM(date) = TRIM(?) AND TRIM(repas) = TRIM(?)",
                new String[]{date, repas}
        );


        if (cursor.moveToFirst()) {
            do {

                String date2 = cursor.getString(cursor.getColumnIndexOrThrow("date"));
                String repas2 = cursor.getString(cursor.getColumnIndexOrThrow("repas"));


                System.out.println("date:" + date);
                System.out.println("repas:" + repas);
                System.out.println("date2:" + date2);
                System.out.println("repas2:" + repas2);

                System.out.println("egaledate" + date.equals(date2));
                System.out.println("egalerepas" + repas.equals(repas2));


                long idProduit = cursor.getLong(cursor.getColumnIndexOrThrow(COL_ID_PRODUITS));
                System.out.println("id:" + idProduit);
                id.add(idProduit);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return id;
    }

}
