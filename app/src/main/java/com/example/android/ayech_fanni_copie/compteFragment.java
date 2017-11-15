package com.example.android.ayech_fanni_copie;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ASUS on 14/11/2017.
 */

public class compteFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.compte_fragment,container,false);

        return rootView;
    }

    @Override
    public String toString() {
        return "compte";
    }
}
