package com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.supporter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.nourelhoudazribi.aaychfanni.R;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by ASUS on 18/11/2017.
 */

public class supporterLayoutTwo extends AppCompatActivity {

    private Button continuerButtonSupporterTwo ;
    private String donationtype;
    private Button supporterMontantPlusButton;
    private Button supporterMontantMinusButton;
    private String montant;
    private TextView montantText;
    private long montantLong;
    public String creator_user_id;


    private static final String TAG = "supporterLayoutTwo";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "created");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supporter_layout_2);

        //get the intent
        creator_user_id="";
        Intent intent = getIntent();
        creator_user_id = intent.getStringExtra(getString(R.string.user_id));
        Log.d(TAG, "setDonationDescription: creator user id is  "+creator_user_id);

        addListenerOnButton();

    }

    public void addListenerOnButton() {

        final Context context = this;
        montantText = (TextView) findViewById(R.id.supporter_montant_text);

        continuerButtonSupporterTwo = (Button) findViewById(R.id.supporter_button_layout_2);
        supporterMontantPlusButton = (Button) findViewById(R.id.supporter_montant_plus_button);
        supporterMontantMinusButton = (Button) findViewById(R.id.supporter_montant_minus_button);

        //Si on clique sur le boutton + on ajoute
        supporterMontantPlusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                montant = montantText.getText().toString();
                montant = montant.replace(" ","");
                montant = montant.split("\\.")[0];
                Log.d(TAG, "onClick: montant est "+montant.getClass().getName());
                Log.d(TAG, "onClick: the type of  Long.valueOf(montant) is  "+ Long.valueOf(montant).getClass().getName());
                montantLong = Long.parseLong(montant);
                montantLong++;
                montantText.setText(String.valueOf(montantLong));
            }
        });

        //Si on clique sur le boutton - on ajoute
        supporterMontantMinusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                montant = montantText.getText().toString().split("\\.")[0];
                montantLong = Long.parseLong(montant);
                if(montantLong >=2){
                    montantLong--;
                }

                montantText.setText(String.valueOf(montantLong));
            }
        });

        //Si on cliqur sur le boutton continuer
        continuerButtonSupporterTwo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                Intent intent = new Intent(context, supporterLayoutThree.class);

                Bundle bundle=getIntent().getExtras();

                if(bundle !=null) {

                    donationtype = bundle.getString("donationType");


                }
                //get the montant value
                montant = montantText.getText().toString();

                //Set the new bundle sent to activity 3

                Bundle bundleToThree = new Bundle();
                bundleToThree.putString("donationType", donationtype);
                bundleToThree.putString("montant", montant);
                intent.putExtra(getString(R.string.user_id), creator_user_id);
                intent.putExtras(bundleToThree);

                startActivity(intent);

            }

        });
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

   /* public void reset(View view){
        scoreForTeamA=0;
        scoreForTeamB=0;
        displayForTeamA(scoreForTeamA);
        displayForTeamB(scoreForTeamB);
    }*/




}
