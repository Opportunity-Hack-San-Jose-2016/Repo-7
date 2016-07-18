package com.up.up_opportunity.fragments.jobs;

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
import com.up.up_opportunity.UpApplication;
import com.up.up_opportunity.model.job.Indeed;
import com.up.up_opportunity.providers.IndeedService;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Named;

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
public class JobsFragment extends android.support.v4.app.Fragment implements JobsRVAdapter.JobClickListener {

    private static final String TAG = "JOBS_FRAGMENT";
    private Button submitButton;
    private EditText cityEditText;
    private EditText jobTitleEditText;
    private RecyclerView jobRecyclerView;
    private SwipeRefreshLayout jobsSwipeRefreshLayout;
    private Indeed indeed;


    private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;
    private JobsRVAdapter jobsRVAdapter;
    SharedPreferences sharedPreferences;

    @Inject @Named("Jobs")Retrofit retrofit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jobs,container,false);
        setRetainInstance(true);
        //Log.d(TAG, "JobsFragment: OnCreateView");

        cityEditText = (EditText)view.findViewById(R.id.job_city_editText);
        jobTitleEditText = (EditText)view.findViewById(R.id.job_title_editText);
        jobRecyclerView = (RecyclerView)view.findViewById(R.id.job_recyclerView);
        jobsSwipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.job_swipeRefreshLayout);
        linearLayoutManager = new LinearLayoutManager(getContext());
        gridLayoutManager = new GridLayoutManager(getContext(), 2);
        sharedPreferences = getActivity().getSharedPreferences("JOBS", Context.MODE_PRIVATE);

        jobsSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryLight, R.color.colorAccent, R.color.colorPrimary);

        ((UpApplication)getActivity().getApplication()).getNetComponent().inject(this);

        Gson gson = new Gson();
        String json = sharedPreferences.getString("Indeed","");
        if(json != ""){
            Indeed indeed = gson.fromJson(json, Indeed.class);
            jobRecyclerView.setLayoutManager(linearLayoutManager);
            //jobRecyclerView.setLayoutManager(gridLayoutManager);
            jobsRVAdapter = new JobsRVAdapter(this, indeed.getResults());
            jobRecyclerView.setAdapter(jobsRVAdapter);
        }

        swipeJobsRefreshListener();

        return view;
    }

    private void swipeJobsRefreshListener(){
        jobsSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshJobsContent();
            }
        });
    }

    /**
     * Pull down to refresh will make new API call
     */
    private void refreshJobsContent(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                jobsApiCall();
                jobsSwipeRefreshLayout.setRefreshing(false);
            }
        }, 0);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Log.i(TAG, "onViewCreated: ");


        submitButton = (Button)view.findViewById(R.id.job_fragment_submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jobsApiCall();
            }
        });
    }

    private void jobsApiCall(){

        IndeedService service = retrofit.create(IndeedService.class);

        String apiKey = "7516191153543229";
        String query = jobTitleEditText.getText().toString();
        String city = cityEditText.getText().toString();
        String country = "us";
        String limit = "40";
        String latLong = "";
        String version = "2";
        String format = "json";

        Call<Indeed> call = service.getIndeedJobs(apiKey, query, city, country, limit, latLong, version, format);
        call.enqueue(new Callback<Indeed>() {
            @Override
            public void onResponse(Call<Indeed> call, Response<Indeed> response) {
                if(response.isSuccessful()){
                    indeed = response.body();
                   // String title = indeed.getResults().get(0).getJobtitle();
                    //Log.d(TAG, "JOB TITLE: " + title);

                    if(jobRecyclerView != null){
                        jobRecyclerView.setLayoutManager(linearLayoutManager);
                        jobsRVAdapter = new JobsRVAdapter(JobsFragment.this, indeed.getResults());
                        jobRecyclerView.setAdapter(jobsRVAdapter);
                    }


                    SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
                    Gson gson = new Gson();
                    String json = gson.toJson(indeed);
                    prefsEditor.putString("Indeed", json);
                    prefsEditor.commit();
                }
            }

            @Override
            public void onFailure(Call<Indeed> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }


    @Override
    public void onCardViewClick(String link) {
        Intent intent = new Intent(getActivity(), JobWebViewActivity.class);
        intent.putExtra("link", link);
        startActivity(intent);
       // Log.d(TAG, "JobsFragment: Card Clicked: " + link);
    }
}
