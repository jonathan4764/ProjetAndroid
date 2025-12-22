package com.example.myapplication;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ModifProfilActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("TAG", "passage dans le onCreate");
        EdgeToEdge.enable(this);
        setContentView(R.layout.modifinfoutilisateuractivity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Spinner spinner = findViewById(R.id.spinnerActivite);
        Button bntEnregister = findViewById(R.id.btnEnregistrer);
        EditText poids = findViewById(R.id.editPoids);
        EditText age = findViewById(R.id.editAge);
        EditText taille = findViewById(R.id.editTaille);
        RadioGroup radioSexe = findViewById(R.id.radioSexe);

        bntEnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validerFormulaire(poids, age, taille,spinner,radioSexe)){

                    String sexe = "";
                    double poids2 = Double.parseDouble(poids.getText().toString());
                    double taille2 = Double.parseDouble(taille.getText().toString());
                    int age2 = Integer.parseInt(age.getText().toString());
                    String activite2 = spinner.getSelectedItem().toString();
                    int selectedId = radioSexe.getCheckedRadioButtonId();

                    if (selectedId == R.id.radioHomme) {
                        System.out.println("Sexe: Homme");
                        sexe = "Homme";
                    } else if (selectedId == R.id.radioFemme) {
                        System.out.println("Sexe: Femme");
                        sexe = "Femme";
                    }

                    Helper helper = Helper.getInstance(ModifProfilActivity.this);

                    helper.insertUser(sexe,age2,poids2,taille2,activite2);

                    // Lecture
                    Cursor cursor = helper.getAllUsers();
                    while (cursor.moveToNext()) {
                        int id = cursor.getInt(0);
                        String sexe2 = cursor.getString(1);
                        double poids = cursor.getDouble(2);
                        double taille = cursor.getDouble(3);
                        int age = cursor.getInt(4);
                        String activite = cursor.getString(5);

                        System.out.println(id + " " + sexe2 + " " + age + " " +taille+ " " +poids+ " " +activite);
                    }
                    cursor.close();


                    Toast.makeText(ModifProfilActivity.this, "Paramètres enregistrés", Toast.LENGTH_SHORT).show();
                    System.out.println("Poids:" + poids.getText().toString());
                    System.out.println("Taille:" + taille.getText().toString());
                    System.out.println("Age:" + age.getText().toString());
                    System.out.println("Activité:" + spinner.getSelectedItem().toString());

                    Intent intent = new Intent(ModifProfilActivity.this, RapportJourneeActivity.class);
                    startActivity(intent);


                }
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.niveau_activite,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


    }

    private boolean validerFormulaire(EditText poids,EditText age,EditText taille,Spinner spinner,RadioGroup radioSexe) {

        if (age.getText().toString().trim().isEmpty()) {
            age.setError("Champ obligatoire");
            return false;
        }

        if (taille.getText().toString().trim().isEmpty()) {
            taille.setError("Champ obligatoire");
            return false;
        }

        if (poids.getText().toString().trim().isEmpty()) {
            poids.setError("Champ obligatoire");
            return false;
        }

        if (radioSexe.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Sélectionnez le sexe", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (spinner.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Choisissez un niveau d'activité", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}


