package com.example.nourelhoudazribi.aaychfanni;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class Demande_de_verification extends AppCompatActivity {

    private Button retour,envoie;
    CheckBox B1,B2,B3,B4,B5,B6,B7;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demande_de_verification);
        B1 = (CheckBox) findViewById(R.id.checkBox);
        B2 = (CheckBox) findViewById(R.id.checkBox2);
        B3 = (CheckBox) findViewById(R.id.checkBox3);
        B4 = (CheckBox) findViewById(R.id.checkBox4);
        B5 = (CheckBox) findViewById(R.id.checkBox5);
        B6 = (CheckBox) findViewById(R.id.checkBox6);
        B7 = (CheckBox) findViewById(R.id.checkBox7);
        retour = (Button) findViewById(R.id.back);
        envoie = (Button) findViewById(R.id.envoyer);
        envoie.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                /// Create Intent for SignUpActivity abd Start The Activity
                Toast.makeText(Demande_de_verification.this, "Votre demande a été envoyée !", Toast.LENGTH_LONG).show();
            }
        });
        retour.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                /// Create Intent for SignUpActivity abd Start The Activity
                finish();
            }
        });

    }
}
