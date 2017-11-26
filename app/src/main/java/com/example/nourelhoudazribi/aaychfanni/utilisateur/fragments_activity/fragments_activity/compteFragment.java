package com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.fragments_activity;


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
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.nourelhoudazribi.aaychfanni.MainActivity;
import com.example.nourelhoudazribi.aaychfanni.R;
import com.example.nourelhoudazribi.aaychfanni.devenir_createur;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by ASUS on 16/11/2017.
 */

public class compteFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "compteFragment";

    private Button devenirCreateur, betterExperience;
    private RelativeLayout relativeLayoutForBetterExperience, relativeLayoutMain;

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: completed");
        View rootViewtTwO = inflater.inflate(R.layout.compte_fragment, container, false);

        devenirCreateur = (Button) rootViewtTwO.findViewById(R.id.become_creator);
        devenirCreateur.setOnClickListener(this);

        //set the clickListener when the user is not logged in

        betterExperience = (Button) rootViewtTwO.findViewById(R.id.better_experience_button);
        betterExperience.setOnClickListener(this);

        //check if the user is logged in or not to choose which layout to show
        setupFirebaseAuth(rootViewtTwO);

        Log.d(TAG, "onCreateView: rootView");
        return rootViewtTwO;
    }

    @Override
    public void onClick(View v) {
        // handling onClick Events
        switch (v.getId()) {

            case R.id.become_creator:
                // code for button when user clicks devenir createur.
                Intent intent = new Intent(getActivity(), devenir_createur.class);
                startActivity(intent);

                break;

            case R.id.better_experience_button:
                // code for button when user clicks devenir createur.
                Intent intent2 = new Intent(getActivity(), MainActivity.class);
                startActivity(intent2);

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
     * checks to see if the @param 'user' is logged in
     *
     * @param user
     */
    private void checkCurrentUser(FirebaseUser user, android.view.View rootView) {
        Log.d(TAG, "checkCurrentUser: checking if user is logged in.");

        relativeLayoutForBetterExperience = (RelativeLayout) rootView.findViewById(R.id.subscribe_for_a_better_experience);
        relativeLayoutMain = (RelativeLayout) rootView.findViewById(R.id.compte_fragment_relative_layout);

        if (user == null) {
            //this is used to change the activity if the user is not logged in
            /*Intent intent = new Intent(this.getActivity(), MainActivity.class);
            startActivity(intent);*/

            //If the user is not logged in we will choose to set the visibility in the layout

            relativeLayoutForBetterExperience.setVisibility(View.VISIBLE);
            relativeLayoutMain.setVisibility(View.GONE);
        } else {
            relativeLayoutForBetterExperience.setVisibility(View.GONE);
            relativeLayoutMain.setVisibility(View.VISIBLE);
        }
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

                //check if the user is logged in
                checkCurrentUser(user, rootViewOne);

                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    //toastMessage("Successfully signed in with: " + user.getEmail());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    // toastMessage("Successfully signed out.");
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