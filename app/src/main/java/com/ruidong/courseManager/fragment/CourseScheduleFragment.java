package com.ruidong.courseManager.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.ruidong.sbu_application.CourseManagerPOI;
import com.example.ruidong.sbu_application.NavigationActivity;
import com.example.ruidong.sbu_application.POI;
import com.example.ruidong.sbu_application.R;
import com.example.ruidong.sbu_application.SbuDailyLifePOI;
import com.google.android.gms.maps.model.Marker;
import com.ruidong.dailylife.fragment.SbuCategoryResultFragment;
import com.ruidong.slidebutton.ViewPagerAdapter;
import com.ruidong.specific.service.CourseManagementService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


/**
 * Created by Ruidong on 6/15/2015.
 */
public class CourseScheduleFragment extends Fragment {
    private ExpandableListView list;
    private ArrayList<String> weekList = new ArrayList<>();
    private HashMap<String,ArrayList<CourseManagerPOI>> courseDetailData = new HashMap<>();
    private NavigationActivity activity;
    private Button history;
    private CourseScheduleFragment courseScheduleFragment;
    public  ArrayList<POI> resultPoiList = new ArrayList<>();
    private Button showMarker;
    private boolean showMarkerFlag;
    public CourseScheduleFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.course_schedule, null);
        list = (ExpandableListView)view.findViewById(R.id.list);
        activity = (NavigationActivity) getActivity();
        CourseManagementService service = activity.getCourseService();
        setTargetList(service.getTargetPOI("my course"));
        ScheduleExpandableListAdapter adapter = new ScheduleExpandableListAdapter(getActivity(),weekList,courseDetailData);
        list.setAdapter(adapter);

        history = (Button)view.findViewById(R.id.course_history);
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CourseHistoryFragment historyFragment = activity.getCourseHistoryFragment();
                FragmentTransaction tran = activity.getSupportFragmentManager().beginTransaction().show(historyFragment);
                tran.addToBackStack(null);
                tran.commit();
                courseScheduleFragment = historyFragment.getCourseScheduleFragment();
                FragmentTransaction tran1 = activity.getSupportFragmentManager().beginTransaction().hide(courseScheduleFragment);
                tran1.commit();
            }
        });

        list.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                if(showMarkerFlag == true){
                CourseHistoryFragment historyFragment = activity.getCourseHistoryFragment();
                courseScheduleFragment = historyFragment.getCourseScheduleFragment();
                FragmentTransaction tran1 = activity.getSupportFragmentManager().beginTransaction().hide(courseScheduleFragment);
                tran1.commit();
                POI currentPOI = courseDetailData.get(weekList.get(groupPosition)).get(childPosition);
                Marker marker = NavigationActivity.mMarkersHashMap2.get(currentPOI);
                activity.setClickedClusterItem(currentPOI);
                if (marker != null) {
                    marker.showInfoWindow();
                }
                activity.setBottomButtonFragment(currentPOI);
                activity.setClusterItemFragToFalse();
                activity.setClusterResultFragmentFlagToFalse();}
                else{
                    Toast.makeText(getActivity(),"Please Click showMarker Button to set marker",Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });

        showMarker = (Button) view.findViewById(R.id.Show_marker);
        showMarker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMarkerFlag =true;
                activity.setDailyFlag(false);
                activity.setCourseFlag(true);
                NavigationActivity.myMarkerList = resultPoiList;
                activity.setBottomButtonFragmentList(resultPoiList);
                activity.getShowListButton().setVisibility(View.INVISIBLE);
                activity.setUpCluster(resultPoiList);
                resultPoiList.clear();
                NavigationActivity.myMarkerList.clear();
                NavigationActivity.editText.setText(" ");
            }
        });
        return view;
    }



    public  void setTargetList(ArrayList<POI> courseScheduleList){
        System.out.println("courseScheduleList Size: "+courseScheduleList.size());
        System.out.println("resultPoiList Size :" + resultPoiList.size());


        resultPoiList.addAll(removeDuplicateWithOrder(courseScheduleList));
        weekList.add("Monday");
        weekList.add("Tuesday");
        weekList.add("Wednesday");
        weekList.add("Thursday");
        weekList.add("Friday");

        for(String str : weekList){
            ArrayList<CourseManagerPOI> courseDetail = new ArrayList<>();
            for(POI poi : courseScheduleList){
                CourseManagerPOI course = (CourseManagerPOI)poi;
                ArrayList<String> courseday = course.getCourseDay();
                 for(String str2 : courseday){
                     if(str.equals(str2)){
                         courseDetail.add(course);
                         courseDetailData.put(str,courseDetail);
                     }
                 }
            }
        }
    }
    private ArrayList<POI> removeDuplicateWithOrder(ArrayList<POI> list) {
        Set<POI> set = new HashSet<POI>(list.size());
        set.addAll(list);
        ArrayList<POI> newList = new ArrayList<POI>(set.size());
        newList.addAll(set);
        return newList;
    }

}
