package com.example.ruidong.sbu_application.courseManager.service;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.ruidong.sbu_application.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Ruidong on 6/15/2015.
 */
public class ScheduleExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private ArrayList<String> weekList;
    private HashMap<String,ArrayList<CourseManagerPOI>> courseDetailData;

    public ScheduleExpandableListAdapter(Context context, ArrayList<String> weekList, HashMap<String, ArrayList<CourseManagerPOI>> courseDetailData){
        this.context=context;
        this.weekList = weekList;
        this.courseDetailData=courseDetailData;
    }
    @Override
    public int getGroupCount() {
        return this.weekList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if(courseDetailData.get(this.weekList.get(groupPosition))!=null)
        return courseDetailData.get(this.weekList.get(groupPosition)).size();
        else
            return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.weekList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return courseDetailData.get(this.weekList.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.course_shedule_parent_adapter,null);
        }
        int count = getChildrenCount(groupPosition);
        TextView text = (TextView)convertView.findViewById(R.id.parent_text);
        if(count>0)
        text.setText(weekList.get(groupPosition)+"("+"Course number : "+count+")");
        else
        text.setText(weekList.get(groupPosition)+"(no course)");
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final CourseManagerPOI course = (CourseManagerPOI) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.course_shedule_child_adapter, null);
        }
        TextView title = (TextView) convertView.findViewById(R.id.lableText);
        TextView location = (TextView) convertView.findViewById(R.id.locationText);
        TextView time = (TextView) convertView.findViewById(R.id.TimeText);
        if(course !=null){
        title.setText((String) course.getCourseNumber()+"("+course.getCourseName()+")");
        location.setText((String) course.getCourseLocation());
        time.setText((String) course.getCourseTime());}
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
