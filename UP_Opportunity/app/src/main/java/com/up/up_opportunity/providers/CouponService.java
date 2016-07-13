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



    // http://api.8coupons.com/v1/getdeals?key=360910f6c6c64841cae140dcdfcc9962ffd260d33b7fdde1dddad5ea2cfee4fca6093d039948f5a86182c0c94baacba2
    // &zip=95131&mileradius=20&limit=50&orderby=radius&categoryid=2,6

    @GET("getdeals?")
    Call<ResponseBody> getCoupons(@Query("key") String key,
                             @Query("zip") String zip,
                             @Query("mileradius") String mile,
                             @Query("limit") int limit,
                             @Query("orderby") String radius,
                             @Query("categoryid") String category
                             );

}
