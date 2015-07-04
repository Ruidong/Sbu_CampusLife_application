package com.ruidong.specific.service;

import com.example.ruidong.sbu_application.CourseManagerPOI;
import com.example.ruidong.sbu_application.EventPOI;
import com.example.ruidong.sbu_application.OncampusAppService;
import com.example.ruidong.sbu_application.POI;
import com.google.android.gms.maps.model.LatLng;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.ruidong.courseManager.fragment.Course;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Ruidong on 6/29/2015.
 */
public class EventService implements OncampusAppService {

    public static Multimap<String,POI> eventMap= ArrayListMultimap.create();

    public EventService(){

    }

    @Override
    public String firstTextInfo(POI POI_element) {
        EventPOI poi = (EventPOI) POI_element;
        String str = poi.getEventName();
        return str;
    }

    @Override
    public String secondTextInfo(POI POI_element) {
        EventPOI poi = (EventPOI) POI_element;
        return poi.getEventLocation();
    }

    @Override
    public ArrayList<POI> getTargetPOI(String str) {
        Collection<POI> myPOICollection =eventMap.get(str);
        ArrayList<POI> list = new ArrayList<>();
        list.addAll(myPOICollection);
        return  list;
    }

    @Override
    public boolean checkMap(String str) {

        return eventMap.containsKey(str);
    }


    public void setEventMapFromCSV( ArrayList<String[]> list){
        String str = list.get(1)[1];

    }




    public void setEventMap(){
        eventMap.clear();
        eventMap.put("my event", new EventPOI(1, "Love and Blessings: The Art of Baby Carriers", "Wang Center", "6/24/2015", "rebornzrd@gmail.com",
                "Celebrating precious traditions and the culture of baby carriers from Taiwan and Southwestern China"
                , new LatLng(40.914833, -73.127761)));
        eventMap.put("my event", new EventPOI(1, "Event 1", "Wang Center", "6/24/2015", "rebornzrd@gmail.com",
                "Celebrating precious traditions and the culture of baby carriers from Taiwan and Southwestern China"
                , new LatLng(40.912692, -73.126945)));
        eventMap.put("my event", new EventPOI(1, "Event 11", "Wang Center", "6/24/2015", "rebornzrd@gmail.com",
                "Celebrating precious traditions and the culture of baby carriers from Taiwan and Southwestern China"
                , new LatLng(40.914962, -73.119650)));
        eventMap.put("my event", new EventPOI(2, "Event 2 ", "Wang Center", "6/24/2015","rebornzrd@gmail.com",
                "Celebrating precious traditions and the culture of baby carriers from Taiwan and Southwestern China"
                ,new LatLng(40.912970, -73.122018)));
        eventMap.put("my event", new EventPOI(2, "Event 3 ", "Wang Center", "6/25/2015","rebornzrd@gmail.com",
                "Celebrating precious traditions and the culture of baby carriers from Taiwan and Southwestern China"
                ,new LatLng(40.912990, -73.122018)));
        eventMap.put("my event", new EventPOI(2, "Event 4 ", "Wang Center", "6/25/2015","rebornzrd@gmail.com",
                "Celebrating precious traditions and the culture of baby carriers from Taiwan and Southwestern China"
                ,new LatLng(40.912990, -73.122018)));
    }
}