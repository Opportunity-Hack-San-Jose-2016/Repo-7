package com.up.up_opportunity.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.up.up_opportunity.R;
import com.up.up_opportunity.model.job.Indeed;
import com.up.up_opportunity.providers.IndeedService;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Query;

/**
 * Created by adao1 on 7/9/2016.
 */
public class JobsFragment extends android.support.v4.app.Fragment {

    private static final String TAG = "JOBS_FRAGMENT";
    private Button submitButton;


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

        submitButton = (Button)view.findViewById(R.id.job_fragment_submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
                OkHttpClient okHttpClient = new OkHttpClient.Builder()
                        .addInterceptor(logging)
                        .build();

                GsonBuilder gsonBuilder = new GsonBuilder()
                        .setLenient();
                Gson gson = gsonBuilder.create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://api.indeed.com/ads/")
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(okHttpClient)
                        .build();

                IndeedService service = retrofit.create(IndeedService.class);


                String apiKey = "7516191153543229";
                String query = "cashier";
                String city = "San Jose";
                String country = "us";
                String limit = "10";
                String latLong = "";
                String version = "2";
                String format = "json";


                Call<Indeed> call = service.getIndeedJobs(apiKey, query, city, country, limit, latLong, version, format);
                call.enqueue(new Callback<Indeed>() {
                    @Override
                    public void onResponse(Call<Indeed> call, Response<Indeed> response) {
                        if(response.isSuccessful()){
                            Indeed indeed = response.body();
                            String title = indeed.getResults().get(0).getJobtitle();
                            Log.d(TAG, "JOB TITLE: " + title);
                        }
                    }

                    @Override
                    public void onFailure(Call<Indeed> call, Throwable t) {
                        t.printStackTrace();
                    }
                });


            }
        });


    }
}
