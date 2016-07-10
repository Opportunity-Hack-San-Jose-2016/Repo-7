package com.up.up_opportunity.providers;

import com.up.up_opportunity.model.job.Indeed;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by samsiu on 7/9/16.
 */
public interface IndeedService {



    // http://api.indeed.com/ads/apisearch?
    // publisher=7516191153543229&
    // q=cashier&
    // l=san%20jose%2C+tx&
    // sort=&
    // radius=&
    // st=&
    // jt=&
    // start=&
    // limit=&
    // fromage=&
    // filter=&
    // latlong=1&
    // co=us&
    // chnl=&
    // userip=1.2.3.4&
    // useragent=Mozilla/%2F4.0%28Firefox%29&
    // v=2&
    // format=json


    @GET("apisearch?")
    Call<Indeed> getIndeedJobs(@Query("publisher") String apiKey,
                           @Query("q") String query,
                           @Query("l") String city,
                           @Query("co") String country,
                           @Query("limit") String limit,
                           @Query("latlong") String latLong,
                           @Query("v") String version,
                           @Query("format") String format
    );


}
