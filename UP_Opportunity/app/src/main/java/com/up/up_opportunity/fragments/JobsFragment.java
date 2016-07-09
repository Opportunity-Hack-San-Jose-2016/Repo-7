package com.up.up_opportunity.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.up.up_opportunity.R;

/**
 * Created by adao1 on 7/9/2016.
 */
public class JobsFragment extends android.support.v4.app.Fragment {

    private static final String TAG = "JOBS_FRAGMENT";
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jobs,container,false);
        setRetainInstance(true);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "onViewCreated: ");
    }
}
