package com.example.ruidong.sbu_application.courseManager.service;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.ruidong.sbu_application.framework.NavigationActivity;
import com.example.ruidong.sbu_application.R;
import com.example.ruidong.sbu_application.framework.POI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


/**
 * Created by Ruidong on 6/15/2015.
 */
public class CourseHistoryFragment extends Fragment {
    private ExpandableListView list;
    private ArrayList<String> courseList = new ArrayList<>();
    private HashMap<String,ArrayList<String>> courseDetailData = new HashMap<>();
    private NavigationActivity activity;
    private Button schedule;
    private Button checkGPA;
    private Fragment historyFragment;
    private CourseScheduleFragment courseScheduleFragment;
    public  ArrayList<POI> resultPoiList = new ArrayList<>();
    private Fragment resultFragment;
    private TextView GPAText;
    public CourseHistoryFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.course_history,null);
        list = (ExpandableListView)view.findViewById(R.id.CourseHistory_list);
        schedule= (Button) view.findViewById(R.id.course_shedule);
        activity = (NavigationActivity) getActivity();
        CourseManagementService service = activity.getCourseService();
        setTargetList(service.getCoursesHistoryList(),service.getTargetPOI("my course"));

        HistoryExpandableListAdapter adapter = new HistoryExpandableListAdapter(getActivity(),courseList,courseDetailData);
        list.setAdapter(adapter);
        GPAText = (TextView)view.findViewById(R.id.GPA_text);

        checkGPA=(Button)view.findViewById(R.id.Check_GPA);
//        checkGPA.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                System.out.println("111111111111111111111"+activity.getLoginFragment().GetGPA());
//                GPAText.setText(activity.getLoginFragment().GetGPA());
//            }
//        });


        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationActivity.map.clear();
                if(NavigationActivity.myMarkerList!=null){
                NavigationActivity.myMarkerList.clear();}
                NavigationActivity.showResultListFlag=false;

                if(NavigationActivity.dailyResultFragment !=null){
                    FragmentTransaction tran = activity.getSupportFragmentManager().beginTransaction().remove(NavigationActivity.dailyResultFragment);
                    tran.commit();
                }
                if(NavigationActivity.courseResultFragment !=null){
                    FragmentTransaction tran = activity.getSupportFragmentManager().beginTransaction().remove(NavigationActivity.courseResultFragment);
                    tran.commit();
                }
                if(activity.getCourseHistoryFragment()!=null){
                historyFragment=activity.getCourseHistoryFragment();}
                else{
                    historyFragment=activity.getLoginFragment().getCourseHistoryFragment();
                }

                FragmentTransaction tran = activity.getSupportFragmentManager().beginTransaction().remove(historyFragment);
                tran.commit();
                courseScheduleFragment =  new CourseScheduleFragment();
                activity.courseScheduleFragment = courseScheduleFragment;
                FragmentTransaction tran1 = activity.getSupportFragmentManager().beginTransaction().add(R.id.CourseSchedule_container,courseScheduleFragment);
                tran1.addToBackStack(null);
                tran1.commit();

                activity.setServiceNumber(1);
                NavigationActivity.myMarkerList = resultPoiList;
                activity.setBottomButtonFragmentList(resultPoiList, 1);
                activity.getShowListButton().setVisibility(View.INVISIBLE);
                activity.setUpCluster(resultPoiList);
                resultPoiList.clear();
                NavigationActivity.myMarkerList.clear();

            }
        });

        return view;
    }
    public CourseScheduleFragment getCourseScheduleFragment(){
        return this.courseScheduleFragment;
    }

    public  void setTargetList(ArrayList<Course> courseHistoryList,ArrayList<POI> courseScheduleList){

        resultPoiList.addAll(removeDuplicateWithOrder(courseScheduleList));

        for(Course courseElement : courseHistoryList)
        {
            courseList.add(courseElement.getCourseTitle());
            ArrayList<String> courseDetail = new ArrayList<>();
            courseDetail.add("CourseName : "+courseElement.getCourseName());
            courseDetail.add("CourseTerm : "+courseElement.getCourseTerm());
            courseDetail.add("CourseGrade : "+courseElement.getCourseGrade());
            courseDetail.add("CourseUnits : "+courseElement.getCourseUnits());
            courseDetailData.put(courseElement.getCourseTitle(),courseDetail);
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
