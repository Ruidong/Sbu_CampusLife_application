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
public class UserHistoryAdapter extends ArrayAdapter {

    private Context context;
    private ArrayList<InputHistory> historyList;
    private LayoutInflater listContainer;
    private HashMap<Integer,View> lmap = new HashMap<Integer,View>();

    public final class ListItemView{
        public TextView title;
        public TextView count;
    }



    public UserHistoryAdapter(Context context, ArrayList<InputHistory> historyList) {
        super(context,R.layout.user_histroy_list_adapter);
        this.context=context;
        listContainer = LayoutInflater.from(context);
        this.historyList=historyList;
    }

    @Override
    public int getCount() {

        return 3;
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
            convertView = listContainer.inflate(R.layout.user_histroy_list_adapter, null);
            listItemView.title = (TextView) convertView.findViewById(R.id.text1);
            listItemView.count = (TextView) convertView.findViewById(R.id.count);
            lmap.put(position, convertView);
            convertView.setTag(listItemView);

            if(position<historyList.size()){
            listItemView.title.setText((String) historyList.get(position).getTitle());
            listItemView.count.setText((String)"count :" + historyList.get(position).getCount().toString());}
            else {
                listItemView.title.setText(" ");
                listItemView.count.setText(" ");
            }

             }
        else{
            convertView=lmap.get(position);
            listItemView=(ListItemView)convertView.getTag();
             }
        return convertView;
    }
}
