package com.example.ruidong.sbu_application;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Ruidong on 6/29/2015.
 */
public class EventPOI extends POI {
    private String EventName;
    private String EventDescription;
    private String EventContactName;
    private String EventContactPhone;
    private String EventContactEmail;
    private String EventLocation;
    private String EventTime;
    private int PoiID;
    private final LatLng mPosition;


    public EventPOI(int id, String EventName, String EventLocation ,String EventTime ,String EventContactEmail,String EventDescription, LatLng position) {
        super(id, EventName, EventLocation, position);
        this.EventName = EventName;
        this.EventLocation = EventLocation;
        this.EventTime = EventTime;
        this.EventDescription = EventDescription;
        this.PoiID = id;
        mPosition = position;
    }

    public String getEventName() {
        return EventName;
    }

    public String getEventLocation() {
        return EventLocation;
    }

    public String getEventTime() {
        return EventTime;
    }

    public String getEventDescription() {
        return EventDescription;
    }

    public String getEventContactName() {
        return EventContactName;
    }

    public String getEventContactPhone() {
        return EventContactPhone;
    }

    public String getEventContactEmail() {
        return EventContactEmail;
    }

    public void setEventName(String eventName) {
        EventName = eventName;
    }

    public void setEventLocation(String eventLocation) {
        EventLocation = eventLocation;
    }

    public void setEventTime(String eventTime) {
        EventTime = eventTime;
    }

    public void setEventDescription(String eventDescription) {
        EventDescription = eventDescription;
    }
}
