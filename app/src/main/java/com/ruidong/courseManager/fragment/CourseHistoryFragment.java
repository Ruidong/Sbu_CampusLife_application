package com.ruidong.courseManager.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.ruidong.sbu_application.NavigationActivity;
import com.example.ruidong.sbu_application.R;
import com.ruidong.specific.service.CourseManagementService;
import java.util.ArrayList;
import java.util.HashMap;


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
        setTargetList(service.getCoursesHistoryList());

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
                System.out.println("2222222222222"+historyFragment==null);

                FragmentTransaction tran = activity.getSupportFragmentManager().beginTransaction().remove(historyFragment);
                tran.commit();
                courseScheduleFragment =  new CourseScheduleFragment();
                FragmentTransaction tran1 = activity.getSupportFragmentManager().beginTransaction().add(R.id.Category_result_Container,courseScheduleFragment);
                tran1.addToBackStack(null);
                tran1.commit();
                System.out.println("2222222222222"+courseScheduleFragment==null);
            }
        });

        return view;
    }
    public CourseScheduleFragment getCourseScheduleFragment(){
        return this.courseScheduleFragment;
    }

    public  void setTargetList(ArrayList<Course> courseHistoryList){

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

}
