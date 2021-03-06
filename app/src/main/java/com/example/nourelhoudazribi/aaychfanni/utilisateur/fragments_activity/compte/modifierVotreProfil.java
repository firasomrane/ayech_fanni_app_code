package com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.compte;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nourelhoudazribi.aaychfanni.Demande_de_verification;
import com.example.nourelhoudazribi.aaychfanni.R;
import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.Utils.FirebaseMethods;
import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.Utils.UniversalImageLoader;
import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.models.User;
import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.models.UserAccountSettings;
import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.models.UserSettings;
import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.share.TakePhoto;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.view.View.VISIBLE;
import static com.example.nourelhoudazribi.aaychfanni.R.id.website;

/**
 * Created by ASUS on 27/11/2017.
 */

public class modifierVotreProfil extends AppCompatActivity {

    //EditProfile Fragment widgets
    private EditText  mUsername, mWebsite, mDescription, mEmail, mPhoneNumber;
    private TextView mChangeProfilePhoto;
    private CircleImageView mProfilePhoto;
    private Boolean toastAppered ;
    private RelativeLayout relativeLayoutWebsite ,relativeLayoutDescription ,relativeLayoutPhoneNumber,
            signOutRelativeLayout, relativeVerified;

    //vars
    private UserSettings mUserSettings;

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseMethods mFirebaseMethods;
    private String userID;
    private Button demander;

