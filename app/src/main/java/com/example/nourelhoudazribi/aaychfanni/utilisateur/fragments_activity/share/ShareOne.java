package com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.share;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nourelhoudazribi.aaychfanni.R;
import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.Utils.FirebaseMethods;
import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.Utils.UniversalImageLoader;
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

public class ShareOne extends AppCompatActivity {

    private static final String TAG = "ShareOne";
    
    private TextView suivant;
    private RelativeLayout cameraRelativeLayout ,phoneImageRelativeLayout ,UrlRelativeLayout;
    private EditText postTitle, postDescription;
    private String urlEntered,imageUrl ,selectedTitle ,selectedDescription;
    private ImageView postImage,poubelle;
    private Boolean imageSelected;
    private LinearLayout selectedImageLinearLayout;

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseMethods mFirebaseMethods;

    //vars
    private String mAppend = "file:/";

    private static final int VERIFY_PERMISSIONS_REQUEST = 1;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        Log.d(TAG, "onCreate: created");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_one_activity_layout);

        setupFirebaseAuth();

        //set the layout widgets
        suivant = (TextView) findViewById(R.id.suivant);
        cameraRelativeLayout = (RelativeLayout) findViewById(R.id.camera_relative_layout);
        //phoneImageRelativeLayout = (RelativeLayout) findViewById(R.id.phone_image_relative_layout);
        UrlRelativeLayout = (RelativeLayout) findViewById(R.id.url_relative_layout);
        postTitle = (EditText) findViewById(R.id.post_title);
        postDescription =(EditText) findViewById(R.id.post_description);


        urlEntered="";
        setImage();
        //when clicking on suivant
        suivant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go to the ShareTwo activity and send the bundles
                sendBundlesToShareTwo();
            }
        });

        //when clicking on the UrlAdd icon
        UrlRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //begin the customDialog
                customDialog();
                //Toast.makeText(ShareOne.this,urlEntered,Toast.LENGTH_SHORT).show();
            }
        });

        //choose to put a photo in the post
        cameraRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShareOne.this, TakePhoto.class);
                startActivity(intent);
            }
        });

        //when clicking on the back arrow icon
        ImageView backArrow = (ImageView) findViewById(R.id.ivBackArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: closing the activity");
                finish();
            }
        });

        //set the image in the layout if a creator chooses to add an image to the new post

    }


    ///get to the shareTwo activity and put the bundles
    private void sendBundlesToShareTwo() {

        Log.d(TAG, "sendBundlesToShareTwo: activated");
        selectedTitle = postTitle.getText().toString();
        selectedDescription = postDescription.getText().toString();
        Log.d(TAG, "sendBundlesToShareTwo: selectedtitle  "+selectedTitle);
        Log.d(TAG, "sendBundlesToShareTwo: selectedDescription   "+selectedDescription);

        Intent intent = new Intent(ShareOne.this , ShareTwo.class);
        intent.putExtra(getString(R.string.selected_image), imageUrl);
        Log.d(TAG, "sendBundlesToShareTwo: selectedimage   "+imageUrl);

        intent.putExtra(getString(R.string.selected_url) ,urlEntered);
        Log.d(TAG, "sendBundlesToShareTwo: selectedurl   "+urlEntered);

        intent.putExtra(getString(R.string.selected_title) ,selectedTitle);
        Log.d(TAG, "sendBundlesToShareTwo: selectedtitle in bundle  "+selectedTitle);

        intent.putExtra(getString(R.string.selected_description) ,selectedDescription);
        Log.d(TAG, "sendBundlesToShareTwo: selectedDescription in bundle  "+selectedDescription);

        startActivity(intent);

    }

    /**
     * gets the image url from the incoming intent and displays the chosen image
     */
    private void setImage(){
        imageUrl = "";
        Intent intent = getIntent();
        postImage = (ImageView) findViewById(R.id.post_image);
        poubelle =(ImageView) findViewById(R.id.poubelle);

        //get the intent
        Bundle  bundle=this.getIntent().getExtras();
        //if(intent.getStringExtra(getString(R.string.selected_image)) !=""){
        if(bundle !=null) {
            Log.d(TAG, "setImage: "+bundle.getString(getString(R.string.selected_image)));
            imageUrl = bundle.getString(getString(R.string.selected_image));

            UniversalImageLoader.setImage(imageUrl, postImage, null, mAppend);
            postImage.setVisibility(View.VISIBLE);
            poubelle.setVisibility(View.VISIBLE);
            imageSelected = true ;

            poubelle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    postImage.setVisibility(View.GONE);
                    poubelle.setVisibility(View.GONE);
                    imageUrl = "";
                    Log.d(TAG, "setImage: imageUrl"+imageUrl);

                }
            });
        }
        else {
            //Log.d(TAG, "setImage: "+"vide  image url is " + imageUrl);
            postImage.setVisibility(View.GONE);
            poubelle.setVisibility(View.GONE);

        }


        Log.d(TAG, "setImage: image Url is "+imageUrl );

    }



    ///set the AlertDialog when typing the url
    private void customDialog() {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(ShareOne.this);
        View mView = getLayoutInflater().inflate(R.layout.url_custom_dialog, null);
        final EditText urlEditText = (EditText) mView.findViewById(R.id.url_edit_text);
        TextView UrlOk = (TextView) mView.findViewById(R.id.ok);
        TextView UrlFermer = (TextView) mView.findViewById(R.id.fermer);

        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();

        UrlOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!urlEditText.getText().toString().isEmpty()){
                    Toast.makeText(ShareOne.this,"successful",Toast.LENGTH_SHORT).show();
                    urlEntered = urlEditText.getText().toString();
                    dialog.dismiss();
                }else{
                    Toast.makeText(ShareOne.this, "fill the field", Toast.LENGTH_SHORT).show();
                }
            }
        });
        UrlFermer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    dialog.dismiss();
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
