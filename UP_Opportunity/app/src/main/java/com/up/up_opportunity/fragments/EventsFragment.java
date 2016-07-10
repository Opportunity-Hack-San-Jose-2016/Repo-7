package com.up.up_opportunity.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.up.up_opportunity.R;
import com.up.up_opportunity.fragments.jobs.JobsRVAdapter;
import com.up.up_opportunity.model.event.GoogleEvent;
import com.up.up_opportunity.model.job.Indeed;
import com.up.up_opportunity.providers.EventApi;
import com.up.up_opportunity.providers.IndeedService;
import com.up.up_opportunity.recycler.GoogleEventsRecyclerView;
import com.yelp.clientlib.entities.Business;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Billy on 7/9/16.
 */
public class EventsFragment extends android.support.v4.app.Fragment {


    private static final String TAG = "EVENTS_FRAGMENT";
    Button submitButton;
    RecyclerView recyclerView;
    GoogleEventsRecyclerView recyclerViewAdapter;
    private GoogleEvent googleEvent;
    SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_events, container, false);
        setRetainInstance(true);
        recyclerView = (RecyclerView) v.findViewById(R.id.list_recyclerView_event);

        sharedPreferences = getActivity().getSharedPreferences("EVENTS", Context.MODE_PRIVATE);

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


        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "onViewCreated: ");

        submitButton = (Button)view.findViewById(R.id.eventButton);
        submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
            eventApiCall();


            }
        });

    }

    private void eventApiCall(){

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        GsonBuilder gsonBuilder = new GsonBuilder()
                .setLenient();
        Gson gson = gsonBuilder.create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/maps/api/place/nearbysearch/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();

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
                    Log.d(TAG, "Event Name: " + name);
                    Log.d(TAG, "Event Name: " + vicinity);
                    Log.d(TAG, "Event Name: " + lat);
                    Log.d(TAG, "Size: " + size);
                    recyclerViewAdapter = new GoogleEventsRecyclerView(googleEvent);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(recyclerViewAdapter);

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
