package com.up.up_opportunity.providers;

import com.up.up_opportunity.model.housing.HousingHUD;
import com.up.up_opportunity.model.job.Indeed;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by samsiu on 7/10/16.
 */
public interface HousingService {

    // http://data.hud.gov/Housing_Counselor/
    // searchByLocation?
    // Lat=37.2970155&
    // Long=-121.8174129&
    // Distance=50&
    // RowLimit=&
    // Services=&
    // Languages=

    @GET("searchByLocation?")
    Call<ResponseBody> getHousingAgencies(@Query("Lat") String latitude,
                                          @Query("Long") String longitude,
                                          @Query("Distance") String distance,
                                          @Query("RowLimit") String rowLimit,
                                          @Query("Services") String services,
                                          @Query("Languages") String languages
    );
}
