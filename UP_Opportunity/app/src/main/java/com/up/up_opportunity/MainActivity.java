package com.up.up_opportunity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ImageView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.up.up_opportunity.fragments.CouponFragment;
import com.up.up_opportunity.fragments.EventsFragment;
import com.up.up_opportunity.fragments.FoodBankFragment;
import com.up.up_opportunity.fragments.ForumFragment;
import com.up.up_opportunity.fragments.HelpFragment;
import com.up.up_opportunity.fragments.jobs.JobsFragment;
import com.up.up_opportunity.fragments.housing.HousingFragment;

public class MainActivity extends AppCompatActivity implements HelpFragment.OnForumClickListener, HelpFragment.OnHousingClickListener, HelpFragment.OnFoodBankClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private Toolbar toolbar;
    private HelpFragment helpFragment;
    private JobsFragment jobsFragment;
    private EventsFragment eventsFragment;
    private CouponFragment couponFragment;
    private FragmentTransaction fragmentTransaction;
    private AHBottomNavigation bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initActionBar();
        initializeFragments();
        initFragManager();
        openHelpFrag();
        bottomNavi();
    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void initViews(){
        toolbar = (Toolbar)findViewById(R.id.home_toolBar);
        bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);

//        logo = (ImageView)findViewById(R.id.logo_id);

    }

    private void initActionBar(){
        setSupportActionBar(toolbar);
//        actionBar = getSupportActionBar();
//        actionBar.setTitle(TAG);
    }

    private void initFragManager(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
    }

    private void openHelpFrag(){
        HelpFragment helpFragment = new HelpFragment();
        fragmentTransaction.add(R.id.frag_container_id, helpFragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    private void initializeFragments(){
        helpFragment = new HelpFragment();
        jobsFragment = new JobsFragment();
        eventsFragment = new EventsFragment();
        couponFragment = new CouponFragment();

    }

//    private void turnOffLogo(){
//        logo.setVisibility(View.GONE);
//    }


    @Override
    public void onFoodBankClicked() {
        initFragManager();
        toolbar.setTitle("Food Bank");
        FoodBankFragment foodBankFragment = new FoodBankFragment();
        fragmentTransaction.replace(R.id.frag_container_id,foodBankFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onForumClicked() {
        initFragManager();

        toolbar.setTitle("Q&A Forum");
        ForumFragment forumFragment = new ForumFragment();
        fragmentTransaction.replace(R.id.frag_container_id,forumFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onHousingClicked() {
        initFragManager();
        toolbar.setTitle("Housing Agencies");
        HousingFragment housingFragment = new HousingFragment();
        fragmentTransaction.replace(R.id.frag_container_id,housingFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }


    private void bottomNavi(){
        setupBottomNavi();
        // Set listener
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                initFragManager();
                fragmentTransaction.addToBackStack(null);
                if (position == 0) {
                    fragmentTransaction.replace(R.id.frag_container_id, helpFragment);
                    toolbar.setTitle("Help");
                    toolbar.setTitleTextColor(ContextCompat.getColor(MainActivity.this, R.color.white));
                }
                if (position == 1) {
                    fragmentTransaction.replace(R.id.frag_container_id, jobsFragment);
                    toolbar.setTitle("Jobs");
                    toolbar.setTitleTextColor(ContextCompat.getColor(MainActivity.this, R.color.white));
                }
                if (position == 2) {
                    fragmentTransaction.replace(R.id.frag_container_id, eventsFragment);
                    toolbar.setTitle("Events & Activities");
                    toolbar.setTitleTextColor(ContextCompat.getColor(MainActivity.this, R.color.white));
                }
                if (position == 3) {
                    fragmentTransaction.replace(R.id.frag_container_id, couponFragment);
                    toolbar.setTitle("Coupons & Discounts");
                    toolbar.setTitleTextColor(ContextCompat.getColor(MainActivity.this, R.color.white));
                }
                fragmentTransaction.commit();
                return true;
            }
        });
    }


    private void setupBottomNavi(){
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.tab_1, R.drawable.ic_local_hospital_black_24dp, R.color.colorPrimary);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.tab_2, R.drawable.ic_card_travel_black_24dp, R.color.colorPrimary);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.tab_3, R.drawable.ic_map_black_24dp, R.color.colorPrimary);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem(R.string.tab_4, R.drawable.ic_local_atm_black_24dp, R.color.colorPrimary);
        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
        bottomNavigation.addItem(item4);

        bottomNavigation.setBehaviorTranslationEnabled(false);
        bottomNavigation.setForceTint(true);
        bottomNavigation.setForceTitlesDisplay(true);
        bottomNavigation.setColored(true);

        int colorAccent = ContextCompat.getColor(MainActivity.this, R.color.colorAccent);
        int colorWhite = ContextCompat.getColor(MainActivity.this, R.color.white);
        // Change colors
        bottomNavigation.setAccentColor(colorAccent);
        bottomNavigation.setInactiveColor(colorWhite);
    }

}
