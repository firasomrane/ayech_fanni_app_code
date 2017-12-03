package com.example.nourelhoudazribi.aaychfanni;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class devenir_createur extends AppCompatActivity {

    Button continuer;
    private EditText descri;
    private EditText empl;
    private EditText faceb;
    private EditText youtube;
    private EditText twitter;
    private EditText twitcher;
    private ProgressDialog progressDialog1;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devenir_createur);
        continuer = (Button) findViewById(R.id.button4);
        descri = (EditText) findViewById(R.id.editText4);
        empl = (EditText) findViewById(R.id.editText3);
        faceb = (EditText) findViewById(R.id.editText6);
        youtube = (EditText) findViewById(R.id.editText5);
        twitter = (EditText) findViewById(R.id.editText7);
        twitcher = (EditText) findViewById(R.id.editText8);
        firebaseAuth=FirebaseAuth.getInstance();
        continuer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/// Create Intent for SignUpActivity abd Start The Activity
                String des=descri.getText().toString().trim();
                String e=empl.getText().toString().trim();
                String fb=faceb.getText().toString().trim();
                String you=youtube.getText().toString().trim();
                String t=twitter.getText().toString().trim();
                String tw=twitcher.getText().toString().trim();
                if((TextUtils.isEmpty(des)))
                {
                    Toast.makeText(devenir_createur.this, "il faut indiquer une description", Toast.LENGTH_LONG).show();
                }
                else if((TextUtils.isEmpty(e)))
                {
                    Toast.makeText(devenir_createur.this, "il faut indiquer votre emplacement", Toast.LENGTH_LONG).show();
                }
                else if((TextUtils.isEmpty(fb))&&(TextUtils.isEmpty(you))&&(TextUtils.isEmpty(t))&&(TextUtils.isEmpty(tw)))
                {
                    Toast.makeText(devenir_createur.this, "Il faut indiquer au moins un lien", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Intent createur = new Intent(getApplicationContext(), signup_createur2.class);
                    startActivity(createur);
                }

            }

        });

    }
}
