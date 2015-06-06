package com.example.ruidong.sbu_application;

/**
 * Created by Ruidong on 5/27/2015.
 */
import android.R.integer;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

import java.util.Comparator;

public class POI implements ClusterItem {
    private String poiLabel;
    private String poiLocatoin;
    private int poiId;
    private final LatLng mPosition;


    public POI(int id, String label,String location, LatLng position)
    {
        this.poiLabel = label;
        this.poiLocatoin=location;
        this.poiId=id;
        mPosition = position;
    }


    public int getPoiId()
    {
        return poiId;
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
        return poiLocatoin;
    }
    public void setPoiLocation(String location){
        this.poiLocatoin=location;
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }
}