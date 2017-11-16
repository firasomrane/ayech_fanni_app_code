package com.example.android.ayech_fanni_copie.utilisateur.fragments_activity.fragments_activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.ayech_fanni_copie.R;

/**
 * Created by ASUS on 16/11/2017.
 */

public class compteFragment extends Fragment {

    private static final String TAG = "compteFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: completed");
        View rootViewtTwO=inflater.inflate(R.layout.compte_fragment,container,false);

        Log.d(TAG, "onCreateView: rootView");
        return rootViewtTwO;
    }



    @Override
    public String toString() {
        return "compte";
    }
}