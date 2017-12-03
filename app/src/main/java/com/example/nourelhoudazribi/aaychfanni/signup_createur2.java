package com.example.nourelhoudazribi.aaychfanni;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.fragments_activity.theEssentialActivity;

public class signup_createur2 extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    Button termine;
    EditText budget ;
    Spinner S;
    Button retour;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_createur2);
        termine= (Button) findViewById(R.id.button5);
        retour= (Button) findViewById(R.id.button6);
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/// Create Intent for SignUpActivity abd Start The Activity

                startActivity(new Intent(signup_createur2.this, devenir_createur.class));

            }

        });

        S= (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> myadappter = new ArrayAdapter<String>(signup_createur2.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.catégorie));
        myadappter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        S.setAdapter(myadappter);
        S.setOnItemSelectedListener(this);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent , View view, int pos, long l) {
        String selected = parent.getItemAtPosition(pos).toString();

        termine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/// Create Intent for SignUpActivity abd Start The Activity

                budget= (EditText) findViewById(R.id.editText9);
                final String bud=budget.getText().toString().trim();

            }

        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}

