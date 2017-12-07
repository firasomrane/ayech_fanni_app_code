package com.example.nourelhoudazribi.aaychfanni;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.Utils.FirebaseMethods;
import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.fragments_activity.theEssentialActivity;
import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.models.User;
import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.models.UserAccountSettings;
import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.models.UserSettings;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class signup_createur2 extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private static final String TAG = "signup_createur2";

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseMethods mFirebaseMethods;
    private String userID;

    //vars
    private UserSettings mUserSettings;
    private User user;
    private UserAccountSettings settings;
    public Boolean trasactionDone;


    Button termine;
    EditText targetSumEditText ;
    Spinner spinner;
    Button retour;

    private String youtubeUrl,description,emplacement,categorie;
    private long targetSum,currentUserArgent,newUserArgent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_createur2);

        mFirebaseMethods = new FirebaseMethods(signup_createur2.this);
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

        mUserSettings = null;
        user = null;
        settings = null;

        getIntents();

        termine= (Button) findViewById(R.id.button5);
        retour= (Button) findViewById(R.id.button6);
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /// Create Intent for SignUpActivity abd Start The Activity

                startActivity(new Intent(signup_createur2.this, devenir_createur.class));

            }

        });

        targetSumEditText = (EditText) findViewById(R.id.editText9);

        spinner= (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> myadappter = new ArrayAdapter<String>(signup_createur2.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.catégorie));
        myadappter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(myadappter);
        spinner.setOnItemSelectedListener(this);

        setupFirebaseAuth();


    }

    private void getIntents() {
        Log.d(TAG, "getIntents:created ");

        description = "";
        youtubeUrl ="";
        emplacement = "";
        Intent intent = getIntent();
        description = intent.getStringExtra("description");
        youtubeUrl = intent.getStringExtra("youtube_url_selected");
        emplacement = intent.getStringExtra("emplacemnt");
        Log.d(TAG, "setDonationDescription: creator description is  "+description);
        Log.d(TAG, "setDonationDescription: creator youtube url  is  "+youtubeUrl);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent , View view, int pos, long l) {
        String selected = parent.getItemAtPosition(pos).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }




    private void setTerminer(){
        Log.d(TAG, "setPyerListener: created");



        termine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                trasactionDone =false;
                currentUserArgent = user.getArgent();

                Log.d(TAG, "setTheNewArgent: money for currentUserArgent " +currentUserArgent);
                categorie = spinner.getSelectedItem().toString();
                Log.d(TAG, "onClick: categorie " + categorie);

                targetSum=Long.parseLong(targetSumEditText.getText().toString());
                Log.d(TAG, "onClick: targetSum is " + targetSum);



                if(currentUserArgent >= 10 &&!trasactionDone&&targetSum>0){

                    trasactionDone =true;
                    newUserArgent =currentUserArgent - 10;

                    Log.d(TAG, "setTheNewArgent: money for new currentUserArgent " +newUserArgent);
                    Log.d(TAG, "setTheNewArgent: money for new currentUserArgent is  " +userID);

                    mFirebaseMethods.devenirCreateur(categorie,description,targetSum,youtubeUrl,newUserArgent);
                    Toast.makeText(signup_createur2.this,"transaction réussite de 10",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(signup_createur2.this , theEssentialActivity.class);
                    finish();
                    startActivity(intent);
                    //intent to the profile activity
                }
                else{
                    Toast.makeText(signup_createur2.this,"une erreur a apparu",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }



    private void getUserInformations(UserSettings userSettings){
        /*Log.d(TAG, "setProfileWidgets: setting widgets with data retrieving from firebase database: " + userSettings.toString());
        Log.d(TAG, "setProfileWidgets: setting widgets with data retrieving from firebase database: " + userSettings.getUser().getEmail());
        Log.d(TAG, "setProfileWidgets: setting widgets with data retrieving from firebase database: " + userSettings.getUser().getPhone_number());

*/


    }















    /*
    ------------------------------------ Firebase ---------------------------------------------
     */

    /**
     * Setup the firebase auth object
     */
    private void setupFirebaseAuth(){
        Log.d(TAG, "setupFirebaseAuth: setting up firebase auth.");

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();


                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //retrieve user information from the database

                mUserSettings = mFirebaseMethods.getUserSettings(dataSnapshot);
                if(mUserSettings !=null){
                    user = mUserSettings.getUser();
                    settings = mUserSettings.getSettings();
                    setTerminer();
                }


                //retrieve images for the user in question

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


}

