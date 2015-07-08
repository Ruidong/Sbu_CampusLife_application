package com.example.ruidong.sbu_application.dailylife.service;

import com.example.ruidong.sbu_application.framework.POI;
import com.google.android.gms.maps.model.LatLng;

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
    private String poiDescription;
    private String poiCategory;
    private int poiId;
    private final LatLng mPosition;


    public SbuDailyLifePOI(int id, String category, String label, String location, String time, String phone, LatLng position, String description)
    {
        super(id, label, location, position);
        this.poiLabel = label;
        this.poiCategory=category;
        this.poiLocation=location;
        this.poiTime=time;
        this.poiPhone=phone;
        mPosition = position;
        this.poiId=id;
        this.poiDescription = description;
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

    public String getPoiCategory(){
        return  this.poiCategory;
    }
    public void setPoiCategory(String category){
        this.poiCategory = category;
    }

    public String getPoiDescription(){
        return  this.poiDescription;
    }
    public void setPoiDescription(String description){
        this.poiDescription = description;
    }


}