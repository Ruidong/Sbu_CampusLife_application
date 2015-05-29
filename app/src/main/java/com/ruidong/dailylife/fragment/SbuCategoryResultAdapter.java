package com.ruidong.dailylife.fragment;

/**
 * Created by Ruidong on 5/27/2015.
 */
import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ruidong.sbu_application.R;


public class SbuCategoryResultAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> lableList;
    private ArrayList<String> locationList;
    private ArrayList<String> timeList;
    private LayoutInflater listContainer;


    public final class ListItemView{
        public TextView title;
        public TextView location;
        public TextView time;
    }

    public SbuCategoryResultAdapter(Context context, ArrayList<String> lableList,ArrayList<String> locationList,ArrayList<String> timeList){

        this.context=context;
        listContainer = LayoutInflater.from(context);
        this.lableList=lableList;
        this.locationList=locationList;
        this.timeList=timeList;


    }


    @Override
    public int getCount() {

        return timeList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    public String getTitle(int checkedID){
        return lableList.get(checkedID);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        ListItemView listItemView= null;


        if (convertView == null) {
            listItemView = new ListItemView();
            convertView = listContainer.inflate(R.layout.category_result_adapter, null);
            listItemView.title=(TextView)convertView.findViewById(R.id.lableText);
            listItemView.location=(TextView)convertView.findViewById(R.id.locationText);
            listItemView.time=(TextView)convertView.findViewById(R.id.TimeText);
            listItemView.title.setText((String) lableList.get(position));
            listItemView.location.setText((String) locationList.get(position));
            listItemView.time.setText((String) timeList.get(position));
        }
        return convertView;
    }
}
