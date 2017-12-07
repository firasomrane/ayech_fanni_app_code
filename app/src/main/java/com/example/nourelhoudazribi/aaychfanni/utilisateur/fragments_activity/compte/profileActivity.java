package com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.compte;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.nourelhoudazribi.aaychfanni.R;
import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.Utils.FirebaseMethods;
import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.Utils.UniversalImageLoader;
import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.fragments_activity.theEssentialActivity;
import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.models.User;
import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.models.UserAccountSettings;
import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.models.UserSettings;
import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.supporter.supporterLayoutOne;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by ASUS on 02/12/2017.
 */

public class profileActivity extends AppCompatActivity {

    private static final String TAG = "profileActivity";

    private Button abonner ,supporter,dejaAbonne;
    private TextView username,description,nbreSupporteurs,targetSum,creatorSum,privileges;
    private ListView listView;
    private ImageView mProfilePhoto,mBackArrow;
    private ProgressBar progressBar;

    private UserSettings mCreatorUserSettings,mUserSettings;

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseMethods mFirebaseMethods;
    private String userID;
    public String creator_user_id;
    public Boolean isFollower;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profil_layout);

        mFirebaseMethods = new FirebaseMethods(profileActivity.this);
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        abonner = (Button) findViewById(R.id.abonner);
        dejaAbonne = (Button) findViewById(R.id.dejaAbonne);
        supporter = (Button) findViewById(R.id.supporter);
        username = (TextView) findViewById(R.id.username);
        description = (TextView) findViewById(R.id.description);
        nbreSupporteurs = (TextView) findViewById(R.id.supporteurs_number);
        targetSum = (TextView) findViewById(R.id.target_sum);
        creatorSum = (TextView) findViewById(R.id.money);
        privileges = (TextView) findViewById(R.id.privileges);
        listView = (ListView) findViewById(R.id.listView);
        mProfilePhoto = (ImageView) findViewById(R.id.profile_photo);
        mBackArrow = (ImageView) findViewById(R.id.back_arrow) ;

        mBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(profileActivity.this , theEssentialActivity.class);
                Bundle bundleToThree = new Bundle();
                bundleToThree.putString("frgToLoad", "one");
                intent.putExtras(bundleToThree);
                finish();
                startActivity(intent);
            }
        });

        getIncomingIntent();

        setupFirebaseAuth();


        //progressBar.setProgress(40);
    }



    private void getIncomingIntent(){
       // Log.d(TAG, "setProfileWidgets: created");

        Intent intent = getIntent();
        creator_user_id = "";
        creator_user_id = intent.getStringExtra(getString(R.string.user_id));
        Log.d(TAG, "getIncomingIntent: creator user id " +creator_user_id );
        getCreatorProfileWidgets();
    }


    private void setCreatorProfileWidgets(UserSettings userSettings){
        Log.d(TAG, "getCreatorProfileWidgets: created");

        Log.d(TAG, "setProfileWidgets: setting widgets with data retrieving from firebase database: " + userSettings.toString());
        Log.d(TAG, "setProfileWidgets: setting widgets with data retrieving from firebase database: " + userSettings.getSettings().getTarget_sum());
        //Log.d(TAG, "setProfileWidgets: setting widgets with data retrieving from firebase database: " + userSettings.getUser().getPhone_number());

        mCreatorUserSettings = userSettings;
        final User creatorUser = mCreatorUserSettings.getUser();
        UserAccountSettings creatorUserAccountSettings = userSettings.getSettings();

        //set the common widgets
        UniversalImageLoader.setImage(creatorUserAccountSettings.getProfile_photo(), mProfilePhoto, null, "");
        username.setText(creatorUserAccountSettings.getUsername());
        description.setText(creatorUserAccountSettings.getDescription());


        if(userID !=null){
            setTheAbonnerButton();
            supporter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(profileActivity.this , supporterLayoutOne.class);
                    intent.putExtra(getString(R.string.user_id), creator_user_id);
                    startActivity(intent);
                }
            });

        }



        nbreSupporteurs.setText(Long.toString(creatorUserAccountSettings.getFollowers()));
        targetSum.setText(Long.toString(creatorUserAccountSettings.getTarget_sum()));
        creatorSum.setText(Long.toString(creatorUserAccountSettings.getDon_sum()));
        Long pr;
        pr = creatorUserAccountSettings.getDon_sum() /creatorUserAccountSettings.getTarget_sum();

        progressBar.setProgress(pr.intValue());

        //setTheProfileListView();





    }


    private void setTheAbonnerButton(){
        Log.d(TAG, "setTheAbonnerButton: ");
        //search if the creator_user_id is in the following list of userId
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        dejaAbonne.setVisibility(View.GONE);
        abonner.setVisibility(View.VISIBLE);

        isFollower = false;
        Query query = reference
                .child(getString(R.string.dbname_following))
                .child(userID);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){

                    Log.d(TAG, "onDataChange: isFollwer or not? the snap     " +ds.child("user_id").getValue().toString() );

                    if(ds.child("user_id").getValue(String.class).toString().equals(creator_user_id)){
                        isFollower =true;
                        Log.d(TAG, "onDataChange: isFollwer or not?     " + isFollower.toString());
                        setTheFollowButton();
                        handleTheFollowClick();

                    }
                    else{
                        handleTheFollowClick();
                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    private void setTheFollowButton(){
        Log.d(TAG, "setTheFollowButton: ");
        //if he is follower
        if (isFollower){
            dejaAbonne.setVisibility(View.VISIBLE);
            abonner.setVisibility(View.GONE);

            dejaAbonne.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
        else {
            dejaAbonne.setVisibility(View.GONE);
            abonner.setVisibility(View.VISIBLE);
            abonner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }


    private void handleTheFollowClick(){
        Log.d(TAG, "handleTheFollowClick: created");

        if(isFollower){

            dejaAbonne.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myRef.child(getString(R.string.dbname_following))
                            .child(userID)
                            .child(creator_user_id)
                            .removeValue();

                    isFollower = false;
                    setTheFollowButton();
                }
            });

        }
        else{
            abonner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myRef.child(getString(R.string.dbname_following))
                    .child(userID)
                            .child(creator_user_id)
                            .child("user_id")
                            .setValue(creator_user_id);

                    isFollower = true;
                    setTheFollowButton();
                }
            });
        }
    }



    private void getCreatorProfileWidgets(){
        Log.d(TAG, "setCreatorProfileWidgets: created");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //retrieve creator  informations from the database
                mCreatorUserSettings = mFirebaseMethods.getCreatorSettings(dataSnapshot ,creator_user_id);
                setCreatorProfileWidgets(mCreatorUserSettings);

                //retrieve images for the user in question

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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
        //Log.d(TAG, "setupFirebaseAuth: setting up firebase auth.");


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

               // Log.d(TAG, "onAuthStateChanged: auth user is " + userID);

                if (user != null) {
                    userID = user.getUid();
                    getIncomingIntent();
                    // User is signed in
                   // Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                   // Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //retrieve user information from the database



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
