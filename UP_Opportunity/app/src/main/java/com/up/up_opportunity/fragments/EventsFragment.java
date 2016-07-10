package com.up.up_opportunity.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.up.up_opportunity.R;

/**
 * Created by Billy on 7/9/16.
 */
public class EventsFragment extends Fragment {

    private static final String TAG = "EVENTS_FRAGMENT";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_events, container, false);
        setRetainInstance(true);
        return v;
    }

<<<<<<< HEAD

=======
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "onViewCreated: ");
    }
>>>>>>> 99aa1777ac7bcce3bdf5329d4c5b17728af94cbc
}
