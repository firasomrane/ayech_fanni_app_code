package com.example.android.ayech_fanni_copie.utilisateur.fragments_activity.supporter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.android.ayech_fanni_copie.R;

/**
 * Created by ASUS on 18/11/2017.
 */

public class supporterLayoutTwo extends AppCompatActivity {

    private Button continuerButtonSupporterTwo ;
    private String donationtype;

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

        continuerButtonSupporterTwo = (Button) findViewById(R.id.supporter_button_layout_2);

        continuerButtonSupporterTwo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                Intent intent = new Intent(context, supporterLayoutThree.class);

                Bundle bundle=getIntent().getExtras();

                if(bundle !=null) {

                    donationtype = bundle.getString("donationType");

                }

                Bundle bundleToThree = new Bundle();
                bundleToThree.putString("donationType", donationtype);
                intent.putExtras(bundleToThree);

                startActivity(intent);

            }

        });
    }




}
