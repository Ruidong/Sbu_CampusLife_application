package com.example.ruidong.sbu_application;

/**
 * Created by Ruidong on 5/30/2015.
 */
public class SbuDailyLifePOI extends POI {

    private String poiLabel;
    private String poiLocation;
    private String poiTime;
    private String poiPhone;
    private Double poiLatitude;
    private Double poiLongitude;
    private int poiId;




    public SbuDailyLifePOI(){

    }

    public SbuDailyLifePOI(int id, String label,String location, double latitude, double longitude,String time,String phone)
    {
        this.poiLabel = label;
        this.poiLocation=location;
        this.poiTime=time;
        this.poiPhone=phone;
        this.poiLatitude = latitude;
        this.poiLongitude = longitude;
        this.poiId=id;
    }

    public String getPoiLabel()
    {
        return poiLabel;
    }
    public void setPoiLabel(String Label)
    {
        this.poiLabel = Label;
    }


    public String getPoiLocation(){
        return poiLocation;
    }
    public void setPoiLocation(String location){
        this.poiLocation=location;
    }


    public String getPoiTime(){
        return poiTime;
    }
    public void setmFund_(String poiTime){
        this.poiTime=poiTime;
    }


    public String getPoiPhone(){
        return this.poiPhone;
    }
    public void setmPhone(String Phone){
        this.poiPhone=Phone;
    }


    public Double getmLatitude()
    {
        return poiLatitude;
    }
    public void setmLatitude(Double Latitude)
    {
        this.poiLatitude = Latitude;
    }


    public Double getmLongitude()
    {
        return poiLongitude;
    }
    public void setmLongitude(Double Longitude)
    {
        this.poiLongitude = Longitude;
    }
}