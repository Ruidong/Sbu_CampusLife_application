package com.ruidong.event.fragment;

/**
 * Created by Ruidong on 6/29/2015.
 */
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.ruidong.sbu_application.EventPOI;
import com.example.ruidong.sbu_application.NavigationActivity;
import com.example.ruidong.sbu_application.POI;

/**
 * @author Mukesh Y
 */
public class Utility {
    public static ArrayList<String> nameOfEvent = new ArrayList<String>();
    public static ArrayList<String> startDates = new ArrayList<String>();
    public static ArrayList<String> descriptions = new ArrayList<String>();
    public static ArrayList<EventPOI> eventPoiList = new ArrayList<>();
    private NavigationActivity activity;

    public Utility(NavigationActivity activity){
        this.activity=activity;
    }


    public  ArrayList<String> readCalendarEvent(Context context) {
        // fetching calendars name
        ArrayList<POI> POIlist = activity.getEventService().getTargetPOI("my event");

        // fetching calendars id
        eventPoiList.clear();
        nameOfEvent.clear();
        startDates.clear();
        descriptions.clear();
        for (POI poi : POIlist) {
            EventPOI eventPOI =(EventPOI) poi;
            eventPoiList.add(eventPOI);
            nameOfEvent.add(eventPOI.getEventName());
            startDates.add(getDate(eventPOI.getEventTime()));
            descriptions.add(eventPOI.getEventDescription());
        }
        return nameOfEvent;
    }

    public  String getDate(String value1) {
        String[] names = value1.split("\\/");
        String date;
        if(Integer.valueOf(names[0])<10)
        {
            date = names[2]+"-"+"0"+names[0]+"-"+names[1];
        }
        else
        {
            date = names[2]+"-"+names[0]+"-"+names[1];
        }
        return  date;
    }
}