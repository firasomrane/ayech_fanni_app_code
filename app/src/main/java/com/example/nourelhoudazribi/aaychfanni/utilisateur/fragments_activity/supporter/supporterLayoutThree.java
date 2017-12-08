package com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.supporter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nourelhoudazribi.aaychfanni.R;
import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.Utils.FirebaseMethods;
import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.compte.profileActivity;
import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.models.UserSettings;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by ASUS on 19/11/2017.
 */

public class supporterLayoutThree extends supporterLayoutOne {

    private static final String TAG = "supporterLayoutThree";


    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseMethods mFirebaseMethods;
    private String userID;

    private String donationtype;
    private String montant;
    public String creator_user_id;

    private UserSettings mCreatorUserSettings,mCurrentUserSettings;

    private Button payer;
    public long newCreatorArgent ,newUserArgent ,currentCreatorArgent , currentUserArgent ,sumToAdd,currentCreatorFollowers,
    newCreatorFollowers;
    public Boolean trasactionDone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: created");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.supporter_layout_3);

        mFirebaseMethods = new FirebaseMethods(supporterLayoutThree.this);
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

        //get the intent
        creator_user_id="";
        Intent intent = getIntent();
        creator_user_id = intent.getStringExtra(getString(R.string.user_id));
        Log.d(TAG, "setDonationDescription: creator user id is  "+creator_user_id);

        //set widgets
        payer = (Button) findViewById(R.id.payer);

        setDonationDescription();
        setupFirebaseAuth();



    }

    public void setDonationDescription(){

        Bundle bundle=this.getIntent().getExtras();
        creator_user_id = "";

        if(bundle !=null) {

            //Handle the donation type
            donationtype = bundle.getString("donationType");
            Toast.makeText(supporterLayoutThree.this,
                     donationtype, Toast.LENGTH_SHORT).show();

            final TextView donation_type_text = (TextView) findViewById(R.id.supporter_donation_type);
            if(donationtype =="Un don par mois"){donation_type_text.setText("par mois");}
            else if (donationtype =="Un don chaque nouveau contenu"){donation_type_text.setText("par contenu");}
            else{donation_type_text.setText("seulement cette fois");}


            //Handle the montant value

            montant = bundle.getString("montant");
            final TextView donationSum = (TextView) findViewById(R.id.supporter_donation_sum);
            donationSum.setText(montant);

            ///handle the creatoruserçid

            creator_user_id = bundle.getString(getString(R.string.user_id));
            Log.d(TAG, "setDonationDescription: creator user id is  "+creator_user_id);
        }
        /*else {
            Toast.makeText(supporterLayoutThree.this,
                    "bundle null", Toast.LENGTH_SHORT).show();
        }*/

        //I have to set donationType to "" when I return to the first activity.



    }

    private void setPyerListener(){
        Log.d(TAG, "setPyerListener: created");



        payer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                trasactionDone =false;
                ///get the user and the creator user  settings

                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        //retrieve creator  informations from the database
                        if(creator_user_id != null){
                            mCreatorUserSettings = mFirebaseMethods.getCreatorSettings(dataSnapshot ,creator_user_id);
                            mCurrentUserSettings = mFirebaseMethods.getCreatorSettings(dataSnapshot ,userID);
                            //then determine the new numbers
                            setTheNewArgent();
                        }



                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                     //update to firebase

                //create new donation and add it to firebase with dates ==> tomorrow
            }
        });

    }

    private  void setTheNewArgent(){
        Log.d(TAG, "setTheNewArgent: created");

        currentCreatorFollowers = mCreatorUserSettings.getSettings().getFollowers();
        currentCreatorArgent = mCreatorUserSettings.getSettings().getDon_sum();
        currentUserArgent = mCurrentUserSettings.getUser().getArgent();

        Log.d(TAG, "setTheNewArgent: money for currentCreatorArgent " +currentCreatorArgent);
        Log.d(TAG, "setTheNewArgent: money for currentUserArgent " +currentUserArgent);

        //int i=5;
        //sumToAdd = "d";
        montant = montant.replace(" ","");
        montant = montant.split("\\.")[0];
        Log.d(TAG, "setTheNewArgent: montant = "+ montant);
        //String montant2 = montant.replaceAll("\\.", "");
        sumToAdd = Long.parseLong(montant);
        Log.d(TAG, "setTheNewArgent: money for sumToAdd " +sumToAdd);

        if(currentUserArgent >= sumToAdd &&!trasactionDone){

            trasactionDone =true;
            newCreatorArgent =currentCreatorArgent+ sumToAdd;
            newUserArgent =currentUserArgent - sumToAdd;
            newCreatorFollowers = currentCreatorFollowers +1;

            Log.d(TAG, "setTheNewArgent: money for new currentCreatorArgent " +newCreatorArgent);
            Log.d(TAG, "setTheNewArgent: money for new currentUserArgent " +newUserArgent);
            Log.d(TAG, "setTheNewArgent: money for new currentUserArgent is  " +userID);

            mFirebaseMethods.updateArgent(creator_user_id ,userID ,newCreatorArgent , newUserArgent ,newCreatorFollowers);
            Toast.makeText(supporterLayoutThree.this,"transaction réussite de " +sumToAdd,Toast.LENGTH_SHORT).show();
            mFirebaseMethods.ajouterNouveauDon(creator_user_id, userID, sumToAdd);
            Intent intent = new Intent(supporterLayoutThree.this , profileActivity.class);
            intent.putExtra(getString(R.string.user_id), creator_user_id);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra(getString(R.string.calling_activity),"one");
            finish();
            startActivity(intent);
            //intent to the profile activity
        }
        else{
            Toast.makeText(supporterLayoutThree.this,"vous n'avez pas assez d'argent",Toast.LENGTH_SHORT).show();
        }


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
                    userID = user.getUid();
                    setPyerListener();
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

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
