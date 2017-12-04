package com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.fragments_activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.nourelhoudazribi.aaychfanni.R;
import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.Utils.UniversalImageLoader;
import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.accueil.MainfeedListAdapter;
import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.accueil.accueilFragment;
import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.compte.compteFragment;
import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.explorer.exploreFragment;
import com.nostra13.universalimageloader.core.ImageLoader;


/**
 * Created by ASUS on 14/11/2017.
 */

public class theEssentialActivity extends AppCompatActivity implements
        MainfeedListAdapter.OnLoadMoreItemsListener {

    @Override
    public void onLoadMoreItems() {
        Log.d(TAG, "onLoadMoreItems: displaying more photos");
        accueilFragment fragment = (accueilFragment) getSupportFragmentManager()
                .findFragmentByTag("android:switcher:" + R.id.viewPager + ":" + viewPager.getCurrentItem());
        if(fragment != null){
            fragment.displayMorePhotos();
        }
    }

    TabLayout tabLayout ;
    Toolbar toolbar;
    ViewPager viewPager ;
    private static final String TAG = "theEssentialActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG,"created");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.the_essential_activity);

        //this is for loading pictures purpose
        initImageLoader();


        //VIEWPAGER
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        //TABLAYOUT
        tabLayout= (TabLayout) findViewById(R.id.tablayout);
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager());

        fragmentAdapter.addFragments(new accueilFragment(),"ACCUEIL");
        fragmentAdapter.addFragments(new exploreFragment(),"EXPLORER");
        fragmentAdapter.addFragments(new notificationsFragment(),"notifications");
        fragmentAdapter.addFragments(new compteFragment(),"compte");

        viewPager.setAdapter(fragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_accueil_red);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_explore);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_notifications);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_account);

        tabLayout.addOnTabSelectedListener(listener(viewPager));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        tabLayout.getTabAt(0).setIcon(R.drawable.ic_accueil_red);
                        tabLayout.getTabAt(1).setIcon(R.drawable.ic_explore);
                        tabLayout.getTabAt(2).setIcon(R.drawable.ic_notifications);
                        tabLayout.getTabAt(3).setIcon(R.drawable.ic_account);
                        break;
                    case 1:
                        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home);
                        tabLayout.getTabAt(1).setIcon(R.drawable.ic_explore_red);
                        tabLayout.getTabAt(2).setIcon(R.drawable.ic_notifications);
                        tabLayout.getTabAt(3).setIcon(R.drawable.ic_account);
                        break;
                    case 2:
                        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home);
                        tabLayout.getTabAt(1).setIcon(R.drawable.ic_explore);
                        tabLayout.getTabAt(2).setIcon(R.drawable.ic_notification_red);
                        tabLayout.getTabAt(3).setIcon(R.drawable.ic_account);
                        break;
                    case 3:
                        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home);
                        tabLayout.getTabAt(1).setIcon(R.drawable.ic_explore);
                        tabLayout.getTabAt(2).setIcon(R.drawable.ic_notifications);
                        tabLayout.getTabAt(3).setIcon(R.drawable.ic_compte_red);
                        break;
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public TabLayout.OnTabSelectedListener listener(final ViewPager pager){
        return new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        };
    }

    private void initImageLoader(){
        UniversalImageLoader universalImageLoader = new UniversalImageLoader(theEssentialActivity.this);
        ImageLoader.getInstance().init(universalImageLoader.getConfig());
    }

}
