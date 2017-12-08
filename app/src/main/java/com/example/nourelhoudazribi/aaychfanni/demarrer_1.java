package com.example.nourelhoudazribi.aaychfanni;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class demarrer_1 extends AppCompatActivity {

    Button avance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demarrer_1);
        avance= (Button) findViewById(R.id.button7) ;
        avance.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                /// Create Intent for SignUpActivity abd Start The Activity*
                Intent intentSignUP = new Intent(getApplicationContext(), demarrer_2.class);
                finish();
                startActivity(intentSignUP);

            }
        });
    }
}
