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
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;


import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;



public class SbuDailyLifeService implements OncampusAppService {

    public  Multimap <String,POI> dailyMap=ArrayListMultimap.create();
    public boolean dailyFlag= false;

    public SbuDailyLifeService (){

    }

    @Override
    public Collection<POI> getTargetPOI(String str){
        Collection<POI> myPOICollection = dailyMap.get(str);
        return myPOICollection;
    }

    @Override
    public String firstTextInfo(POI POI_element){
        String str="Location:"+POI_element.getPoiLocation();
        return str;
    }

    @Override
    public String secondTextInfo(POI POI_element){
        String str="Available_Time:"+POI_element.getPoiFundDetail();
        return str;
    }

    @Override
    public boolean checkMap(String str){
        return dailyMap.containsKey(str);
    }


    public void storeData(JSONObject json, String keyword) throws JSONException{


        dailyMap.put(keyword.toLowerCase(), new POI(json.getInt("POI_ID"), json.getString("POI_Name"),
                json.getString("Location_name"), json.getDouble("Latitude"), json.getDouble("Longtitude"), json.getString("Available_time"),
                json.getString("Phone")));



    }


    public ArrayList<String> getTargetListView(POI POI_element){

        String[] values = new String[] {"Name:  "+POI_element.getPoiLabel(),"Location:  "+POI_element.getPoiLocation(),
                "Available Time:  "+POI_element.getPoiFundDetail(),"Contact Phone:  "+POI_element.getmPhone()};

        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < values.length; ++i) {
            list.add(values[i]);
        }

        return list;
    }


    class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }

}