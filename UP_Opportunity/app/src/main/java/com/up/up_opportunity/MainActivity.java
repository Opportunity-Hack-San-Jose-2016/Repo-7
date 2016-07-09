package com.up.up_opportunity;

import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private BottomBar bottomBar;
    private Toolbar toolbar;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initActionBar();

        bottomBar = bottomBar.attach(this, savedInstanceState);
        bottomBar.setItems(R.menu.bottombar_menu);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    private void bottomBarClickListener(BottomBar bottomBar){
        bottomBar.setOnMenuTabClickListener(new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                switch(menuItemId){
                    case R.id.bottomBarItemOne:
                        // The user selected item number one.
                        Log.d(TAG, "Tab 1");
                        break;
                    case R.id.bottomBarItemTwo:
                        Log.d(TAG, "Tab 2");
                        break;
                    case R.id.bottomBarItemThree:
                        Log.d(TAG, "Tab 3");
                        break;
                    case R.id.bottomBarItemFour:
                        Log.d(TAG, "Tab 4");
                        break;
                    default:
                        Log.d(TAG, "None");
                        break;
                }
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {
                switch(menuItemId){
                    case R.id.bottomBarItemOne:
                        // The user reselected item number one, scroll your content to top.
                        Log.d(TAG, "Tab 1");
                        break;
                    case R.id.bottomBarItemTwo:
                        Log.d(TAG, "Tab 2");
                        break;
                    case R.id.bottomBarItemThree:
                        Log.d(TAG, "Tab 3");
                        break;
                    case R.id.bottomBarItemFour:
                        Log.d(TAG, "Tab 4");
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
