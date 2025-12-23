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



public class ModifProfil2 extends AppCompatActivity {
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

        Button  button2 = findViewById(R.id.btnCalandrier);
        Button  button3 = findViewById(R.id.btnRepas);
        Button  button4 = findViewById(R.id.btnHistorique);


        Spinner spinner = findViewById(R.id.spinnerActivite);
        Button bntEnregister = findViewById(R.id.btnEnregistrer);
        EditText poids = findViewById(R.id.editPoids);
        EditText age = findViewById(R.id.editAge);
        EditText taille = findViewById(R.id.editTaille);
        RadioGroup radioSexe = findViewById(R.id.radioSexe);

        Helper helper = Helper.getInstance(ModifProfil2.this);
        Utilisateur utilisateur = new Utilisateur();

        Cursor cursor = helper.getAllUsers();

        while (cursor.moveToNext()) {

            // Récupérer toutes les valeurs depuis le Cursor
            String sexe2 = cursor.getString(cursor.getColumnIndexOrThrow("sexe"));
            String poids2 = cursor.getString(cursor.getColumnIndexOrThrow("poids"));
            String taille2 = cursor.getString(cursor.getColumnIndexOrThrow("taille"));
            String age2 = cursor.getString(cursor.getColumnIndexOrThrow("age"));
            String activite2 = cursor.getString(cursor.getColumnIndexOrThrow("activite"));

            utilisateur.setActivite(activite2);
            utilisateur.setAge(Integer.parseInt(age2));
            utilisateur.setPoids(Double.parseDouble(poids2));
            utilisateur.setTaille(Double.parseDouble(taille2));
            utilisateur.setSexe(sexe2);

        }

        cursor.close();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.niveau_activite,
                R.layout.spinner_selected_white
        );

        adapter.setDropDownViewResource(R.layout.spinner_dropdown_white);
        spinner.setAdapter(adapter);

        int position = adapter.getPosition(utilisateur.getActivite());
        if (position >= 0) {
            spinner.setSelection(position);
        }

        poids.setText(String.valueOf(utilisateur.getPoids()));
        age.setText(String.valueOf(utilisateur.getAge()));
        taille.setText(String.valueOf(utilisateur.getTaille()));



        if(utilisateur.getSexe().equals("Homme")){
            radioSexe.check(R.id.radioHomme);
        }else{
            radioSexe.check(R.id.radioFemme);
        }

        bntEnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                utilisateur.setPoids(Double.parseDouble(poids.getText().toString()));
                utilisateur.setAge(Integer.parseInt(age.getText().toString()));
                utilisateur.setTaille(Double.parseDouble(taille.getText().toString()));

                int selectedSexId = radioSexe.getCheckedRadioButtonId();
                if (selectedSexId == R.id.radioHomme) {
                    utilisateur.setSexe("Homme");
                } else {
                    utilisateur.setSexe("Femme");
                }

                utilisateur.setActivite(spinner.getSelectedItem().toString());

                // Mettre à jour la base
                helper.updateUser(utilisateur);

                Toast.makeText(ModifProfil2.this, "Modification enregistrée", Toast.LENGTH_SHORT).show();

                // Relancer l'activité pour rafraîchir l'affichage
                Intent intent = new Intent(ModifProfil2.this, ModifProfil2.class);
                startActivity(intent);
                finish();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ModifProfil2.this,RapportJourneeActivity.class);
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ModifProfil2.this,CalandrierActivity.class);
                startActivity(intent);
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ModifProfil2.this,AnalyseAliment.class);
                startActivity(intent);
            }
        });

}
}
