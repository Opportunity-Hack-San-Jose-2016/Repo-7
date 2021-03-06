package com.up.up_opportunity.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.google.gson.Gson;
import com.up.up_opportunity.JobWebViewActivity;
import com.up.up_opportunity.R;
import com.up.up_opportunity.UpApplication;
import com.up.up_opportunity.keys.keys;
import com.up.up_opportunity.model.coupons.CouponsArray;
import com.up.up_opportunity.providers.CouponService;
import com.up.up_opportunity.recycler.CouponsRecyclerAdapter;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Named;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Billy on 7/9/16.
 */
public class CouponFragment extends Fragment implements CouponsRecyclerAdapter.CouponClickListener{

    private static final String TAG = "COUPON_FRAGMENT";
    private CouponService couponService;
    private CouponsRecyclerAdapter couponsRecyclerAdapter;
    private RecyclerView recyclerView;
    private Button submitButton;
    private EditText zipEditText;
    private EditText milesEditText;
    private String couponHTTP = "http://api.8coupons.com/v1/";
    private String zipCode = "95131";
    private String mileRadius = "20";
    private int limit = 50;
    private String orderBy = "radius";
    private String category = "2,6";
    SharedPreferences sharedPreferences;
    private SwipeRefreshLayout couponSwipeRefreshLayout;

    @Inject @Named("Coupon") Retrofit retrofit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coupon, container, false);
        setRetainInstance(true);

        initiViews(view);
        setLayoutManager();
        getSharedPreferences();
        injectDagger();

        displaySharedPreferences();

        setClickListener(submitButton);

        swipeCouponRefreshListener();

        return view;
    }

    private void initiViews(View view){
        recyclerView = (RecyclerView) view.findViewById(R.id.coupon_recycler_id);
        submitButton = (Button) view.findViewById(R.id.coupon_fragment_submit_button);
        zipEditText = (EditText) view.findViewById(R.id.coupon_zip_editText);
        milesEditText = (EditText) view.findViewById(R.id.coupon_miles_editText);

        couponSwipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.coupon_swipeRefreshLayout);
        couponSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryLight, R.color.colorAccent, R.color.colorPrimary);

    }

    private void setLayoutManager(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void getSharedPreferences(){
        sharedPreferences = getActivity().getSharedPreferences("COUPONS", Context.MODE_PRIVATE);
    }

    private void injectDagger(){
        ((UpApplication)getActivity().getApplication()).getNetComponent().inject(this);
    }

    private void displaySharedPreferences(){
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Coupons","");

        if(json != ""){
            CouponsArray couponsData = gson.fromJson(json, CouponsArray.class);
            //jobRecyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            couponsRecyclerAdapter = new CouponsRecyclerAdapter(CouponFragment.this, couponsData);
            recyclerView.setAdapter(couponsRecyclerAdapter);
        }else{
            makeRetrofitCall();
        }
    }

    private void setClickListener(Button button){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mileRadius = milesEditText.getText().toString();
                zipCode = zipEditText.getText().toString();
                makeRetrofitCall();
            }
        });
    }

    private void swipeCouponRefreshListener(){
        couponSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshCouponContent();
            }
        });
    }

    /**
     * Pull down to refresh will make new API call
     */
    private void refreshCouponContent(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                makeRetrofitCall();
                couponSwipeRefreshLayout.setRefreshing(false);
            }
        }, 0);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "onViewCreated: ");
    }

    private void makeRetrofitCall(){
        // &zip=95131&mileradius=20&limit=50&orderby=radius&categoryid=2,6

        couponService = retrofit.create(CouponService.class);
        Call<ResponseBody> call = couponService.getCoupons(keys.COUPON_KEY, zipCode, mileRadius, limit, orderBy, category);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String parsedRespone = null;
                try {
                    parsedRespone = response.body().string().replace("[{","{\"coupons\" : [{").replace("}]","}]}");
                    Log.e(TAG, "ZIP: " + zipCode);

                    Gson gson = new Gson();

                    CouponsArray couponsData = gson.fromJson(parsedRespone.trim(), CouponsArray.class);
                    if (couponsData == null) {
                        return;
                    }

                    if(recyclerView != null){
                        couponsRecyclerAdapter = new CouponsRecyclerAdapter(CouponFragment.this, couponsData);
                        recyclerView.setAdapter(couponsRecyclerAdapter);
                        couponsRecyclerAdapter.notifyDataSetChanged();
                    }

                    //Collections.addAll(couponsList, couponsData);
    //                Log.i(TAG, " "+ couponsList);

                    SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
                    Gson gsonCoupon = new Gson();
                    String json = gsonCoupon.toJson(couponsData);
                    prefsEditor.putString("Coupons", json);
                    prefsEditor.commit();


                    if (recyclerView != null) {

                        }
                } catch (IOException e) {
                    Log.e(TAG, "STACKTRACE");
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
        Intent intent = new Intent(getActivity(), JobWebViewActivity.class);
        intent.putExtra("link", link);
        startActivity(intent);
        //Log.d(TAG, "CouponFragment: Card Clicked: " + link);
    }
}
