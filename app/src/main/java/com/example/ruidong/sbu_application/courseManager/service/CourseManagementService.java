package com.example.ruidong.sbu_application.courseManager.service;

/**
 * Created by Ruidong on 5/27/2015.
 */
import com.example.ruidong.sbu_application.courseManager.service.CourseManagerPOI;
import com.example.ruidong.sbu_application.framework.OncampusAppService;
import com.example.ruidong.sbu_application.framework.POI;
import com.google.android.gms.maps.model.LatLng;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.example.ruidong.sbu_application.courseManager.service.Course;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;



public class CourseManagementService implements OncampusAppService {
    private ArrayList<Course> coursesHistoryList = new ArrayList<>();
    public static Multimap<String,POI> courseMap= ArrayListMultimap.create();
    private JSONArray array;
    public CourseManagementService(){

    }

    @Override
    public String firstTextInfo(POI POI_element) {
        CourseManagerPOI poi = (CourseManagerPOI) POI_element;
        String str = poi.getCourseNumber()+" - "+poi.getCourseName();
        return str;
    }

    @Override
    public String secondTextInfo(POI POI_element) {
        CourseManagerPOI poi = (CourseManagerPOI) POI_element;
        return poi.getCourseTime();
    }

    @Override
    public ArrayList<POI> getTargetPOI(String str) {
            Collection<POI> myPOICollection =courseMap.get(str);
            ArrayList<POI> list = new ArrayList<>();
            list.addAll(myPOICollection);
            return  list;
    }

    @Override
    public boolean checkMap(String str) {

        return courseMap.containsKey(str);
    }

    public void setCourseMap(){
        courseMap.clear();
        courseMap.put("my course", new CourseManagerPOI(1,"AMS542","Analysis of Algorithms","JAVITS LECTR 103","MoFr 1:00PM - 2:20PM"
        ,"Jie Gao",new LatLng(40.912960,-73.122048)));
        courseMap.put("my course", new CourseManagerPOI(2,"ESE 511","Solid-State Electronics","PHYSICS P113 ","MoFr 1:00PM - 2:20PM"
                ,"Jie Gao",new LatLng(40.916073,-73.126060)));
    }

    public ArrayList<Course> getCoursesHistoryList(){
        return this.coursesHistoryList;
    }

    public void storeData() throws JSONException {

//        JSONArray array = jsonObject.getJSONArray("Course History");
        for(int i=0; i<array.length(); i++){

            JSONObject json = null;
            try{
                json = array.getJSONObject(i);
                coursesHistoryList.add(new Course(json.getString("Course"),json.getString("Description"),json.getString("Term"),
                        json.getString("Grade"),json.getString("Units")));

            } catch (JSONException e){
                e.printStackTrace();
            }
        }
    }

//    public void setData(JSONObject jsonObject) throws JSONException {
//        storeData(jsonObject);
//    }


    public void obtainData() throws JSONException {
         String sss = "[{\"Description\":\"Analysis of Algorithms\",\"Course\":\"AMS 542\",\"Units\":\"3.00\",\"Grade\":\" \",\"Term\":\"Fall 2015\"},{\"Description\":\"Theory of Database Systems\",\"Course\":\"CSE 532\",\"Units\":\"3.00\",\"Grade\":\"A\",\"Term\":\"Spring 2015\"},{\"Description\":\"Stochastic Systems\",\"Course\":\"ESE 503\",\"Units\":\"3.00\",\"Grade\":\"B+\",\"Term\":\"Fall 2014\"},{\"Description\":\"Advncd Dgtl Systm Dsgn and Gn\",\"Course\":\"ESE 507\",\"Units\":\"3.00\",\"Grade\":\"B\",\"Term\":\"Fall 2014\"},{\"Description\":\"Solid-State Electronics\",\"Course\":\"ESE 511\",\"Units\":\"3.00\",\"Grade\":\" \",\"Term\":\"Fall 2015\"},{\"Description\":\"Reliability Theory\",\"Course\":\"ESE 540\",\"Units\":\"3.00\",\"Grade\":\"A\",\"Term\":\"Fall 2014\"},{\"Description\":\"Basic concepts of mobile cloud\",\"Course\":\"ESE 543\",\"Units\":\"3.00\",\"Grade\":\"A\",\"Term\":\"Spring 2015\"},{\"Description\":\"Networking Algorithms and Anal\",\"Course\":\"ESE 546\",\"Units\":\"3.00\",\"Grade\":\"A\",\"Term\":\"Fall 2014\"},{\"Description\":\"Compu Model for Compu Engineer\",\"Course\":\"ESE 554\",\"Units\":\"3.00\",\"Grade\":\"A-\",\"Term\":\"Spring 2015\"},{\"Description\":\"Pattern Recognition\",\"Course\":\"ESE 588\",\"Units\":\"3.00\",\"Grade\":\"A-\",\"Term\":\"Spring 2015\"},{\"Description\":\"Research Master's students\",\"Course\":\"ESE 599\",\"Units\":\"1.00\",\"Grade\":\" \",\"Term\":\"Fall 2015\"}]}";
         array = new JSONArray(sss);
         storeData();
    }
}