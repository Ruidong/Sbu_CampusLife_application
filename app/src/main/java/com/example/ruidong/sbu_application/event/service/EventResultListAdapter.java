package com.example.ruidong.sbu_application.event.service;

/**
 * Created by Ruidong on 5/27/2015.
 *
 */

// This is the adapter for Category Result List View of SBU daily life function.
// The List View will show three features of a POI, which are lable, location and available time.
// Each features will be shown in a TextView.


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ruidong.sbu_application.R;

import java.util.ArrayList;
import java.util.HashMap;


public class EventResultListAdapter extends ArrayAdapter {

    private Context context;
    private ArrayList<EventPOI> PoiList;
    private LayoutInflater listContainer;
    private  HashMap<Integer,View> lmap = new HashMap<Integer,View>();

    public final class ListItemView{
        public TextView title;
        public TextView location;
    }

    public EventResultListAdapter(Context context, ArrayList<EventPOI> PoiList){

        super(context,R.layout.category_result_adapter);
        this.context=context;
        listContainer = LayoutInflater.from(context);
        this.PoiList= PoiList;


    }


    @Override
    public int getCount() {

        return PoiList.size();
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

            if(lmap.get(position) == null) {
                EventPOI poi = (EventPOI)PoiList.get(position);
                listItemView = new ListItemView();
                convertView = listContainer.inflate(R.layout.category_result_adapter, null);
                listItemView.title = (TextView) convertView.findViewById(R.id.lableText);
                listItemView.location = (TextView) convertView.findViewById(R.id.locationText);

                lmap.put(position,convertView);
                convertView.setTag(listItemView);

                listItemView.title.setText((String) poi.getEventName());
                listItemView.location.setText((String) poi.getEventLocation());
            }
            else{
                convertView=lmap.get(position);
                listItemView=(ListItemView)convertView.getTag();
            }
        return convertView;
    }
}
