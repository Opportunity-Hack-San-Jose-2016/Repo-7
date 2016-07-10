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
import android.view.View;
import android.widget.ImageView;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;
import com.up.up_opportunity.fragments.CouponFragment;
import com.up.up_opportunity.fragments.EventsFragment;
import com.up.up_opportunity.fragments.FoodBankFragment;
import com.up.up_opportunity.fragments.ForumFragment;
import com.up.up_opportunity.fragments.HelpFragment;
import com.up.up_opportunity.fragments.HousingFragment;
import com.up.up_opportunity.fragments.JobsFragment;
import com.up.up_opportunity.fragments.LogoFragment;

public class MainActivity extends AppCompatActivity implements HelpFragment.OnForumClickListener, HelpFragment.OnHousingClickListener, HelpFragment.OnFoodBankClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private BottomBar bottomBar;
    private Toolbar toolbar;
    private ImageView logo;
    private ActionBar actionBar;
    private HelpFragment helpFragment;
    private JobsFragment jobsFragment;
    private EventsFragment eventsFragment;
    private CouponFragment couponFragment;
    private FragmentTransaction fragmentTransaction;

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
//        logo = (ImageView)findViewById(R.id.logo_id);
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

//    private void turnOffLogo(){
//        logo.setVisibility(View.GONE);
//    }


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
//                        turnOffLogo();
                        fragmentTransaction.replace(R.id.frag_container_id,jobsFragment);
                        fragmentTransaction.commit();
                        Log.d(TAG, "JOBS");
                        break;
                    case R.id.bottomBarItemThree:
//                        turnOffLogo();
                        fragmentTransaction.replace(R.id.frag_container_id,eventsFragment);
                        fragmentTransaction.commit();
                        Log.d(TAG, "EVENTS");
                        break;
                    case R.id.bottomBarItemFour:
//                        turnOffLogo();
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
        bottomBar.mapColorForTab(0, ContextCompat.getColor(this, R.color.colorPrimary));
        bottomBar.mapColorForTab(1, ContextCompat.getColor(this, R.color.colorPrimary));
        bottomBar.mapColorForTab(2, ContextCompat.getColor(this, R.color.colorPrimary));
        bottomBar.mapColorForTab(3, ContextCompat.getColor(this, R.color.colorPrimary));
    }

    @Override
    public void onFoodBankClicked() {
        initFragManager();
        FoodBankFragment foodBankFragment = new FoodBankFragment();
        fragmentTransaction.replace(R.id.frag_container_id,foodBankFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onForumClicked() {
        initFragManager();
        ForumFragment forumFragment = new ForumFragment();
        fragmentTransaction.replace(R.id.frag_container_id,forumFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onHousingClicked() {
        initFragManager();
        HousingFragment housingFragment = new HousingFragment();
        fragmentTransaction.replace(R.id.frag_container_id,housingFragment);
        fragmentTransaction.commit();
    }
}
