package com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.compte;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nourelhoudazribi.aaychfanni.MainActivity;
import com.example.nourelhoudazribi.aaychfanni.R;
import com.example.nourelhoudazribi.aaychfanni.devenir_createur;
import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.Utils.FirebaseMethods;
import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.Utils.UniversalImageLoader;
import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.messages.Messages;
import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.models.User;
import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.models.UserAccountSettings;
import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.models.UserSettings;
import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.share.ShareOne;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.nourelhoudazribi.aaychfanni.R.id.messages_relative_layout;
import static com.example.nourelhoudazribi.aaychfanni.R.id.vos_createurs_relative_layout;
import static com.example.nourelhoudazribi.aaychfanni.R.id.vos_supporteurs_relative_layout;

/**
 * Created by ASUS on 16/11/2017.
 */

public class compteFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "compteFragment";

    private Button devenirCreateur, betterExperience,rechargerVotreSolde;
    private RelativeLayout relativeLayoutForBetterExperience, relativeLayoutMain,modifierVotreProfil,createPost,
            relativeLayoutParametres,relativeLayoutVosCreateurs ,relativeLayoutMessages;

    private RelativeLayout vosSupporteurs;
    private TextView compteLayoutNameField;
    private ImageView mProfilePhoto;
    private ProgressBar mProgressBar;

    //add Firebase Database stuff
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private  String userID;
    private FirebaseMethods mFirebaseMethods;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: completed");
        View rootViewtTwO = inflater.inflate(R.layout.compte_fragment, container, false);

        //set the user
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        if(mAuth.getCurrentUser() != null){
            userID = mAuth.getCurrentUser().getUid();
        }
        mFirebaseMethods = new FirebaseMethods(getActivity());

        devenirCreateur = (Button) rootViewtTwO.findViewById(R.id.become_creator);
        devenirCreateur.setOnClickListener(this);


        //set the clickListener when the User is not logged in

        betterExperience = (Button) rootViewtTwO.findViewById(R.id.better_experience_button);
        betterExperience.setOnClickListener(this);

        /*//set the log out button
        logOut =(Button) rootViewtTwO.findViewById(R.id.se_deconnecter_compte);
        logOut.setOnClickListener(this);*/

        //set the progressbar
        mProgressBar = (ProgressBar) rootViewtTwO.findViewById(R.id.profileProgressBar);
        mProgressBar.setVisibility(View.GONE);

        //set the modifier votre profil clickListener
        modifierVotreProfil = (RelativeLayout) rootViewtTwO.findViewById(R.id.modifier_votre_profil_relative_layout) ;
        modifierVotreProfil.setOnClickListener(this);

        //set the parametres relative layout
        relativeLayoutParametres = (RelativeLayout)  rootViewtTwO.findViewById(R.id.parametres_relative_layout);
        relativeLayoutParametres.setOnClickListener(this);

        //set the vos createurs relative layout
        relativeLayoutVosCreateurs = (RelativeLayout)  rootViewtTwO.findViewById(vos_createurs_relative_layout);
        relativeLayoutVosCreateurs.setOnClickListener(this);


        //set the vos supporteurs relative layout
        relativeLayoutVosCreateurs = (RelativeLayout)  rootViewtTwO.findViewById(vos_supporteurs_relative_layout);
        relativeLayoutVosCreateurs.setOnClickListener(this);

        //set the vos supporteurs relative layout
        relativeLayoutMessages = (RelativeLayout)  rootViewtTwO.findViewById(messages_relative_layout);
        relativeLayoutMessages.setOnClickListener(this);

        //check if the User is logged in or not to choose which layout to show
        setupFirebaseAuth(rootViewtTwO);

        //set the name text from the firebase
        compteLayoutNameField =(TextView) rootViewtTwO.findViewById(R.id.compte_layout_name_field) ;

        //set the user profile image
        mProfilePhoto = (ImageView) rootViewtTwO.findViewById(R.id.profile_photo);
        ///setProfileImage();





        Log.d(TAG, "onCreateView: rootView");
        return rootViewtTwO;
    }

    ////set the user profile photo
    private void setProfileImage(){
        Log.d(TAG, "setProfileImage: setting profile photo.");
        String imgURL = "www.androidcentral.com/sites/androidcentral.com/files/styles/xlarge/public/article_images/2016/08/ac-lloyd.jpg?itok=bb72IeLf";
        UniversalImageLoader.setImage(imgURL, mProfilePhoto, mProgressBar, "https://");
    }


    @Override
    public void onClick(View v) {
        // handling onClick Events
        switch (v.getId()) {

            case R.id.become_creator:
                // code for button when User clicks devenir createur.
                Intent intent = new Intent(getActivity(), devenir_createur.class);
                startActivity(intent);

                break;

            case R.id.better_experience_button:
                // code for button when User clicks devenir createur.
                Intent intent2 = new Intent(getActivity(), MainActivity.class);
                startActivity(intent2);

                break;
            case R.id.modifier_votre_profil_relative_layout:
                Intent intent3 = new Intent(getActivity(), modifierVotreProfil.class);
                startActivity(intent3);
                break;

            case R.id.parametres_relative_layout:
                Intent intent4 = new Intent(getActivity(), Parametres.class);
                startActivity(intent4);
                break;

            case R.id.vos_createurs_relative_layout:
                Intent intent5 = new Intent(getActivity(), VosCreateurs.class);
                startActivity(intent5);
                break;

            case R.id.vos_supporteurs_relative_layout:
            Intent intent6 = new Intent(getActivity(), VosSupporteurs.class);
            startActivity(intent6);
            break;

            case R.id.messages_relative_layout:
                Intent intent7 = new Intent(getActivity(), Messages.class);
                startActivity(intent7);
                break;


            default:
                break;
        }
    }


    @Override
    public String toString() {
        return "layout_compte";
    }



