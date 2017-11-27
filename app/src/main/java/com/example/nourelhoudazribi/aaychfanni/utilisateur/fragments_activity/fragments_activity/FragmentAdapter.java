package com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.fragments_activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ASUS on 14/11/2017.
 */

public class FragmentAdapter extends FragmentPagerAdapter {

    private static final String TAG = "FragmentAdapter";
    ArrayList<Fragment> fragments =new ArrayList<>();
    ArrayList<String> tabTittles = new ArrayList<>();
    private  HashMap<Integer,String> mFragmentTags;
    private FragmentManager fragmentManager;

    public void addFragments(Fragment fragment, String titles){
        this.fragments.add(fragment);
        this.tabTittles.add(titles);
    }

    public FragmentAdapter(FragmentManager fm){
        super(fm);
        mFragmentTags = new HashMap<Integer,String>();
        fragmentManager = fm ;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTittles.get(position);
    }

    public void setTabTittles(ArrayList<String> tabTittles) {
        this.tabTittles = tabTittles;
    }
}
