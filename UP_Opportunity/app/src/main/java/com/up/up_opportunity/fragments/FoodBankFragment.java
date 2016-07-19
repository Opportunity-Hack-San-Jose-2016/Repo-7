package com.up.up_opportunity.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.up.up_opportunity.FoodBankAdapter;
import com.up.up_opportunity.JobWebViewActivity;
import com.up.up_opportunity.R;
import com.yelp.clientlib.connection.YelpAPI;
import com.yelp.clientlib.connection.YelpAPIFactory;
import com.yelp.clientlib.entities.Business;
import com.yelp.clientlib.entities.SearchResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by adao1 on 7/9/2016.
 */
public class FoodBankFragment extends Fragment implements FoodBankAdapter.FoodClickListener {

    private static final String TAG = "FOODBANK_FRAGMENT";
    private ArrayList<Business> foodBanks;
    private FoodBankAdapter foodBankAdapter;
    private RecyclerView foodBankRV;
    private EditText locationET;
    private Button submitButton;
    private SwipeRefreshLayout foodbankSwipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_foodbank,container,false);

        initViews(view);
        
        swipeFoodbankRefreshListener();

        return view;
    }

    private void initViews(View view){
        foodBankRV = (RecyclerView)view.findViewById(R.id.foodbank_RV);
        locationET = (EditText)view.findViewById(R.id.foodbank_location_editText);
        submitButton = (Button)view.findViewById(R.id.foodbank_submit_button);

        foodbankSwipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.foodbank_swipeRefreshLayout);
        foodbankSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryLight, R.color.colorAccent, R.color.colorPrimary);

    }

    private void swipeFoodbankRefreshListener(){
        foodbankSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFoodbankContent();
            }
        });
    }

    /**
     * Pull down to refresh will make new API call
     */
    private void refreshFoodbankContent(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                manageYelpApi();
                foodbankSwipeRefreshLayout.setRefreshing(false);
            }
        }, 0);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "onViewCreated: ");
        setSubmitListener();
        makeRVAdapter();
        manageYelpApi();

    }

    private void setSubmitListener(){
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (locationET.getText().toString()!="")manageYelpApi();
            }
        });
    }

    private void makeRVAdapter(){
        foodBanks= new ArrayList<>();
        foodBankAdapter = new FoodBankAdapter(FoodBankFragment.this, foodBanks);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        if(foodBankRV != null){
            foodBankRV.setLayoutManager(layoutManager);
            foodBankRV.setAdapter(foodBankAdapter);
        }

    }

    private void manageYelpApi(){
        YelpAPIFactory yelpApiFactory = new YelpAPIFactory("EilC2USvz-YObU6vfeJRHw",
                "0fxSeohRSU4zyZXayEIS2v66k-U","dyHwTkydjLinN0jLBPKmNVqvmbEoj86r","hmh69RYVAWjeAKT9M1xWVBd5F6Y");
        YelpAPI yelpAPI = yelpApiFactory.createAPI();
        Map<String, String> params = new HashMap<>();

        params.put("term", "food bank");

        Call<SearchResponse> call = yelpAPI.search(locationET.getText().toString(), params);
        call.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                ArrayList<Business> responseList = response.body().businesses();
                foodBanks.clear();
                foodBanks.addAll(responseList);
                foodBankAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {


            }
        });
    }

    @Override
    public void onCardViewClick(String link) {
        Intent intent = new Intent(getActivity(), JobWebViewActivity.class);
        intent.putExtra("link", link);
        startActivity(intent);
        //Log.d(TAG, "JobsFragment: Card Clicked: " + link);
    }
}
