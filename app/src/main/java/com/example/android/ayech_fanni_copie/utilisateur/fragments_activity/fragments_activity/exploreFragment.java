package com.example.android.ayech_fanni_copie.utilisateur.fragments_activity.fragments_activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.android.ayech_fanni_copie.R;

import java.util.ArrayList;

/**
 * Created by ASUS on 14/11/2017.
 */



public class exploreFragment extends Fragment {
    private static final String TAG = "exploreFragment";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.explore_fragment,container,false);
        ListView lv= (ListView) rootView.findViewById(R.id.explore_fragment_list);
        CustomAdapter adapter=new CustomAdapter(this.getActivity(),getExploreElements());
        lv.setAdapter(adapter);
        return rootView;
    }
    private ArrayList<elementExplore> getExploreElements() {
        ArrayList<elementExplore> explorerElements=new ArrayList<>();
        elementExplore newElement=new elementExplore("Animation");
        explorerElements.add(newElement);
        newElement=new elementExplore("Blog");
        explorerElements.add(newElement);
        newElement=new elementExplore("Comédie");
        explorerElements.add(newElement);
        newElement=new elementExplore("Dance");
        explorerElements.add(newElement);
        newElement=new elementExplore("Education");
        explorerElements.add(newElement);
        newElement=new elementExplore("Jeu Video");
        explorerElements.add(newElement);
        newElement=new elementExplore("Musique");
        explorerElements.add(newElement);
        newElement=new elementExplore("Podcasts");
        explorerElements.add(newElement);
        return explorerElements;

    }
    @Override
    public String toString() {
        return "explorer";
    }
}
