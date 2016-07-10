package com.up.up_opportunity.model.job;

/**
 * Created by samsiu on 7/9/16.
 */
public class IndeedResults {

    String jobtitle;
    String company;
    String city;
    String state;
    String country;
    String formattedLocation;
    String source;
    String date;
    String snippet;
    String url;
    String onmousedown;
    double latitude;
    double longitude;
    String jobkey;
    boolean sponsored;
    boolean expired;
    boolean indeedApply;
    String formattedLocationFull;
    String formattedRelativeTime;


    public String getJobtitle() {
        return jobtitle;
    }

    public String getCompany() {
        return company;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public String getFormattedLocation() {
        return formattedLocation;
    }

    public String getSource() {
        return source;
    }

    public String getDate() {
        return date;
    }

    public String getSnippet() {
        return snippet;
    }

    public String getUrl() {
        return url;
    }

    public String getOnmousedown() {
        return onmousedown;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getJobkey() {
        return jobkey;
    }

    public boolean isSponsored() {
        return sponsored;
    }

    public boolean isExpired() {
        return expired;
    }

    public boolean isIndeedApply() {
        return indeedApply;
    }

    public String getFormattedLocationFull() {
        return formattedLocationFull;
    }

    public String getFormattedRelativeTime() {
        return formattedRelativeTime;
    }
}
