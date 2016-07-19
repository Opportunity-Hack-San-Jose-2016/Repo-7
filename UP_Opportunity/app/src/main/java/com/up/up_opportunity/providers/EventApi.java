package com.up.up_opportunity.providers;

import com.up.up_opportunity.model.event.GoogleEvent;
import com.up.up_opportunity.model.job.Indeed;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by yash on 7/9/2016.
 */
public interface EventApi {

    @GET("json?")
    Call<GoogleEvent> getGoogleEvents(@Query("location") String latLong,
                                    @Query("radius") String radius,
                                    @Query("name") String eventName,
                                    @Query("key") String key

    );
}
