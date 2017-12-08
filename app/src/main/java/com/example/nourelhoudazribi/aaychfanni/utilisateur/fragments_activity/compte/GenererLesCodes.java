package com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.compte;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.nourelhoudazribi.aaychfanni.R;
import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.Utils.FirebaseMethods;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by ASUS on 08/12/2017.
 */

public class GenererLesCodes extends AppCompatActivity {
    private static final String TAG = "GenererLesCodes";

    private Button generer;

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseMethods mFirebaseMethods;
    private String userID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_generer_codes);

        //set the user
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        if(mAuth.getCurrentUser() != null){
            userID = mAuth.getCurrentUser().getUid();
        }

        generer = (Button) findViewById(R.id.generer_code);
        generer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newPostKey = myRef.child(getString(R.string.dbname_codes)).push().getKey();

                myRef.child(getString(R.string.dbname_codes))
                        .child(newPostKey)
                        .child(getString(R.string.dbname_code_id))
                        .setValue(newPostKey);
                Intent intent = new Intent(GenererLesCodes.this,ConsulterLesCodes.class);
                startActivity(intent);
            }
        });
    }
}
