package com.up.up_opportunity;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by adao1 on 7/9/2016.
 */
public class UpApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }

}
