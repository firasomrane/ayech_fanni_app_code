package com.example.android.ayech_fanni_copie.utilisateur.fragments_activity.accueil;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.android.ayech_fanni_copie.R;
import com.example.android.ayech_fanni_copie.utilisateur.fragments_activity.fragments_activity.elementExplore;

import java.util.ArrayList;

/**
 * Created by ASUS on 13/11/2017.
 */

public class accueilFragment extends Fragment {

    private static final String TAG = "accueilFragment";
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: created");
        View rootView=inflater.inflate(R.layout.accueil_fragment,container,false);

        ListView lv= (ListView) rootView.findViewById(R.id.accueil_fragment_list);

        publicationAdapter adapter=new publicationAdapter(this.getActivity() ,getAccueilElements());

        lv.setAdapter(adapter);

        return rootView;
    }


    private ArrayList<Publication> getAccueilElements() {
        ArrayList<Publication> AccueilElements=new ArrayList<>();
        Publication newElement=new Publication("Animation");
        AccueilElements.add(newElement);
        newElement=new elementExplore("Blog");
        AccueilElements.add(newElement);
        newElement=new elementExplore("Comédie");
        AccueilElements.add(newElement);
        newElement=new elementExplore("Dance");
        AccueilElements.add(newElement);
        newElement=new elementExplore("Education");
        AccueilElements.add(newElement);
        newElement=new elementExplore("Jeu Video");
        AccueilElements.add(newElement);
        newElement=new elementExplore("Musique");
        AccueilElements.add(newElement);
        newElement=new elementExplore("Podcasts");
        AccueilElements.add(newElement);
        return AccueilElements;

    }


    @Override
    public String toString() {
        return "accueil";
    }

    /**
     * Created by ASUS on 14/11/2017.
     */

}
