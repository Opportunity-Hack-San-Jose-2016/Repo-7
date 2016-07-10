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

import com.up.up_opportunity.R;
import com.up.up_opportunity.keys.keys;
import com.up.up_opportunity.model.coupons.Coupons;
import com.up.up_opportunity.providers.CouponService;
import com.up.up_opportunity.recycler.CouponsRecyclerAdapter;

import java.util.ArrayList;
import java.util.Collections;

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
    private ArrayList<Coupons> couponsList;
    private String couponHTTP = "http://api.8coupons.com/v1/getdeals?key=";

    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_coupon, container, false);
        setRetainInstance(true);
        couponsList = new ArrayList<>();
        recyclerView = (RecyclerView) v.findViewById(R.id.list_recyclerView_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        couponsRecyclerAdapter = new CouponsRecyclerAdapter(couponsList);
        recyclerView.setAdapter(couponsRecyclerAdapter);


        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "onViewCreated: ");
    }

    private void retrofit(int zipCode){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(couponHTTP + keys.COUPON_KEY)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        couponService = retrofit.create(CouponService.class);
        Call<Coupons> call = couponService.getCoupons(zipCode);
        call.enqueue(new Callback<Coupons>() {
            @Override
            public void onResponse(Call<Coupons> call, Response<Coupons> response) {
                Coupons couponsResponse = response.body();
                if (couponsResponse == null) {
                    return;
                }
                Collections.addAll(couponsList, couponsResponse.getResult());


                if (recyclerView != null) {
                    couponsRecyclerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Coupons> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    }

}
