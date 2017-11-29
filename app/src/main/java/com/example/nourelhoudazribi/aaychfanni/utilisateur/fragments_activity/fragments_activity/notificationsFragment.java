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
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.nourelhoudazribi.aaychfanni.MainActivity;
import com.example.nourelhoudazribi.aaychfanni.R;
import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.Utils.FirebaseMethods;
import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.models.User;
import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.models.UserSettings;
import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.share.ShareOne;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by ASUS on 14/11/2017.
 */

public class notificationsFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "notificationsFragment";

    private Button betterExperience;
    private RelativeLayout relativeLayout,createPost;
    private ListView lv;

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
        View rootView=inflater.inflate(R.layout.notifications_fragment,container,false);
        lv= (ListView) rootView.findViewById(R.id.notifications_fragment_list);
        //set the user
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        if(mAuth.getCurrentUser() != null){
            userID = mAuth.getCurrentUser().getUid();
        }
        mFirebaseMethods = new FirebaseMethods(getActivity());

        //CustomAdapter adapter=new CustomAdapter(this.getActivity(),getExploreElements());
       // lv.setAdapter(adapter);

        //set the clickListener when the user is not logged in

        betterExperience =(Button) rootView.findViewById(R.id.better_experience_button);
        betterExperience.setOnClickListener(this);

        //check if the user is logged in or not to choose which layout to show
        setupFirebaseAuth(rootView);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        // handling onClick Events
        switch (v.getId()) {

            case R.id.better_experience_button:
                // code for button when user clicks devenir createur.
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);

                break;

            default:
                break;
        }
    }

    /*private ArrayList<elementExplore> getExploreElements() {
        ArrayList<elementExplore> explorerElements=new ArrayList<>();
        elementExplore newElement=new elementExplore("Aniamtion");
        explorerElements.add(newElement);
        newElement=new elementExplore("Comedie");
        explorerElements.add(newElement);
        newElement=new elementExplore("Third");
        explorerElements.add(newElement);
        newElement=new elementExplore("Ghost");
        explorerElements.add(newElement);
        return explorerElements;
    }*/
    @Override
    public String toString() {
        return "notifications";
    }




    /************************FIREBASE**********************/
    /**
     * checks to see if the @param 'user' is logged in
     * @param user
     */
    private void checkCurrentUser(FirebaseUser user, final android.view.View rootView){
        Log.d(TAG, "checkCurrentUser: checking if user is logged in.");

        relativeLayout = (RelativeLayout) rootView.findViewById(R.id.subscribe_for_a_better_experience) ;
        lv= (ListView) rootView.findViewById(R.id.notifications_fragment_list);

        if(user == null){
            //this is used to change the activity if the user is not logged in
            /*Intent intent = new Intent(this.getActivity(), MainActivity.class);
            startActivity(intent);*/

            //If the user is not logged in we will choose to set the visibility in the layout

            relativeLayout.setVisibility(View.VISIBLE);
            lv.setVisibility(View.GONE);
        }
        else{
            relativeLayout.setVisibility(View.GONE);
            lv.setVisibility(View.VISIBLE);

            //if the user is logged in fetch his profile informations
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    //checks if he hese creator and show remaining parts
                    setCreatorWidgets(mFirebaseMethods.getUserSettings(dataSnapshot),rootView);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    //set the apperance if the user is creator
    private void setCreatorWidgets(UserSettings userSettings , android.view.View rootView){
        Log.d(TAG, "setProfileWidgets: setting widgets with data retrieving from firebase database: " + userSettings.toString());
        Log.d(TAG, "setProfileWidgets: setting widgets with data retrieving from firebase database: " + userSettings.getUser().getEst_createur());

        User user = userSettings.getUser();
        //if the user is creator set the visivility of the remaining layout
        if(user.getEst_createur()){
            createPost =(RelativeLayout) rootView.findViewById(R.id.create_post);
            createPost.setVisibility(View.VISIBLE);

            createPost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity() , ShareOne.class);
                    startActivity(intent);
                }
            });
        }

    }


    /**
     * Setup the firebase auth object
     */
    private void setupFirebaseAuth(android.view.View rootView){
        Log.d(TAG, "setupFirebaseAuth: setting up firebase auth.");

        final View rootViewOne = rootView ;
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                //check if the user is logged in
                checkCurrentUser(user,rootViewOne);

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

    private void toastMessage(String message){
        Toast.makeText(this.getActivity(),message,Toast.LENGTH_SHORT).show();
    }
}
