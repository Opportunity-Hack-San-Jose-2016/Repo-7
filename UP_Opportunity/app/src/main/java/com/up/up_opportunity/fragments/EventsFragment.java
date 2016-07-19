package com.up.up_opportunity.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.up.up_opportunity.R;
import com.up.up_opportunity.UpApplication;
import com.up.up_opportunity.model.event.GoogleEvent;
import com.up.up_opportunity.providers.EventApi;
import com.up.up_opportunity.recycler.GoogleEventsRecyclerView;

import android.support.v7.widget.RecyclerView;

import javax.inject.Inject;
import javax.inject.Named;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Billy on 7/9/16.
 */
public class EventsFragment extends android.support.v4.app.Fragment {


    private static final String TAG = "EVENTS_FRAGMENT";
    RecyclerView recyclerView;
    GoogleEventsRecyclerView recyclerViewAdapter;
    private GoogleEvent googleEvent;
    SharedPreferences sharedPreferences;
    private SwipeRefreshLayout eventSwipeRefreshLayout;
    @Inject @Named("Events") Retrofit retrofit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events, container, false);
        setRetainInstance(true);

        initViews(view);
        getSharedPreferences();
        injectDagger();

        displaySharedPreferences();

        swipeEventRefreshListener();

        return view;
    }

    private void initViews(View view){
        recyclerView = (RecyclerView) view.findViewById(R.id.list_recyclerView_event);
        eventSwipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.event_swipeRefreshLayout);
        eventSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryLight, R.color.colorAccent, R.color.colorPrimary);

    }

    private void getSharedPreferences(){
        sharedPreferences = getActivity().getSharedPreferences("EVENTS", Context.MODE_PRIVATE);
    }

    private void injectDagger(){
        ((UpApplication)getActivity().getApplication()).getNetComponent().inject(this);
    }

    private void displaySharedPreferences(){
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Event","");

        if(json != ""){
            GoogleEvent googleEvent = gson.fromJson(json, GoogleEvent.class);

            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerViewAdapter = new GoogleEventsRecyclerView(googleEvent);
            recyclerView.setAdapter(recyclerViewAdapter);
        }else{
            eventApiCall();
        }
    }

    private void swipeEventRefreshListener(){
        eventSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshEventsContent();
            }
        });
    }

    /**
     * Pull down to refresh will make new API call
     */
    private void refreshEventsContent(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                eventApiCall();
                eventSwipeRefreshLayout.setRefreshing(false);
            }
        }, 0);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       // Log.i(TAG, "onViewCreated: ");

//        submitButton = (Button)view.findViewById(R.id.eventButton);
//        submitButton.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//            eventApiCall();
//
//
//            }
//        });

    }

    private void eventApiCall(){


        EventApi service = retrofit.create(EventApi.class);

        //https://maps.googleapis.com/maps/api/place/nearbysearch/json?
        // location=37.3382,-121.8863&
        // radius=500&
        // name=events&
        // key=AIzaSyA0ls9fcQ0Gnec7lTuV5xmLci3nrOQcePI


        String apiKey = "AIzaSyA0ls9fcQ0Gnec7lTuV5xmLci3nrOQcePI";
        String latLong= "37.3382,-121.8863";
        String radius = "500";
        String name = "events";


        Call<GoogleEvent> call = service.getGoogleEvents(latLong , radius, name, apiKey);
        call.enqueue(new Callback<GoogleEvent>() {
            @Override
            public void onResponse(Call<GoogleEvent> call, Response<GoogleEvent> response) {
                if(response.isSuccessful()){
                    googleEvent = response.body();
                    Double lat = googleEvent.getResults().get(0).getGeometry().getLocation().getLat();
                    String name = googleEvent.getResults().get(0).getName();
                    String vicinity = googleEvent.getResults().get(0).getVicinity();
                    int size = googleEvent.getResults().size();
//                    Log.d(TAG, "Event Name: " + name);
//                    Log.d(TAG, "Event Name: " + vicinity);
//                    Log.d(TAG, "Event Name: " + lat);
//                    Log.d(TAG, "Size: " + size);
                    if(recyclerView != null){
                        recyclerViewAdapter = new GoogleEventsRecyclerView(googleEvent);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        recyclerView.setAdapter(recyclerViewAdapter);
                    }


                    SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
                    Gson gson = new Gson();
                    String json = gson.toJson(googleEvent);
                    prefsEditor.putString("Event", json);
                    prefsEditor.commit();

                }
            }

            @Override
            public void onFailure(Call<GoogleEvent> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

}
