package com.example.ruidong.sbu_application;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Ruidong on 6/9/2015.
 */
public class AutoCompleteAdapter extends ArrayAdapter {

    private Context context;
    private ArrayList<String> hintList;
    private LayoutInflater listContainer;
    private HashMap<Integer,View> lmap = new HashMap<Integer,View>();

    public final class ListItemView{
        public TextView title;
    }



    public AutoCompleteAdapter(Context context, ArrayList<String> hintList) {
        super(context,R.layout.hint_list_adapter);
        this.context=context;
        listContainer = LayoutInflater.from(context);
        this.hintList=hintList;
    }

    @Override
    public int getCount() {

        return hintList.size();
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

            listItemView = new ListItemView();
            convertView = listContainer.inflate(R.layout.hint_list_adapter, null);
            listItemView.title = (TextView) convertView.findViewById(R.id.text);
            lmap.put(position, convertView);
            convertView.setTag(listItemView);
            listItemView.title.setText((String) hintList.get(position));}
        else{
            convertView=lmap.get(position);
            listItemView=(ListItemView)convertView.getTag();
             }
        return convertView;
    }
}
