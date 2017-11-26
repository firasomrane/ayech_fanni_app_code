package com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.accueil;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.nourelhoudazribi.aaychfanni.R;

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
        Publication newPost=new Publication("Firas Omrane","New post title", "This is the description for the post", "Youtube.com", "24/11/2017", R.drawable.youtube_image,R.drawable.user_photo);
        AccueilElements.add(newPost);
        newPost=new Publication("Firas Omrane","New post title", "This is the description for the post", "Youtube.com", "24/11/2017",R.drawable.user_photo);
        AccueilElements.add(newPost);
        newPost=new Publication("Firas Omrane","New post title", "This is the description for the post", "Youtube.com", "24/11/2017" , R.drawable.youtube_image,R.drawable.user_photo);
        AccueilElements.add(newPost);
        newPost=new Publication("Firas Omrane","New post title", "This is the description for the post", "Youtube.com", "24/11/2017",R.drawable.youtube_image,R.drawable.user_photo);
        AccueilElements.add(newPost);
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
