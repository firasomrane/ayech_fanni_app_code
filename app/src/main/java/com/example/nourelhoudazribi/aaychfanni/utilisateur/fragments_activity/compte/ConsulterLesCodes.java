package com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.compte;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.nourelhoudazribi.aaychfanni.R;
import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.Utils.FirebaseMethods;
import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.accueil.MainfeedListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by ASUS on 08/12/2017.
 */

public class ConsulterLesCodes extends AppCompatActivity implements
        MainfeedListAdapter.OnLoadMoreItemsListener {

    @Override
    public void onLoadMoreItems() {

        displayMorePhotos();

    }
    private static final String TAG = "ConsulterLesCodes";

    private ImageView retour;
    private ListView mListView;
    private ArrayList<String> mLesCodes;
    private ArrayList<String> mPaginatedCodes;


    //add Firebase Database stuff
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private  String userID;
    private FirebaseMethods mFirebaseMethods;
    private ConsulterLesCodesListAdapter mAdapter;
    private int mResults;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_consulter_les_codes);

        retour = (ImageView) findViewById(R.id.back_arrow);
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mListView = (ListView) findViewById(R.id.list_view);


        mPaginatedCodes = new ArrayList<>();

        //set the user
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        /*if(mAuth.getCurrentUser() != null){

        }*/
        mFirebaseMethods = new FirebaseMethods(ConsulterLesCodes.this);

        getCodes();

    }

    ///get the codes
    private void getCodes(){
        Log.d(TAG, "getCodes: ");
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        mLesCodes = new ArrayList<>();

        Query query = reference
                .child(getString(R.string.dbname_codes));
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                    Log.d(TAG, "onDataChange: found code: " +singleSnapshot.child("code_id").getValue().toString());

                    //mFollowing.add(singleSnapshot.child(getString(R.string.field_user_id)).getValue().toString());
                    mLesCodes.add(singleSnapshot.child(getString(R.string.dbname_code_id)).getValue().toString());

                }

                //get the photos
                displayCodes();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    private void displayCodes(){


        mPaginatedCodes = new ArrayList<>();
        if(mLesCodes != null){
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

                mAdapter = new ConsulterLesCodesListAdapter(ConsulterLesCodes.this, R.layout.account_consulter_codes_element, mLesCodes);
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

            if(mLesCodes.size() > mResults && mLesCodes.size() > 0){

                int iterations;
                if(mLesCodes.size() > (mResults + 10)){
                    Log.d(TAG, "displayMorePhotos: there are greater than 10 more photos");
                    iterations = 10;
                }else{
                    Log.d(TAG, "displayMorePhotos: there is less than 10 more photos");
                    iterations = mLesCodes.size() - mResults;
                }

                //add the new photos to the paginated results
                for(int i = mResults; i < mResults + iterations; i++){
                    mPaginatedCodes.add(mLesCodes.get(i));
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


}
