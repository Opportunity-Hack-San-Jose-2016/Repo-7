package com.up.up_opportunity.model.event;

/**
 * Created by yash on 7/9/2016.
 */
public class GoogleResults {
    String name;
    Geometry geometry;
    String vicinity;

    public String getVicinity() {
        return vicinity;
    }

    public String getName() {
        return name;
    }

    public Geometry getGeometry() {
        return geometry;
    }

}
