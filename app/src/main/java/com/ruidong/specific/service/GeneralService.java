package com.ruidong.specific.service;

/**
 * Created by Ruidong on 5/27/2015.
 */
import com.example.ruidong.sbu_application.OncampusAppService;
import com.example.ruidong.sbu_application.POI;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.util.ArrayList;
import java.util.Collection;



public class GeneralService implements OncampusAppService {

    public Multimap<String,POI> genMap= ArrayListMultimap.create();
    public  boolean genFlag=false;
    public GeneralService (){

        //genMap.put("sac",new POI("sac", "Student Activity Center", " ","631-681-0000", 40.914505, -73.124266,10));
    }

    @Override
    public String firstTextInfo(POI POI_element){
        String str="Name:"+POI_element.getPoiLabel();
        return str;
    }
    @Override
    public String secondTextInfo(POI POI_element){

        String str="Location:"+POI_element.getPoiLocation();
        return str;
    }
    @Override
    public ArrayList<POI> getTargetPOI(String str){
        Collection<POI> myPOICollection = genMap.get(str);
        ArrayList<POI> list = new ArrayList<>();
        list.addAll(myPOICollection);
        return list;
    }
    @Override
    public boolean checkMap(String str){
        return genMap.containsKey(str);
    }
}
