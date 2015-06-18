package com.example.ruidong.sbu_application;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by Ruidong on 6/15/2015.
 */
public class CourseManagerPOI extends POI {
    private String courseNumber;
    private String courseName;
    private String courseLocation;
    private String courseTime;
    private ArrayList<String> courseDay = new ArrayList<>();
    private Double courseLatitude;
    private Double courseLongitude;
    private String courseDescription;
    private String courseInstructor;
    private int poiId;
    private final LatLng mPosition;


    public CourseManagerPOI(int id, String courseNumber, String courseName, String courseLocation, String courseTime, String courseInstructor, LatLng position) {
        super(id, courseNumber, courseLocation, position);
        this.courseNumber = courseNumber;
        this.courseName = courseName;
        this.courseLocation = courseLocation;
        this.courseTime = courseTime;
        this.courseInstructor = courseInstructor;
        mPosition = position;
        this.poiId = id;
        setCourseDay();
    }


    public String getCourseNumber() {
        return courseNumber;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseLocation() {
        return courseLocation;
    }

    public String getCourseTime() {
        return courseTime;
    }

    public String getCourseInstructor() {
        return courseInstructor;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }

    public void setCourseLocation(String courseLocation) {
        this.courseLocation = courseLocation;
    }

    public void setCourseTime(String courseTime) {
        this.courseTime = courseTime;
    }

    public void setCourseInstructor(String courseInstructor) {
        this.courseInstructor = courseInstructor;
    }

    public void setCourseDay(){
        courseDay.add("Monday");
        courseDay.add("Thursday");
    }

    public ArrayList<String> getCourseDay(){
        return this.courseDay;
    }
}
