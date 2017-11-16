package com.example.android.ayech_fanni_copie.utilisateur.fragments_activity.fragments_activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.ayech_fanni_copie.R;

/**
 * Created by ASUS on 13/11/2017.
 */

public class accueilFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.accueil_fragment,container,false);

        return rootView;
    }
    @Override
    public String toString() {
        return "accueil";
    }

    /**
     * Created by ASUS on 14/11/2017.
     */

}
