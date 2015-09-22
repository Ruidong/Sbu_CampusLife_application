package com.example.ruidong.sbu_application.framework;

/**
 * Created by Ruidong on 5/27/2015.
 */
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public  class  OncampusAppService {

    public Multimap<String,POI> serviceMap= ArrayListMultimap.create();
    public ArrayList<String> serviceHintList = new ArrayList<String>();

    public Multimap getServiceMap(){
        return null;
    }
    public void clearMap() {

    }

    public void setMap() {

    }

    public String firstTextInfo(POI POI_element) {
        return null;
    }


    public String secondTextInfo(POI POI_element) {
        return null;
    }


    public ArrayList<POI> getTargetPOI(String str) {
        return null;
    }


    public boolean checkMap(String str) {
        return false;
    }

    public void storeData(JSONObject json) throws JSONException {

    }

    public void obtainData(JSONArray jsonArray) {

    }

}
