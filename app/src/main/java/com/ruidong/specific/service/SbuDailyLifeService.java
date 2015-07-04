package com.ruidong.specific.service;

/**
 * Created by Ruidong on 5/27/2015.
 */

import java.util.ArrayList;
import java.util.Collection;



import org.json.JSONException;
import org.json.JSONObject;

import com.example.ruidong.sbu_application.OncampusAppService;
import com.example.ruidong.sbu_application.POI;
import com.example.ruidong.sbu_application.SbuDailyLifePOI;
import com.google.android.gms.maps.model.LatLng;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;


public class SbuDailyLifeService implements OncampusAppService {

    public static Multimap <String,POI> dailyMap=ArrayListMultimap.create();
    public static Multimap <String,String> dailyAliasMap=ArrayListMultimap.create();
    public boolean dailyFlag= false;
    public ArrayList<POI> dailyList = new ArrayList<POI>();
    public ArrayList<String> dailyHintList = new ArrayList<String>();

    public SbuDailyLifeService (){

    }





    public ArrayList<String> getNameList(String str){
        Collection<String> myNameCollection = dailyAliasMap.get(str);
        ArrayList<String> list = new ArrayList<>();
         list.addAll(myNameCollection);
        return  list;
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
       public ArrayList<POI> getTargetPOI(String str) {
        Collection<POI> myPOICollection =dailyMap.get(str);
        ArrayList<POI> list = new ArrayList<>();
        list.addAll(myPOICollection);
        return  list;
    }

    @Override
    public boolean checkMap(String str){
        return dailyMap.containsKey(str);
    }

    public void setDailyMap(){
        dailyMap.put("food",new SbuDailyLifePOI(1,"food","Salad","Wang Center","7:00 am to 10:00 pm", "631-681-8857",new LatLng(40.914833, -73.127761),"haha"));
        dailyMap.put("food",new SbuDailyLifePOI(1,"food","Soup","Wang Center","7:00 am to 10:00 pm", "631-681-8857",new LatLng(40.912692, -73.126945),"hahaha"));
    }

    public void storeData(JSONObject json) throws JSONException{

        POI poi = new SbuDailyLifePOI(json.getInt("POI_ID"), json.getString("POI_Category"),json.getString("POI_Name"),
                json.getString("Location_name"),json.getString("Available_time"),
                json.getString("Phone"),new LatLng(json.getDouble("Latitude"),json.getDouble("Longitude")),json.getString("Description"));

        dailyMap.put(json.getString("POI_Category").toLowerCase(), poi);
        dailyMap.put(json.getString("POI_Name").toLowerCase(),poi);
        dailyList.add(poi);

    }

    public void storeAlias(JSONObject json) throws  JSONException{
        dailyAliasMap.put(json.getString("Aliase").toLowerCase(),json.getString("POI_Category").toLowerCase());
        dailyHintList.add(json.getString("Aliase"));
    }

}