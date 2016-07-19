package com.up.up_opportunity.providers;

import com.up.up_opportunity.model.coupons.Coupons;
import com.up.up_opportunity.model.coupons.CouponsArray;
import com.up.up_opportunity.model.job.Indeed;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Billy on 7/9/16.
 */
public interface CouponService {

    @GET("getdeals?")
    Call<ResponseBody> getCoupons(@Query("key") String key,
                             @Query("zip") String zip,
                             @Query("mileradius") String mile,
                             @Query("limit") int limit,
                             @Query("orderby") String radius,
                             @Query("categoryid") String category
                             );

}
