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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.ruidong.sbu_application.R;

public class SbuDailyLifeGridViewAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> categoriesList;
    private LayoutInflater gridContainer;
    private boolean[] hasChecked;


    public final class GridItemView{
        public TextView title;
        public CheckBox check;
    }

    public SbuDailyLifeGridViewAdapter(Context context, ArrayList<String> categoriesList){

        this.context=context;
        gridContainer = LayoutInflater.from(context);
        this.categoriesList=categoriesList;
        hasChecked = new boolean[getCount()];

    }


    @Override
    public int getCount() {

        return categoriesList.size();
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


    private void checkedChange(int checkedID) {
        hasChecked[checkedID] = !hasChecked[checkedID];
    }


    public boolean hasChecked(int checkedID) {
        return hasChecked[checkedID];
    }

    public String getTitle(int checkedID){
        return categoriesList.get(checkedID);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final int selectedID= position;
        GridItemView gridItemView= null;


        if (convertView == null) {
            gridItemView = new GridItemView();

            convertView = gridContainer.inflate(R.layout.daily_grid_adapter, null);


            gridItemView.title=(TextView)convertView.findViewById(R.id.categoryItem);
            gridItemView.check=(CheckBox)convertView.findViewById(R.id.checkItem);

            gridItemView.title.setText((String) categoriesList.get(position));

            gridItemView.check
                    .setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {

                        @Override
                        public void onCheckedChanged(CompoundButton buttonView,
                                                     boolean isChecked) {

                            checkedChange(selectedID);
                        }

                    });

        }
        return convertView;
    }
}