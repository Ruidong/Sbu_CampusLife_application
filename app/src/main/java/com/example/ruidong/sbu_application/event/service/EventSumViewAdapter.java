package com.example.ruidong.sbu_application.event.service;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ruidong.sbu_application.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Ruidong on 7/1/2015.
 */
public class EventSumViewAdapter extends ArrayAdapter{

private Context context;
private ArrayList<String> listInfo;
private LayoutInflater listContainer;
private HashMap<Integer,View> lmap = new HashMap<Integer,View>();

public final class ListItemView{
    public TextView text;
}

    public EventSumViewAdapter(Context context,ArrayList<String> listInfo){

        super(context, R.layout.event_sum_adapter);
        this.context=context;
        listContainer = LayoutInflater.from(context);
        this.listInfo = listInfo;


    }


    @Override
    public int getCount() {

        return listInfo.size();
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
            String str = listInfo.get(position);
            listItemView = new ListItemView();
            convertView = listContainer.inflate(R.layout.event_sum_adapter, null);
            listItemView.text = (TextView) convertView.findViewById(R.id.text);

            lmap.put(position, convertView);
            convertView.setTag(listItemView);

            listItemView.text.setText(str);

        }
        else{
            convertView=lmap.get(position);
            listItemView=(ListItemView)convertView.getTag();
        }
        return convertView;
    }
}
