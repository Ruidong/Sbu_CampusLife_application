package com.example.ruidong.sbu_application.dailylife.service;

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


public class SbuDailyLifeResultAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<SbuDailyLifePOI> POiList;
    private LayoutInflater listContainer;


    public final class ListItemView{
        public TextView title;
        public TextView infomation;
    }

    public SbuDailyLifeResultAdapter(Context context, ArrayList<SbuDailyLifePOI> POiList){

        this.context=context;
        listContainer = LayoutInflater.from(context);
        this.POiList=POiList;


    }

    @Override
    public int getCount() {

        return POiList.size();
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


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        ListItemView listItemView= null;


        if (convertView == null) {
            listItemView = new ListItemView();
            convertView = listContainer.inflate(R.layout.category_result_adapter, null);
            listItemView.title=(TextView)convertView.findViewById(R.id.lableText);
            listItemView.infomation=(TextView)convertView.findViewById(R.id.locationText);
            listItemView.title.setText((String) POiList.get(position).getPoiLabel());
            listItemView.infomation.setText((String) POiList.get(position).getPoiLocation()+" , "+POiList.get(position).getPoiTime());
        }
        return convertView;
    }
}
