package com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.compte;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.nourelhoudazribi.aaychfanni.R;
import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.Utils.FirebaseMethods;
import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.models.UserSettings;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

/**
 * Created by ASUS on 08/12/2017.
 */

public class RechargerVotreSolde extends AppCompatActivity {

    private static final String TAG = "RechargerVotreSolde";


    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseMethods mFirebaseMethods;
    private String userID;


    private ImageView retour;
    private EditText codeEditText;
    private Button valider;
    private String codeText;
    private int numberOfAttempts;
    private Boolean codeExists;
    private UserSettings mCurrentUserSettings;
    public long currentUserArgent ,newUserArgent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_recharger_solde);

        //set the user
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        if(mAuth.getCurrentUser() != null){
            userID = mAuth.getCurrentUser().getUid();
        }
        setupFirebaseAuth();

        mFirebaseMethods = new FirebaseMethods(RechargerVotreSolde.this);

        valider = (Button) findViewById(R.id.recharger_votre_solde);
        codeEditText = (EditText) findViewById(R.id.code);

        codeExists = false;
        numberOfAttempts = 0;
        codeText = "";

        retour = (ImageView) findViewById(R.id.back_arrow);
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(numberOfAttempts <3){

                    numberOfAttempts ++;
                    codeText = codeEditText.getText().toString();
                    if(codeText.length()>0){
                        verifyCode();
                    }
                    else{
                        Toast.makeText(RechargerVotreSolde.this, "Il faut saisir un code", Toast.LENGTH_SHORT).show();
                    }

                }

                else
                {
                    Toast.makeText(RechargerVotreSolde.this, "Vous avez usé les 3 tentatives permises. Veuillez attendre 90s pour une nouvelle", Toast.LENGTH_SHORT).show();
                    final long l = System.currentTimeMillis();
                        valider.setVisibility(View.INVISIBLE);
                       // finish();
                    /* final Handler handler = new Handler();
                    final Runnable r= new Runnable(){
                        @Override
                        public void run(){


                                codeEditText.setFocusable(true);
                            Toast.makeText(RechargerVotreSolde.this, "Nour", Toast.LENGTH_SHORT).show();
                            handler.postDelayed(this, 10); //This runs the code again
                        }
                    };
                    handler.postDelayed(r, 1);
                    codeEditText.setFocusable(false);*/
                   // numberOfAttempts=0;
                    //finish();
                }

            }
        });




    }

    private void verifyCode(){
        Log.d(TAG, "verifyCode: created");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        codeExists = false;


        Query query = reference
                .child(getString(R.string.dbname_codes));
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                    Log.d(TAG, "onDataChange: found user: " +singleSnapshot.child("code_id").getValue().toString());

                    //mFollowing.add(singleSnapshot.child(getString(R.string.field_user_id)).getValue().toString());

                    if(codeText.equals(singleSnapshot.child("code_id").getValue().toString())){
                        codeExists = true;
                        deleteCodeAndAddMoney();
                    }

                }

                if(!codeExists){
                    Toast.makeText(RechargerVotreSolde.this, "code erroné", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void deleteCodeAndAddMoney(){
        Log.d(TAG, "deleteCodeAndAddMoney: created");

        currentUserArgent = mCurrentUserSettings.getUser().getArgent();
        newUserArgent = currentUserArgent +10 ;

        myRef.child(getString(R.string.dbname_users))
                .child(userID)
                .child("argent")
                .setValue(newUserArgent);

        myRef.child(getString(R.string.dbname_codes))
                .child(codeText)
                .removeValue();

        Toast.makeText(RechargerVotreSolde.this, "votre recharge de 10 DT est réussie !", Toast.LENGTH_SHORT).show();

        finish();


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
                    userID = firebaseAuth.getCurrentUser().getUid();
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

                //retrieve creator  informations from the database
               if(userID != null){

                    mCurrentUserSettings = mFirebaseMethods.getCreatorSettings(dataSnapshot ,userID);
                    //then determine the new numbers
                }





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
