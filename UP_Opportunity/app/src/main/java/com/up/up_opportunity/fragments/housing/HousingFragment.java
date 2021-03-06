package com.up.up_opportunity.fragments.housing;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.up.up_opportunity.JobWebViewActivity;
import com.up.up_opportunity.R;
import com.up.up_opportunity.UpApplication;
import com.up.up_opportunity.model.housing.HousingHUD;
import com.up.up_opportunity.providers.HousingService;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Named;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by samsiu on 7/10/16.
 */
public class HousingFragment extends android.support.v4.app.Fragment implements HousingRVAdapter.HousingClickListener {

    private static final String TAG = "HOUSING_FRAGMENT";

    private RecyclerView housingRecyclerView;
    private SwipeRefreshLayout housingSwipeRefreshLayout;

    private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;
    private HousingRVAdapter housingRVAdapter;
    SharedPreferences sharedPreferences;

    @Inject @Named("Housing") Retrofit retrofit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_housing,container,false);
        setRetainInstance(true);

        initViews(view);
        setLayoutManager();
        getSharedPreferences();
        injectDagger();

        displaySharedPreferences();

        swipeHousingRefreshListener();
        housingApiCall();

        return view;
    }

    private void initViews(View view){
        housingRecyclerView = (RecyclerView)view.findViewById(R.id.housing_recyclerView);
        housingSwipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.housing_swipeRefreshLayout);

        housingSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryLight, R.color.colorAccent, R.color.colorPrimary);
    }

    private void setLayoutManager(){
        linearLayoutManager = new LinearLayoutManager(getContext());
        gridLayoutManager = new GridLayoutManager(getContext(), 2);
    }

    private void getSharedPreferences(){
        sharedPreferences = getActivity().getSharedPreferences("HOUSING", Context.MODE_PRIVATE);
    }

    private void injectDagger(){
        ((UpApplication)getActivity().getApplication()).getNetComponent().inject(this);
    }

    private void swipeHousingRefreshListener(){
        housingSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshHousingContent();
            }
        });
    }

    private void displaySharedPreferences(){
        Gson gson = new Gson();
        String json = sharedPreferences.getString("HousingHUD","");
        if(json != ""){
            HousingHUD housingHUD = gson.fromJson(json, HousingHUD.class);
            housingRecyclerView.setLayoutManager(linearLayoutManager);
            //housingRecyclerView.setLayoutManager(gridLayoutManager);
            housingRVAdapter = new HousingRVAdapter(this, housingHUD);
            housingRecyclerView.setAdapter(housingRVAdapter);
        }
    }

    /**
     * Pull down to refresh will make new API call
     */
    private void refreshHousingContent(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                housingApiCall();
                housingSwipeRefreshLayout.setRefreshing(false);
            }
        }, 0);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void housingApiCall(){

        HousingService service = retrofit.create(HousingService.class);

        String latitude = "37.2970155";
        String longitude = "-121.8174129";
        String distance = "50";
        String rowLimit = "";
        String services = "";
        String languages = "";

        Call<ResponseBody> call = service.getHousingAgencies(latitude, longitude, distance, rowLimit, services, languages);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String parsedRespone = null;
                try {
                    parsedRespone = response.body().string().replace("[", "{\"housing\" : [").replace("]", "]}");
                    Gson gson = new Gson();
                    HousingHUD housingHUD = gson.fromJson(parsedRespone, HousingHUD.class);

                    if(housingRecyclerView != null){
                        housingRecyclerView.setLayoutManager(linearLayoutManager);
                        housingRVAdapter = new HousingRVAdapter(HousingFragment.this, housingHUD);
                        housingRecyclerView.setAdapter(housingRVAdapter);
                    }

                    SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
                    Gson gsonHousing = new Gson();
                    String json = gsonHousing.toJson(housingHUD);
                    prefsEditor.putString("HousingHUD", json);
                    prefsEditor.commit();

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }


    @Override
    public void onCardViewClick(String link) {

        if(link == null || link.equals("n/a")){
            return;
        }

        Intent intent = new Intent(getActivity(), JobWebViewActivity.class);
        intent.putExtra("link", link);
        startActivity(intent);
    }
}
