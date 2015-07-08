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
public class HistoryExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private ArrayList<String> courseList;
    private HashMap<String,ArrayList<String>> courseDetailData;

    public HistoryExpandableListAdapter(Context context, ArrayList<String> courseList, HashMap<String, ArrayList<String>> courseDetailData){
        this.context=context;
        this.courseList=courseList;
        this.courseDetailData=courseDetailData;
    }
    @Override
    public int getGroupCount() {
        return this.courseList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return courseDetailData.get(this.courseList.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.courseList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return courseDetailData.get(this.courseList.get(groupPosition)).get(childPosition);
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
            convertView = inflater.inflate(R.layout.course_history_parent_adapter,null);
        }
        TextView text = (TextView)convertView.findViewById(R.id.parent_text);
        text.setText(courseList.get(groupPosition));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.course_history_child_adapter, null);
        }
        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.child_text);
        txtListChild.setText(childText);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
