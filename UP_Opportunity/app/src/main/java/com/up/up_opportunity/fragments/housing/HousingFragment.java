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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.up.up_opportunity.JobWebViewActivity;
import com.up.up_opportunity.R;
import com.up.up_opportunity.model.housing.HousingHUD;
import com.up.up_opportunity.providers.HousingService;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by samsiu on 7/10/16.
 */
public class HousingFragment extends android.support.v4.app.Fragment implements HousingRVAdapter.HousingClickListener {

    private static final String TAG = "HOUSING_FRAGMENT";
    private RecyclerView housingRecyclerView;
    private SwipeRefreshLayout housingSwipeRefreshLayout;
    private HousingHUD housingHUD;


    private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;
    private HousingRVAdapter housingRVAdapter;
    SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_housing,container,false);
        setRetainInstance(true);
        //Log.d(TAG, "HousingFragment: OnCreateView");

        housingRecyclerView = (RecyclerView)view.findViewById(R.id.housing_recyclerView);
        housingSwipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.housing_swipeRefreshLayout);
        linearLayoutManager = new LinearLayoutManager(getContext());
        gridLayoutManager = new GridLayoutManager(getContext(), 2);
        sharedPreferences = getActivity().getSharedPreferences("HOUSING", Context.MODE_PRIVATE);

        housingSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryLight, R.color.colorAccent, R.color.colorPrimary);

        Gson gson = new Gson();
        String json = sharedPreferences.getString("HousingHUD","");
        if(json != ""){
            HousingHUD housingHUD = gson.fromJson(json, HousingHUD.class);
            housingRecyclerView.setLayoutManager(linearLayoutManager);
            //housingRecyclerView.setLayoutManager(gridLayoutManager);
            housingRVAdapter = new HousingRVAdapter(this, housingHUD);
            housingRecyclerView.setAdapter(housingRVAdapter);
        }

        swipeHousingRefreshListener();
        housingApiCall();

        return view;
    }

    private void swipeHousingRefreshListener(){
        housingSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //Log.d(TAG, "HOUSING FRAGMENT: onRefresh");
                refreshHousingContent();
            }
        });
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

        //Log.d(TAG, "HOUSING API CALL");

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        GsonBuilder gsonBuilder = new GsonBuilder()
                .setLenient();
        Gson gson = gsonBuilder.create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://data.hud.gov/Housing_Counselor/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();

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

       // Log.i(TAG, "HousingFragment: Card Clicked: " + link);
    }
}
