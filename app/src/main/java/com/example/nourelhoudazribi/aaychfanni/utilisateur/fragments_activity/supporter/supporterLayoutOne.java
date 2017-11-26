package com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.supporter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.nourelhoudazribi.aaychfanni.R;

/**
 * Created by ASUS on 18/11/2017.
 */

public class supporterLayoutOne extends AppCompatActivity {

    private CharSequence donationtype;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private Button continuerButtonSupporterOne;

    private static final String TAG = "supporterLayoutOne";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "created");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supporter_layout_1);

        //handle the selected radio buttons from here https://www.mkyong.com/android/android-radio-buttons-example/
        addListenerOnButton();


    }

    //check which radio button was selected
    public void addListenerOnButton() {

        final Context context = this;

        radioGroup = (RadioGroup) findViewById(R.id.supporter_radio_group);
        continuerButtonSupporterOne = (Button) findViewById(R.id.supporter_button_layout_1);

        continuerButtonSupporterOne.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // get selected radio button from radioGroup
                int selectedId = radioGroup.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                radioButton = (RadioButton) findViewById(selectedId);

                donationtype = radioButton.getText();

                Toast.makeText(supporterLayoutOne.this,
                        donationtype, Toast.LENGTH_SHORT).show();

                // do something when the button is clicked
                // Yes we will handle click here but which button clicked??? We don't know

                // So we will make
                switch (v.getId() /*to get clicked view id**/) {
                    case R.id.supporter_radioButton1:

                        // do something when the supporter_radioButton1 is clicked

                        break;
                    case R.id.supporter_radioButton2:

                        // do something when the supporter_radioButton2 is clicked

                        break;
                    case R.id.supporter_radioButton3:

                        // do something when the supporter_radioButton3 is clicked

                        break;
                    default:
                        break;
                }

                Intent intent = new Intent(context, supporterLayoutTwo.class);
                //Create the bundle
                Bundle bundle = new Bundle();

                //Add your data to bundle
                bundle.putString("donationType",donationtype.toString() );

                //Add the bundle to the intent
                intent.putExtras(bundle);

                startActivity(intent);

            }

        });

    }



}
