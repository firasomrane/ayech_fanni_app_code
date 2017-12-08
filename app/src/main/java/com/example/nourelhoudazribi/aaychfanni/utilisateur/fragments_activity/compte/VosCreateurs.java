package com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.compte;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.nourelhoudazribi.aaychfanni.R;
import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.Utils.FirebaseMethods;
import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.accueil.MainfeedListAdapter;
import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.models.VosCreateurElement;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ASUS on 07/12/2017.
 */

public class VosCreateurs extends AppCompatActivity implements
        MainfeedListAdapter.OnLoadMoreItemsListener {

    @Override
    public void onLoadMoreItems() {

        displayMorePhotos();

    }

    private static final String TAG = "VosCreateurs";

    private ListView mListView;
    private ImageView backArrow;

    //add Firebase Database stuff
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private  String userID;
    private FirebaseMethods mFirebaseMethods;

    private ArrayList<String> mVosCreators;
    private ArrayList<VosCreateurElement> mPaginatedPhotos;
    private ArrayList<VosCreateurElement> mCreateursElements;
    public String lastUserId;
    private int mResults;
    private VosCreateursListAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_vos_createurs);

        mListView = (ListView) findViewById(R.id.vos_createurs_list_view);
        mVosCreators = new ArrayList<>();
        mCreateursElements = new ArrayList<>();

        //set the user
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        /*if(mAuth.getCurrentUser() != null){

        }*/
        mFirebaseMethods = new FirebaseMethods(VosCreateurs.this);
        backArrow = (ImageView) findViewById(R.id.m_icon) ;

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        setupFirebaseAuth();
    }



    ///get the posts that have the chosen category
    private void getUserCreators(){
        Log.d(TAG, "getUserCreators: created");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        mVosCreators = new ArrayList<>();

        Query query = reference
                .child(getString(R.string.dbname_following))
                .child(userID);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                    Log.d(TAG, "onDataChange: found user: " +singleSnapshot.child("user_id").getValue().toString());

                    //mFollowing.add(singleSnapshot.child(getString(R.string.field_user_id)).getValue().toString());
                    mVosCreators.add(singleSnapshot.child("user_id").getValue().toString());

                }

                //get the photos
                getCreatorCaracteristics();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void getCreatorCaracteristics(){
        Log.d(TAG, "getPhotos: getting photos");
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Log.d(TAG, "getCreatorCaracteristics: mVosCreateurs "+mVosCreators);

        for(int i = 0; i < mVosCreators.size(); i++){

            final int count = i;
            //Log.d(TAG, "getCreatorCaracteristics: " +mVosCreators.get(i));
            Query query = reference
                    .child(getString(R.string.dbname_user_account_settings))
                    .orderByChild(getString(R.string.field_user_id))
                    .equalTo(mVosCreators.get(i));
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {


                    for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){

                        Log.d(TAG, "onDataChange: singleSnapshot = "+singleSnapshot );
                        VosCreateurElement creatorElement = new VosCreateurElement();
                        Map<String, Object> objectMap = (HashMap<String, Object>) singleSnapshot.getValue();
                        Log.d(TAG, "onDataChange: the hash map  for i = " +count  +objectMap );

                        //if the following is deferent from the lastone who is zzzzzzzzzzzzzzzzzz

                            //Log.d(TAG, "onDataChange: count <mFollowing.size()-1  et count =  " + count);

                            creatorElement.setCreator_image_path(objectMap.get(getString(R.string.profile_photo)).toString());
                            creatorElement.setCreator_name(objectMap.get(getString(R.string.field_username)).toString());
                            creatorElement.setCreator_id((objectMap.get(getString(R.string.field_user_id)).toString()));
                            mCreateursElements.add(creatorElement);
                            Log.d(TAG, "onDataChange:  mCreateursElements " + mCreateursElements);

                    }


                    if(count == mVosCreators.size() -1 ){
                        //display our photos
                        Log.d(TAG, "onDataChange: mFollowing.size - 1  == count " + (mVosCreators.size()-1));
                        Log.d(TAG, "onDataChange:mFollowing.size the posts table" + mCreateursElements.size());
                        displayCreators();
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    private void displayCreators(){


        mPaginatedPhotos = new ArrayList<>();
        if(mCreateursElements != null){
            try{
                /*int iterations = mCreateursElements.size();

                if(iterations > 40){
                    iterations = 40;
                }

                mResults = 40;
                for(int i = 0; i < iterations; i++){
                    mPaginatedPhotos.add(mCreateursElements.get(i));
                }
                Log.d(TAG, "onDataChange:mFollowing.size the mPaginatedPhotos     " + mPaginatedPhotos);*/

                mAdapter = new VosCreateursListAdapter(VosCreateurs.this, R.layout.account_vos_createurs_element, mCreateursElements);
                mListView.setAdapter(mAdapter);

            }catch (NullPointerException e){
                Log.e(TAG, "displayPhotos: NullPointerException: " + e.getMessage() );
            }catch (IndexOutOfBoundsException e){
                Log.e(TAG, "displayPhotos: IndexOutOfBoundsException: " + e.getMessage() );
            }
        }

    }

    public void displayMorePhotos(){
        Log.d(TAG, "displayMorePhotos: displaying more photos");

        try{

            if(mCreateursElements.size() > mResults && mCreateursElements.size() > 0){

                int iterations;
                if(mCreateursElements.size() > (mResults + 10)){
                    Log.d(TAG, "displayMorePhotos: there are greater than 10 more photos");
                    iterations = 10;
                }else{
                    Log.d(TAG, "displayMorePhotos: there is less than 10 more photos");
                    iterations = mCreateursElements.size() - mResults;
                }

                //add the new photos to the paginated results
                for(int i = mResults; i < mResults + iterations; i++){
                    mPaginatedPhotos.add(mCreateursElements.get(i));
                }
                mResults = mResults + iterations;
                mAdapter.notifyDataSetChanged();
            }
        }catch (NullPointerException e){
            Log.e(TAG, "displayPhotos: NullPointerException: " + e.getMessage() );
        }catch (IndexOutOfBoundsException e){
            Log.e(TAG, "displayPhotos: IndexOutOfBoundsException: " + e.getMessage() );
        }
    }









    public boolean notEmpty(ArrayList a) {
        return !a.isEmpty();
    }





    /**************************Firebase****************/

    /**
     * Setup the firebase auth object
     */
    private void setupFirebaseAuth(){
        Log.d(TAG, "setupFirebaseAuth: setting up firebase auth.");

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                //check if the user is logged in

                if (user != null) {
                    // User is signed in
                    userID = mAuth.getCurrentUser().getUid();
                    getUserCreators();

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
        Toast.makeText(VosCreateurs.this,message,Toast.LENGTH_SHORT).show();
    }
    
}
