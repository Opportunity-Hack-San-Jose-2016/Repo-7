package com.up.up_opportunity;

import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;
import com.up.up_opportunity.fragments.CouponFragment;
import com.up.up_opportunity.fragments.EventsFragment;
import com.up.up_opportunity.fragments.HelpFragment;
import com.up.up_opportunity.fragments.jobs.JobsFragment;
import com.up.up_opportunity.fragments.LogoFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private BottomBar bottomBar;
    private Toolbar toolbar;
    private ActionBar actionBar;
    HelpFragment helpFragment;
    JobsFragment jobsFragment;
    EventsFragment eventsFragment;
    CouponFragment couponFragment;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initActionBar();
        bottomBar = bottomBar.attach(this, savedInstanceState);
        bottomBar.setItems(R.menu.bottombar_menu);
        initializeFragments();
        initFragManager();
        openLogoFrag();
        bottomBarClickListener(bottomBar);

    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        bottomBar.onSaveInstanceState(outState);
    }

    private void initViews(){
        toolbar = (Toolbar)findViewById(R.id.home_toolBar);

    }

    private void initActionBar(){
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setTitle(TAG);
    }

    private void initFragManager(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
    }

    private void openLogoFrag(){
        LogoFragment logoFragment = new LogoFragment();
        fragmentTransaction.add(R.id.frag_container_id,logoFragment);
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


    private void bottomBarClickListener(BottomBar bottomBar){
        bottomBar.setOnMenuTabClickListener(new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                initFragManager();
                switch(menuItemId){
                    case R.id.bottomBarItemOne:
                        // The user selected item number one.
                        fragmentTransaction.replace(R.id.frag_container_id,helpFragment);
                        fragmentTransaction.commit();
                        Log.d(TAG, "HELP");
                        break;
                    case R.id.bottomBarItemTwo:
                        fragmentTransaction.replace(R.id.frag_container_id,jobsFragment);
                        fragmentTransaction.commit();
                        Log.d(TAG, "JOBS");
                        break;
                    case R.id.bottomBarItemThree:
                        fragmentTransaction.replace(R.id.frag_container_id,eventsFragment);
                        fragmentTransaction.commit();
                        Log.d(TAG, "EVENTS");
                        break;
                    case R.id.bottomBarItemFour:
                        fragmentTransaction.replace(R.id.frag_container_id,couponFragment);
                        fragmentTransaction.commit();
                        Log.d(TAG, "DISCOUNTS");
                        break;
                    default:
                        Log.d(TAG, "None");
                        break;
                }
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {
                initFragManager();
                switch(menuItemId){
                    case R.id.bottomBarItemOne:
                        // The user reselected item number one, scroll your content to top.
                        fragmentTransaction.replace(R.id.frag_container_id,helpFragment);
                        fragmentTransaction.commit();
                        Log.d(TAG, "HELP");
                        break;
                    case R.id.bottomBarItemTwo:
                        fragmentTransaction.replace(R.id.frag_container_id,jobsFragment);
                        fragmentTransaction.commit();
                        Log.d(TAG, "JOBS");
                        break;
                    case R.id.bottomBarItemThree:
                        fragmentTransaction.replace(R.id.frag_container_id,eventsFragment);
                        fragmentTransaction.commit();
                        Log.d(TAG, "EVENTS");
                        break;
                    case R.id.bottomBarItemFour:
                        fragmentTransaction.replace(R.id.frag_container_id,couponFragment);
                        fragmentTransaction.commit();
                        Log.d(TAG, "DISCOUNTS");
                        break;
                    default:
                        Log.d(TAG, "None");
                        break;
                }
            }

        });

        // Setting colors for different tabs when there's more than three of them.
        // You can set colors for tabs in three different ways as shown below.
        bottomBar.mapColorForTab(0, ContextCompat.getColor(this, R.color.tab1));
        bottomBar.mapColorForTab(1, ContextCompat.getColor(this, R.color.tab2));
        bottomBar.mapColorForTab(2, ContextCompat.getColor(this, R.color.tab3));
        bottomBar.mapColorForTab(3, ContextCompat.getColor(this, R.color.tab4));
    }

}
