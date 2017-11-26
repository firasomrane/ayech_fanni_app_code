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
    private Double montantDouble;

    private static final String TAG = "supporterLayoutTwo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "created");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supporter_layout_2);

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
                montantDouble = Double.parseDouble(montant);
                montantDouble++;
                montantText.setText(String.valueOf(round(montantDouble,2)));
            }
        });

        //Si on clique sur le boutton - on ajoute
        supporterMontantMinusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                montant = montantText.getText().toString();
                montantDouble = Double.parseDouble(montant);
                if(montantDouble>=2){
                    montantDouble--;
                }

                montantText.setText(String.valueOf(round(montantDouble,2)));
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
