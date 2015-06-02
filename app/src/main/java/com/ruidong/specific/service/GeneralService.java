package com.ruidong.specific.service;

/**
 * Created by Ruidong on 5/27/2015.
 */
import com.example.ruidong.sbu_application.OncampusAppService;
import com.example.ruidong.sbu_application.POI;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.util.Collection;



public class GeneralService implements OncampusAppService {

    public Multimap<String,POI> genMap= ArrayListMultimap.create();
    public  boolean genFlag=false;
    public GeneralService (){

        //genMap.put("sac",new POI("sac", "Student Activity Center", " ","631-681-0000", 40.914505, -73.124266,10));
    }

    @Override
    public String firstTextInfo(POI POI_element){
        String str="Location:"+POI_element.getPoiLocation();
        return str;
    }
    @Override
    public String secondTextInfo(POI POI_element){
        String str="Contact Phone:"+POI_element.getPoiPhone();
        return str;
    }
    @Override
    public Collection<POI> getTargetPOI(String str){
        Collection<POI> myPOICollection = genMap.get(str);
        return myPOICollection;
    }
    @Override
    public boolean checkMap(String str){
        return genMap.containsKey(str);
    }
}
