package com.ruidong.courseManager.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
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
    private Fragment historyFragment;
    private CourseScheduleFragment courseScheduleFragment;
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

        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                historyFragment=activity.getCourseHistoryFragment();
                FragmentTransaction tran = activity.getSupportFragmentManager().beginTransaction().hide(historyFragment);
                tran.commit();
                courseScheduleFragment =  new CourseScheduleFragment();
                FragmentTransaction tran1 = activity.getSupportFragmentManager().beginTransaction().add(R.id.Category_result_Container,courseScheduleFragment);
                tran1.addToBackStack(null);
                tran1.commit();
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
