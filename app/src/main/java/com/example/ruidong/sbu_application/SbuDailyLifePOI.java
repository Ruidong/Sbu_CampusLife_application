package com.example.ruidong.sbu_application;

/**
 * Created by Ruidong on 5/30/2015.
 */
public class SbuDailyLifePOI {

    private String poiLabel;
    private String poiLocatoin;
    private String poiTime;
    private String poiPhone;
    private Double poiLatitude;
    private Double poiLongitude;
    private int poiId;

    public SbuDailyLifePOI(int id, String label,String location, double latitude, double longitude,String time,String phone)
    {
        this.poiLabel = label;
        this.poiLocatoin=location;
        this.poiTime=time;
        this.poiPhone=phone;
        this.poiLatitude = latitude;
        this.poiLongitude = longitude;
        this.poiId=id;
    }
}
