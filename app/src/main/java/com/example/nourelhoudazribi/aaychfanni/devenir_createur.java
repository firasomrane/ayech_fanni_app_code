package com.example.nourelhoudazribi.aaychfanni;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.compte.compteFragment;
import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.fragments_activity.theEssentialActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class devenir_createur extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private static final String TAG = "devenir_createur";

    Button continuer;
    Button retour;
    private EditText descri;
   // private EditText empl;
    private EditText faceb;
    private EditText youtube;
    private EditText twitter;
    private EditText twitcher;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devenir_createur);
        continuer = (Button) findViewById(R.id.button4);
        retour = (Button) findViewById(R.id.arriere_createur);
        descri = (EditText) findViewById(R.id.editText4);
        spinner = (Spinner) findViewById(R.id.editText3);
        faceb = (EditText) findViewById(R.id.editText6);
        youtube = (EditText) findViewById(R.id.editText5);
        twitter = (EditText) findViewById(R.id.editText7);
        twitcher = (EditText) findViewById(R.id.editText8);
        firebaseAuth=FirebaseAuth.getInstance();

        ArrayAdapter<String> myadappter = new ArrayAdapter<String>(devenir_createur.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.localisation));
        myadappter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(myadappter);
        spinner.setOnItemSelectedListener(this);

        continuer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        /// Create Intent for SignUpActivity abd Start The Activity
                String descriptionText=descri.getText().toString();
                String emplacementText=spinner.getSelectedItem().toString();
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
                finish();
                startActivity(new Intent(devenir_createur.this, theEssentialActivity.class));
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos , long l) {
        String selected = parent.getItemAtPosition(pos).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
