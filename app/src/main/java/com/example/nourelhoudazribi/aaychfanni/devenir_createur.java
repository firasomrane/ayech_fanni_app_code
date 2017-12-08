package com.example.nourelhoudazribi.aaychfanni;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.fragments_activity.theEssentialActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class devenir_createur extends AppCompatActivity {
    private static final String TAG = "devenir_createur";

    Button continuer;
    Button retour;
    private EditText descri;
    private EditText empl;
    private EditText faceb;
    private EditText youtube;
    private EditText twitter;
    private EditText twitcher;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devenir_createur);
        continuer = (Button) findViewById(R.id.button4);
        retour = (Button) findViewById(R.id.arriere_createur);
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
                String descriptionText=descri.getText().toString();
                String emplacementText=empl.getText().toString();
                String facebookText=faceb.getText().toString();
                String youtubeText=youtube.getText().toString();
                String twitterText=twitter.getText().toString();
                String twitcherText=twitcher.getText().toString();
                if((TextUtils.isEmpty(descriptionText)))
                {
                    Toast.makeText(devenir_createur.this, "il faut indiquer une description", Toast.LENGTH_LONG).show();
                }
                else if((TextUtils.isEmpty(emplacementText)))
                {
                    Toast.makeText(devenir_createur.this, "il faut indiquer votre emplacement", Toast.LENGTH_LONG).show();
                }
                else if((TextUtils.isEmpty(facebookText))&&(TextUtils.isEmpty(youtubeText))
                        &&(TextUtils.isEmpty(twitcherText))&&(TextUtils.isEmpty(twitterText)))
                {
                    Toast.makeText(devenir_createur.this, "Il faut indiquer au moins un lien", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Intent intent = new Intent(devenir_createur.this, signup_createur2.class);
                    intent.putExtra("description", descriptionText);
                    intent.putExtra("emplacement", emplacementText);
                    intent.putExtra("facebook_url_selected", facebookText);
                    intent.putExtra("youtube_url_selected", youtubeText);
                    intent.putExtra("twitter_url_selected", twitterText);
                    intent.putExtra("twitcher_url_selected", twitcherText);
                    startActivity(intent);
                }

            }

        });
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(devenir_createur.this, theEssentialActivity.class));
            }
        });

    }
}
