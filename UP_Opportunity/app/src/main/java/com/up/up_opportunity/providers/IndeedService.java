package com.up.up_opportunity.providers;

import com.up.up_opportunity.model.job.Indeed;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by samsiu on 7/9/16.
 */
public interface IndeedService {

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
