package com.example.android.ayech_fanni_copie.utilisateur.fragments_activity.fragments_activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
/**
 * Created by ASUS on 14/11/2017.
 */

public class MyPagerAdapter extends FragmentPagerAdapter {

    private static final String TAG = "MyPagerAdapter";
    ArrayList<Fragment> fragments=new ArrayList<>();
    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }
    @Override
    public int getCount() {
        return fragments.size();
    }
    //ADD PAGE
    public void addFragment(Fragment f)
    {
        fragments.add(f);
    }
    //set title
    @Override
    public CharSequence getPageTitle(int position) {
        String title=fragments.get(position).toString();
        return title.toString();
    }
}
