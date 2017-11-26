package com.example.nourelhoudazribi.aaychfanni;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Compte_nour extends AppCompatActivity {
    private Button deconnexion;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private TextView valueview;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compte_nour);
        deconnexion = (Button) findViewById(R.id.button3);
        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference();
        valueview = (TextView) findViewById(R.id.compte);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children=dataSnapshot.getChildren();
               for ( DataSnapshot child : children)
                {
                    child.getValue(FirebaseUser.class);
                    //Toast.makeText(Compte_nour.this,S,Toast.LENGTH_SHORT).show();
                }
                /*String Nom=value.getName();
                valueview.setText(Nom);*/
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        if(firebaseAuth.getCurrentUser()==null){
            finish();
            Toast.makeText(Compte_nour.this, "Vous n'êtes plus connecté", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this,MainActivity.class));
        }

        FirebaseUser user=firebaseAuth.getCurrentUser();

        deconnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/// Create Intent for SignUpActivity abd Start The Activity



                        firebaseAuth.signOut();
                        finish();
                Toast.makeText(Compte_nour.this, "Vous n'êtes plus connecté", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(Compte_nour.this,MainActivity.class));

                }

            });

            }

    }
