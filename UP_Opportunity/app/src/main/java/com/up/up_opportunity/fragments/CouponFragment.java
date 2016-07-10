package com.up.up_opportunity.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.up.up_opportunity.R;
import com.up.up_opportunity.keys.keys;
import com.up.up_opportunity.model.coupons.Coupons;
import com.up.up_opportunity.model.coupons.CouponsArray;
import com.up.up_opportunity.providers.CouponService;
import com.up.up_opportunity.recycler.CouponsRecyclerAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Billy on 7/9/16.
 */
public class CouponFragment extends Fragment {

    private static final String TAG = "COUPON_FRAGMENT";
    private CouponService couponService;
    private CouponsRecyclerAdapter couponsRecyclerAdapter;
    private RecyclerView recyclerView;
    private CouponsArray couponsList;
    private String couponHTTP = "http://api.8coupons.com/v1/";
    private int zipCode = 95131;
    private int mileRadius = 20;
    private int limit = 10;
    private String orderBy = "radius";
    private String category = "2,6";




    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_coupon, container, false);
        setRetainInstance(true);
        recyclerView = (RecyclerView) v.findViewById(R.id.coupon_recycler_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        retrofit();

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "onViewCreated: ");
    }

    private void retrofit(){

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        GsonBuilder gsonBuilder = new GsonBuilder().setLenient();
        Gson gson = gsonBuilder.create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(couponHTTP)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();

        // &zip=95131&mileradius=20&limit=50&orderby=radius&categoryid=2,6

        couponService = retrofit.create(CouponService.class);
        Call<ResponseBody> call = couponService.getCoupons(keys.COUPON_KEY, zipCode, mileRadius, limit, orderBy, category);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String parsedRespone = null;
                try {
                    parsedRespone = response.body().string().replace("[","{\"coupons\" : [").replace("]","]}");
                    Gson gson = new Gson();

                    CouponsArray couponsData = gson.fromJson(parsedRespone, CouponsArray.class);
                    if (couponsData == null) {
                        return;
                    }

                    couponsRecyclerAdapter = new CouponsRecyclerAdapter(couponsData);
                    recyclerView.setAdapter(couponsRecyclerAdapter);
                    couponsRecyclerAdapter.notifyDataSetChanged();
                    //Collections.addAll(couponsList, couponsData);
    //                Log.i(TAG, " "+ couponsList);
                    if (recyclerView != null) {

                        }
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
}
