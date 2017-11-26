package com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.fragments_activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.nourelhoudazribi.aaychfanni.R;

/**
 * Created by ASUS on 16/11/2017.
 */

public class compteFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "compteFragment";

    private Button devenirCreateur;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: completed");
        View rootViewtTwO=inflater.inflate(R.layout.compte_fragment,container,false);

        devenirCreateur = (Button) rootViewtTwO.findViewById(R.id.become_creator);
        devenirCreateur.setOnClickListener(this);

        Log.d(TAG, "onCreateView: rootView");
        return rootViewtTwO;
    }

    @Override
    public void onClick(View v) {
        // handling onClick Events
        switch (v.getId()) {

            case R.id.become_creator:
                // code for button when user clicks devenir createur.
                Intent intent = new Intent(getActivity(), devenir_createur.class);
                startActivity(intent);

                break;

            default:
                break;
        }
    }



    @Override
    public String toString() {
        return "layout_compte";
    }
}