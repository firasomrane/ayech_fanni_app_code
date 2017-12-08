package com.example.nourelhoudazribi.aaychfanni;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.fragments_activity.theEssentialActivity;

public class demerrer_3 extends AppCompatActivity {
    Button avance;
    Button recule;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demerrer_3);
        avance= (Button) findViewById(R.id.button11) ;
        recule= (Button) findViewById(R.id.button10) ;
        avance.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                /// Create Intent for SignUpActivity abd Start The Activity
                Intent intentSignUP = new Intent(getApplicationContext(), theEssentialActivity.class);
                finish();
                startActivity(intentSignUP);
            }
        });
        recule.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                /// Create Intent for SignUpActivity abd Start The Activity
                Intent intentSignUP = new Intent(getApplicationContext(), demarrer_2.class);
                finish();
                startActivity(intentSignUP);
            }
        });
    }
}
