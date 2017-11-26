package com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.fragments_activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nourelhoudazribi.aaychfanni.R;

/**
 * Created by ASUS on 14/11/2017.
 */

public class notificationsFragment extends Fragment {
    private static final String TAG = "notificationsFragment";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.notifications_fragment,container,false);
        //ListView lv= (ListView) rootView.findViewById(R.id.notifications_fragment_list);
        //CustomAdapter adapter=new CustomAdapter(this.getActivity(),getExploreElements());
       // lv.setAdapter(adapter);
        return rootView;
    }
    /*private ArrayList<elementExplore> getExploreElements() {
        ArrayList<elementExplore> explorerElements=new ArrayList<>();
        elementExplore newElement=new elementExplore("Aniamtion");
        explorerElements.add(newElement);
        newElement=new elementExplore("Comedie");
        explorerElements.add(newElement);
        newElement=new elementExplore("Third");
        explorerElements.add(newElement);
        newElement=new elementExplore("Ghost");
        explorerElements.add(newElement);
        return explorerElements;
    }*/
    @Override
    public String toString() {
        return "notifications";
    }
}
