package com.example.nourelhoudazribi.aaychfanni;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {
    Button termine;
    private EditText email;
    private EditText pswd;
    private EditText prenom;
    private EditText nom;
    private EditText rpswd;
    private ProgressDialog progressDialog1;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        firebaseAuth=FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()!=null){
            //profile activity here
            finish();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }

        progressDialog1 =new ProgressDialog(this);
        prenom = (EditText) findViewById(R.id.prenom);
        nom = (EditText) findViewById(R.id.nom);
        pswd = (EditText) findViewById(R.id.password);
        email = (EditText) findViewById(R.id.email);
        rpswd = (EditText) findViewById(R.id.confpassword);
        termine =(Button) findViewById(R.id.buttont);
        termine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/// Create Intent for SignUpActivity abd Start The Activity

                registerUser();
               // savedatauser();

            }

        });

    }

    private void registerUser(){
        String e=email.getText().toString().trim();
        final String password=pswd.getText().toString().trim();
        final String p = rpswd.getText().toString().trim();
        final String n=nom.getText().toString().trim();
        final String pr=prenom.getText().toString().trim();


        if(TextUtils.isEmpty(e)){
            //email is empty
            Toast.makeText(this,"Veuillez entrer votre adresse email",Toast.LENGTH_SHORT).show();
            //stopping the function ewecution further
            return;
        }
        if(TextUtils.isEmpty(password)){
            //password is empty
            Toast.makeText(this,"Veuillez entrer un mot de passe",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(p)){
            //email is empty
            Toast.makeText(this,"Veillez confimer le mot de passe",Toast.LENGTH_SHORT).show();
            //stopping the function ewecution further
            return;
        }
        if(p.equals(password))
        {progressDialog1.setMessage("Enregistrement...");
        progressDialog1.show();

        firebaseAuth.createUserWithEmailAndPassword(e,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(Signup.this,"Enregistrement réussi",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),Compte_nour.class));
                                finish();
                                userinformation info = new userinformation(n,pr);
                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                databaseReference= FirebaseDatabase.getInstance().getReference();
                                databaseReference.child(user.getUid()).setValue(info);

                            }else {
                                Toast.makeText(Signup.this,"Enregistrement non réussi",Toast.LENGTH_SHORT).show();

                            }
                            progressDialog1.dismiss();

                    }


                });
        }
        else
        {
            Toast.makeText(Signup.this,"la confirmation du mot de passe est erroné",Toast.LENGTH_SHORT).show();
        }
    }
}