/************************FIREBASE**********************/
    /**
     * checks to see if the @param 'User' is logged in
     *
     * @param user
     */
    private void checkCurrentUser(FirebaseUser user, final android.view.View rootView) {
        Log.d(TAG, "checkCurrentUser: checking if User is logged in.");

        relativeLayoutForBetterExperience = (RelativeLayout) rootView.findViewById(R.id.subscribe_for_a_better_experience);
        relativeLayoutMain = (RelativeLayout) rootView.findViewById(R.id.compte_fragment_relative_layout);

        if (user == null) {
            //this is used to change the activity if the User is not logged in
            /*Intent intent = new Intent(this.getActivity(), MainActivity.class);
            startActivity(intent);*/

            //If the User is not logged in we will choose to set the visibility in the layout

            relativeLayoutForBetterExperience.setVisibility(View.VISIBLE);
            relativeLayoutMain.setVisibility(View.GONE);
        } else {
            relativeLayoutForBetterExperience.setVisibility(View.GONE);
            relativeLayoutMain.setVisibility(View.VISIBLE);

            //if the user is logged in fetch his profile informations
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    // showData(dataSnapshot);

                    //checks if he hese creator and show remaining parts
                    setCreatorWidgets(mFirebaseMethods.getUserSettings(dataSnapshot),rootView);

                    //retrieve user information from the database
                    setProfileWidgets(mFirebaseMethods.getUserSettings(dataSnapshot));

                    //retrieve images for the user in question

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

    }

    //set the apperance if the user is creator
    private void setCreatorWidgets(UserSettings userSettings ,android.view.View rootView){
        Log.d(TAG, "setProfileWidgets: setting widgets with data retrieving from firebase database: " + userSettings.toString());
        Log.d(TAG, "setProfileWidgets: setting widgets with data retrieving from firebase database: " + userSettings.getUser().getEst_createur());

        User user = userSettings.getUser();
        vosSupporteurs =(RelativeLayout) rootView.findViewById(vos_supporteurs_relative_layout);
        createPost =(RelativeLayout) rootView.findViewById(R.id.create_post);
        //if the user is creator set the visivility of the remaining layout
        if(user.getEst_createur()){


            vosSupporteurs.setVisibility(View.VISIBLE);
            createPost.setVisibility(View.VISIBLE);
            devenirCreateur.setVisibility(View.GONE);

            createPost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity() , ShareOne.class);
                    startActivity(intent);
                }
            });
        }
        else {
            devenirCreateur.setVisibility(View.VISIBLE);
            vosSupporteurs.setVisibility(View.GONE);
            createPost.setVisibility(View.GONE);

        }

    }


        //set the general profile widgets
    private void setProfileWidgets(UserSettings userSettings){
        Log.d(TAG, "setProfileWidgets: setting widgets with data retrieving from firebase database: " + userSettings.toString());
        Log.d(TAG, "setProfileWidgets: setting widgets with data retrieving from firebase database: " + userSettings.getSettings().getUsername());


        //User user = userSettings.getUser();
        UserAccountSettings settings = userSettings.getSettings();

        UniversalImageLoader.setImage(settings.getProfile_photo(), mProfilePhoto, null, "");

        //mDisplayName.setText(settings.getDisplay_name());
        compteLayoutNameField.setText(settings.getUsername());

        if(userSettings.getUser().getEst_createur()){
            compteLayoutNameField.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), profileActivity.class);
                    intent.putExtra(getString(R.string.user_id), userID);
                    intent.putExtra(getString(R.string.calling_activity), "four");
                    startActivity(intent);
                }
            });
            mProfilePhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), profileActivity.class);
                    intent.putExtra(getString(R.string.user_id), userID);
                    intent.putExtra(getString(R.string.calling_activity), "four");

                    startActivity(intent);
                }
            });
        }


        /*mWebsite.setText(settings.getWebsite());
        mDescription.setText(settings.getDescription());
        mPosts.setText(String.valueOf(settings.getPosts()));
        mFollowing.setText(String.valueOf(settings.getFollowing()));
        mFollowers.setText(String.valueOf(settings.getFollowers()));
        mProgressBar.setVisibility(View.GONE);*/
    }


    /**
     * Setup the firebase auth object
     */
    private void setupFirebaseAuth(android.view.View rootView) {
        Log.d(TAG, "setupFirebaseAuth: setting up firebase auth.");

        final View rootViewOne = rootView;
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                //check if the User is logged in
                checkCurrentUser(user, rootViewOne);

                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    //toastMessage("Successfully signed in with: " + User.getEmail());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    // toastMessage("Successfully signed out.");

                    //if you want to change activity when the User is logged out
                    /*Intent intent = new Intent(getActivity(), LoginActivity.class);
                    //this is to clean the activities stack when the User clicks back arrow icon
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);*/
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

    private void toastMessage(String message) {
        Toast.makeText(this.getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}