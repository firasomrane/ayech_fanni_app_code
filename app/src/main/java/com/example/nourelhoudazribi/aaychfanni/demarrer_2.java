package com.example.nourelhoudazribi.aaychfanni;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class demarrer_2 extends AppCompatActivity {

    Button avance;
    Button recule;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demarrer_2);
        avance= (Button) findViewById(R.id.button9) ;
        recule= (Button) findViewById(R.id.button8) ;
        avance.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                /// Create Intent for SignUpActivity abd Start The Activity
                Intent intentSignUP = new Intent(getApplicationContext(), demerrer_3.class);
                finish();
                startActivity(intentSignUP);
            }
        });
        recule.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                /// Create Intent for SignUpActivity abd Start The Activity
                Intent intentSignUP = new Intent(getApplicationContext(), demarrer_1.class);
                finish();
                startActivity(intentSignUP);
            }
        });


    }
}
