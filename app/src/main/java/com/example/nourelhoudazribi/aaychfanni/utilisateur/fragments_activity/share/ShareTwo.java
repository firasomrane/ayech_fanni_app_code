package com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.share;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nourelhoudazribi.aaychfanni.R;
import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.Utils.FirebaseMethods;
import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.fragments_activity.theEssentialActivity;
import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.models.UserSettings;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by ASUS on 29/11/2017.
 */

public class ShareTwo extends AppCompatActivity {

    private static final String TAG = "ShareTwo";

    ///wiggets
    private TextView publier;
    private RadioButton radioButton;
    private RadioGroup radioGroup;

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseMethods mFirebaseMethods;


    //vars
    private String mAppend = "file:/" ,shareType,urlEntered,imageUrl ,selectedTitle ,selectedDescription;
    private int postCount = 0;
    private UserSettings mUserSettings;
    private Intent intent;
    private Bitmap bitmap;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_two_activity_layout);

        mFirebaseMethods = new FirebaseMethods(ShareTwo.this);
        setupFirebaseAuth();
        ImageView backArrow = (ImageView) findViewById(R.id.m_icon);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: closing the activity");
                finish();
            }
        });

        //get the radioButtons result and share the new post
        publier = (TextView) findViewById(R.id.publier) ;
        publier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBundlesFromOne();
                sharePost();
            }
        });

    }




    ///get the bundles send from ShareOne
    private void getBundlesFromOne() {
        Log.d(TAG, "getBundlesFromOne: activated");

        intent = getIntent();
        //get the bundle
        Bundle  bundle=this.getIntent().getExtras();

        if(bundle !=null) {
            //Log.d(TAG, "getBundlesFromOne: bundle not null");

            ///if a string is passes not a bitmap
            if(intent.hasExtra(getString(R.string.selected_image))) {
                imageUrl = bundle.getString(getString(R.string.selected_image));
                //Log.d(TAG, "sendBundlesToShareTwo: selectedimage   "+imageUrl);
            }
            else if(intent.hasExtra(getString(R.string.selected_bitmap))){
                Log.d(TAG, "getBundlesFromOne: bitmap selected");
            }

            urlEntered = bundle.getString(getString(R.string.selected_url));
            //Log.d(TAG, "sendBundlesToShareTwo: selectedurl   "+urlEntered);

            selectedTitle = bundle.getString(getString(R.string.selected_title));
            //Log.d(TAG, "sendBundlesToShareTwo: selectedtitle  "+selectedTitle);

            selectedDescription = bundle.getString(getString(R.string.selected_description));
            //Log.d(TAG, "sendBundlesToShareTwo: selectedDescription   "+selectedDescription);
        }

        else{
            Log.d(TAG, "getBundlesFromOne: bundle  null");
        }
    }



    //share the post
    public void sharePost() {

        radioGroup = (RadioGroup) findViewById(R.id.post_target_radio_group);

        // get selected radio button from radioGroup
        int selectedId = radioGroup.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        radioButton = (RadioButton) findViewById(selectedId);

        shareType = radioButton.getText().toString();

        //Toast.makeText(ShareTwo.this, shareType, Toast.LENGTH_SHORT).show();

        //upload the image to firebase
        Toast.makeText(ShareTwo.this, "Attempting to upload new post", Toast.LENGTH_SHORT).show();
        //Log.d(TAG, "sharePost:  selectedTitle " + selectedTitle);
       // Log.d(TAG, "sharePost:  selectedDescription " + selectedDescription);


        if(intent.hasExtra(getString(R.string.selected_image))) {

            //if there is an image selected upload the new image
            if (!imageUrl.equals("")) {
                Log.d(TAG, "sharePost: image url not nul " + imageUrl);
                mFirebaseMethods.uploadNewPhoto(getString(R.string.new_photo), urlEntered, shareType, selectedTitle, selectedDescription, postCount, imageUrl, null);

            } else {

                Log.d(TAG, "sharePost: image url empty " + imageUrl);
                mFirebaseMethods.addPostToDatabase(urlEntered, shareType, selectedTitle, selectedDescription, imageUrl, mUserSettings);

            }
        }
        else if(intent.hasExtra(getString(R.string.selected_bitmap))){
            bitmap = (Bitmap) intent.getParcelableExtra(getString(R.string.selected_bitmap));
            mFirebaseMethods.uploadNewPhoto(getString(R.string.new_photo), urlEntered, shareType, selectedTitle, selectedDescription, postCount, null, bitmap);
            mFirebaseMethods.addPostToDatabase(urlEntered, shareType, selectedTitle, selectedDescription, "", mUserSettings);
        }
        //navigate to the main feed so the user can see their photo
        Intent intent = new Intent(ShareTwo.this, theEssentialActivity.class);
        finish();
        startActivity(intent);

    }



    ///the plan
    private void someMethod(){
        /*
            Step 1)
            Create a data model for Photos
            Step 2)
            Add properties to the Photo Objects (caption, date, imageUrl, photo_id, tags, user_id)
            Step 3)
            Count the number of photos that the user already has.
            Step 4)
            a) Upload the photo to Firebase Storage
            b) insert into 'photos' node
            c) insert into 'user_photos' node
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
        Log.d(TAG, "onDataChange: image count: " + postCount);

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


                postCount = mFirebaseMethods.getPostCount(dataSnapshot);
                postCount = mFirebaseMethods.getPostCount(dataSnapshot);
                Log.d(TAG, "onDataChange: post count: " + postCount);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange: addListenerForSingleValueEvent       activated");

                //retrieve user information from the database

                mUserSettings = mFirebaseMethods.getUserSettings(dataSnapshot);

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
