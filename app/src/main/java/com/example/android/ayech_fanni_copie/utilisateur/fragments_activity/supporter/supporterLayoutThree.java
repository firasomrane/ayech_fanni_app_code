package com.example.android.ayech_fanni_copie.utilisateur.fragments_activity.supporter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.ayech_fanni_copie.R;

/**
 * Created by ASUS on 19/11/2017.
 */

public class supporterLayoutThree extends supporterLayoutOne {

    private String donationtype;

    private static final String TAG = "supporterLayoutThree";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: created");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.supporter_layout_3);
        setDonationDescription();




    }

    public void setDonationDescription(){

        Bundle bundle=this.getIntent().getExtras();

        if(bundle !=null) {

            donationtype = bundle.getString("donationType");
            Toast.makeText(supporterLayoutThree.this,
                     donationtype, Toast.LENGTH_SHORT).show();

            final TextView donation_type_text = (TextView) findViewById(R.id.supporter_donation_type);
            if(donationtype =="Un don par mois"){donation_type_text.setText("par mois");}
            else if (donationtype =="Un don chaque nouveau contenu"){donation_type_text.setText("par contenu");}
            else{donation_type_text.setText("seulement cette fois");}
        }
        /*else {
            Toast.makeText(supporterLayoutThree.this,
                    "bundle null", Toast.LENGTH_SHORT).show();
        }*/

        //I have to set donationType to "" when I return to the first activity.





    }
}
