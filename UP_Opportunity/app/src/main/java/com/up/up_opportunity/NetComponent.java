package com.up.up_opportunity;

import com.up.up_opportunity.fragments.CouponFragment;
import com.up.up_opportunity.fragments.EventsFragment;
import com.up.up_opportunity.fragments.FoodBankFragment;
import com.up.up_opportunity.fragments.housing.HousingFragment;
import com.up.up_opportunity.fragments.jobs.JobsFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by samsiu on 7/17/16.
 */
@Singleton
@Component(modules={NetModule.class})
public interface NetComponent {

    void inject(HousingFragment fragment);
    void inject(JobsFragment fragment);
    void inject(CouponFragment fragment);
    void inject(EventsFragment fragment);
    void inject(FoodBankFragment fragment);

}
