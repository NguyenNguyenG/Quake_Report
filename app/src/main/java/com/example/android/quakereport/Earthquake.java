package com.example.android.quakereport;

/**
 * Created by nguyennguyen on 9/12/17.
 */

public class Earthquake {
    private String mlocation;
    private double mMagnitute;
    private long timeInMillis;
    private String mUrl;

    public Earthquake(double magnitute, String location , long time, String url)
    {
        mlocation = location;
        mMagnitute = magnitute;
        timeInMillis = time;
        mUrl = url;
    }

    public String getLocation()
    {
        return mlocation;
    }

    public double getMagnitude()
    {
        return mMagnitute;
    }

    public long getDate()
    {
        return timeInMillis;
    }

    public String getUrl(){
        return mUrl;
    }



}