    private static final String TAG = "modifierVotreProfil";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_modifier_votre_profil);

        mProfilePhoto = (CircleImageView) findViewById(R.id.profile_photo);
        demander=(Button) findViewById(R.id.demande);
       // mDisplayName = (EditText) findViewById(R.id.display_name);
        mUsername = (EditText) findViewById(R.id.username);
        mWebsite = (EditText) findViewById(website);
        mDescription = (EditText) findViewById(R.id.description);
        mEmail = (EditText) findViewById(R.id.email);
        mPhoneNumber = (EditText) findViewById(R.id.phoneNumber);
        mChangeProfilePhoto = (TextView) findViewById(R.id.changeProfilePhoto);
        mFirebaseMethods = new FirebaseMethods(modifierVotreProfil.this);
        relativeLayoutDescription = (RelativeLayout) findViewById(R.id.relative_lay_description);
        relativeLayoutPhoneNumber = (RelativeLayout) findViewById(R.id.relative_lay_phone_number);
        relativeLayoutWebsite = (RelativeLayout) findViewById(R.id.relative_lay_website);
        signOutRelativeLayout = (RelativeLayout) findViewById(R.id.relative_lay_log_out) ;
        relativeVerified = (RelativeLayout) findViewById(R.id.button_layout);

        setupFirebaseAuth();
        getIncomingIntent();



        //back arrow for navigating back to "ProfileActivity"
        ImageView backArrow = (ImageView) findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: navigating back to ProfileActivity");
                modifierVotreProfil.this.finish();
            }
        });

        ///save chages
        ImageView checkmark = (ImageView) findViewById(R.id.save_changes);
        checkmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: attempting to save changes.");
                saveProfileSettings();
                finish();
            }
        });


    }

    private void getIncomingIntent() {
        Intent intent = getIntent();

        //if there is an imageUrl attached as an extra, then it was chosen from the gallery/photo fragment
        if (intent.hasExtra(getString(R.string.selected_image))
                || intent.hasExtra(getString(R.string.selected_bitmap)) ){


            Log.d(TAG, "getIncomingIntent: New incoming imgUrl");
            if (intent.getStringExtra(getString(R.string.return_to_fragment)).equals(getString(R.string.modifierVotreProfilActivity))) {

                if(intent.hasExtra(getString(R.string.selected_image))) {
                    //set the new profile picture
                    FirebaseMethods firebaseMethods = new FirebaseMethods(modifierVotreProfil.this);
                    firebaseMethods.uploadNewPhoto(getString(R.string.profile_photo), "", "", "", null, 0,
                            intent.getStringExtra(getString(R.string.selected_image)),null);
                }
                else if(intent.hasExtra(getString(R.string.selected_bitmap))){
                    //set the new profile picture
                    FirebaseMethods firebaseMethods = new FirebaseMethods(modifierVotreProfil.this);
                    firebaseMethods.uploadNewPhoto(getString(R.string.profile_photo),"","" ,"",null, 0,null,
                            (Bitmap) intent.getParcelableExtra(getString(R.string.selected_bitmap)));
                }
            }
        }

        }


    /**
     * Retrieves the data contained in the widgets and submits it to the database
     * Before donig so it chekcs to make sure the username chosen is unqiue
     */
    private void saveProfileSettings(){
        //final String displayName = mDisplayName.getText().toString();
        final String username = mUsername.getText().toString();



        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //case1: the user did not change their username
                if(!mUserSettings.getUser().getUsername().equals(username)){

                    checkIfUsernameExists(username);
                }
                //case2: the user changed their username therefore we need to check for uniqueness
                /*else{

                }*/
                /**
                 * change the rest of the settings that do not require uniqueness
                 */

                if(mUserSettings.getUser().getEst_createur()) {
                    final String website = mWebsite.getText().toString();
                    final String description = mDescription.getText().toString();
                    //final String email = mEmail.getText().toString();
                    final long phoneNumber = Long.parseLong(mPhoneNumber.getText().toString());

                    toastAppered = false;
                    /*if (!mUserSettings.getSettings().getDisplay_name().equals(displayName)) {
                        //update displayname
                        mFirebaseMethods.updateUserAccountSettings(displayName, null, null, 0);
                        if (!toastAppered) {
                            Toast.makeText(modifierVotreProfil.this, "saved change.", Toast.LENGTH_SHORT).show();
                            toastAppered = true;
                        }
                    }*/
                    if (!mUserSettings.getSettings().getWebsite().equals(website)) {
                        //update website
                        mFirebaseMethods.updateUserAccountSettings(null, website, null, 0);
                        if (!toastAppered) {
                            Toast.makeText(modifierVotreProfil.this, "changement enregistré.", Toast.LENGTH_SHORT).show();
                            toastAppered = true;
                        }
                    }
                    if (!mUserSettings.getSettings().getDescription().equals(description)) {
                        //update description
                        mFirebaseMethods.updateUserAccountSettings(null, null, description, 0);
                        if (!toastAppered) {
                            Toast.makeText(modifierVotreProfil.this, "changement enregistré..", Toast.LENGTH_SHORT).show();
                            toastAppered = true;
                        }
                    }
                    if (!mUserSettings.getSettings().getProfile_photo().equals(phoneNumber)) {
                        //update phoneNumber
                        mFirebaseMethods.updateUserAccountSettings(null, null, null, phoneNumber);
                        if (!toastAppered) {
                            Toast.makeText(modifierVotreProfil.this, "changement enregistré.", Toast.LENGTH_SHORT).show();
                            toastAppered = true;
                        }
                    }
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    /**
     * Check is @param username already exists in teh database
     * @param username
     */
    private void checkIfUsernameExists(final String username) {
        Log.d(TAG, "checkIfUsernameExists: Checking if  " + username + " already exists.");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference
                .child(getString(R.string.dbname_users))
                .orderByChild(getString(R.string.field_username))
                .equalTo(username);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(!dataSnapshot.exists()){
                    //add the username
                    mFirebaseMethods.updateUsername(username);
                    Toast.makeText(modifierVotreProfil.this, "nom enregistré.", Toast.LENGTH_SHORT).show();

                }
                for(DataSnapshot singleSnapshot: dataSnapshot.getChildren()){
                    if (singleSnapshot.exists()){
                        Log.d(TAG, "checkIfUsernameExists: FOUND A MATCH: " + singleSnapshot.getValue(User.class).getUsername());
                        Toast.makeText(modifierVotreProfil.this, "Le nom est déja pris.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void setProfileWidgets(UserSettings userSettings){
        Log.d(TAG, "setProfileWidgets: setting widgets with data retrieving from firebase database: " + userSettings.toString());
        Log.d(TAG, "setProfileWidgets: setting widgets with data retrieving from firebase database: " + userSettings.getUser().getEmail());
        Log.d(TAG, "setProfileWidgets: setting widgets with data retrieving from firebase database: " + userSettings.getUser().getPhone_number());


        signOutRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                finish();
            }
        });

        mUserSettings = userSettings;
        User user = userSettings.getUser();
        UserAccountSettings settings = userSettings.getSettings();

        //set the common widgets
        UniversalImageLoader.setImage(settings.getProfile_photo(), mProfilePhoto, null, "");
        //mDisplayName.setText(settings.getDisplay_name());
        mUsername.setText(settings.getUsername());

        //if the user is creator
        if(user.getEst_createur()){

            mWebsite.setText(settings.getWebsite());
            mDescription.setText(settings.getDescription());
            //mEmail.setText(userSettings.getUser().getEmail());
            mPhoneNumber.setText(String.valueOf(userSettings.getUser().getPhone_number()));
            relativeLayoutWebsite.setVisibility(VISIBLE);
            relativeLayoutDescription.setVisibility(VISIBLE);
            relativeLayoutPhoneNumber.setVisibility(VISIBLE);
            relativeVerified.setVisibility(VISIBLE);
            demander.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    /// Create Intent for SignUpActivity abd Start The Activity
                    Intent intentSignUP = new Intent(getApplicationContext(), Demande_de_verification.class);
                    startActivity(intentSignUP);
                }
            });

        }
        /*else{

        }*/        //change profile_photo
        mChangeProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: changing profile photo");
                Intent intent = new Intent(modifierVotreProfil.this, TakePhoto.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //268435456
                startActivity(intent);
                finish();
            }
        });



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


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //retrieve user information from the database

                setProfileWidgets(mFirebaseMethods.getUserSettings(dataSnapshot));

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
