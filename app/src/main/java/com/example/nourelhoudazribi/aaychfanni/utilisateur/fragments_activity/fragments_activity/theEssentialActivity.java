package com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.fragments_activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.example.nourelhoudazribi.aaychfanni.R;
import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.accueil.accueilFragment;


/**
 * Created by ASUS on 14/11/2017.
 */

public class theEssentialActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {
        ViewPager vp;
        TabLayout tabLayout;
        private static final String TAG = "theEssentialActivity";
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            Log.d(TAG,"created");
            super.onCreate(savedInstanceState);
            setContentView(R.layout.the_essential_activity);
            //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            //setSupportActionBar(toolbar);

            //VIEWPAGER
            vp= (ViewPager) findViewById(R.id.viewPager);
            this.addPages();
            //TABLAYOUT
            tabLayout= (TabLayout) findViewById(R.id.tablayout);
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
            tabLayout.setupWithViewPager(vp);
            //tabLayout.getTabAt(0).setIcon(R.drawable.ic_home);
            tabLayout.addOnTabSelectedListener(this);

            setupTabIcons();
        }

        //to get the icons below the text in the tabLayout
    private void setupTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText("ACCUEIL");
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_home, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText("EXPLORER");
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_home, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabThree.setText("NOIFICATIONS");
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_home, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabThree);

        TextView tabFour = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabFour.setText("COMPTE");
        tabFour.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_home, 0, 0);
        tabLayout.getTabAt(3).setCustomView(tabFour);
    }

        private void addPages()
        {
            MyPagerAdapter pagerAdapter=new MyPagerAdapter(this.getSupportFragmentManager());
            pagerAdapter.addFragment(new accueilFragment());
            pagerAdapter.addFragment(new exploreFragment());
            pagerAdapter.addFragment(new notificationsFragment());
            pagerAdapter.addFragment(new compteFragment());
            //SET ADAPTER TO VP
            vp.setAdapter(pagerAdapter);
        }
        public void onTabSelected(TabLayout.Tab tab) {
            vp.setCurrentItem(tab.getPosition());
        }
        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
        }
        @Override
        public void onTabReselected(TabLayout.Tab tab) {
        }

}
