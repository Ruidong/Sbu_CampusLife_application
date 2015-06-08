package com.ruidong.specific.service;

/**
 * Created by Ruidong on 5/27/2015.
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.ruidong.sbu_application.OncampusAppService;
import com.example.ruidong.sbu_application.POI;
import com.example.ruidong.sbu_application.SbuDailyLifePOI;
import com.google.android.gms.maps.model.LatLng;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;


import android.content.Context;
import android.widget.ArrayAdapter;


public class SbuDailyLifeService implements OncampusAppService {

    public  Multimap <String,POI> dailyMap=ArrayListMultimap.create();
    public boolean dailyFlag= false;

    public SbuDailyLifeService (){

    }


    @Override
    public Collection<POI> getTargetPOI(String str){

        Collection<POI> myPOICollection =dailyMap.get(str);
        return myPOICollection;
    }

    @Override
    public String firstTextInfo(POI POI_element){
        SbuDailyLifePOI SDLPoi = (SbuDailyLifePOI)POI_element;
        String str=SDLPoi.getPoiLabel();
        return str;
    }

    @Override
    public String secondTextInfo(POI POI_element){
        SbuDailyLifePOI SDLPoi = (SbuDailyLifePOI)POI_element;
        String str="Location: "+SDLPoi.getPoiLocation();
        return str;
    }

    @Override
    public boolean checkMap(String str){
        return dailyMap.containsKey(str);
    }


    public void storeData(JSONObject json, String keyword) throws JSONException{


        dailyMap.put(keyword.toLowerCase(), new SbuDailyLifePOI(json.getInt("POI_ID"), json.getString("POI_Name"),
                json.getString("Location_name"),json.getString("Available_time"),
                json.getString("Phone"),new LatLng(json.getDouble("Latitude"),json.getDouble("Longtitude"))));
    }

}