package com.example.ruidong.sbu_application.event.service;

/**
 * Created by Ruidong on 5/27/2015.
 *
 */

// This is the Category Result List View of SBU dailylife service.
// There are two main parts of this ListView fragment:
// First is OnClick Method of List Items, it will show corresponding marker information window
// and related BottomButton Fragment, and hide the List View fragment itself.
// Second is SetTargetList method, which will set the content of this ListView, based on the
// POI collection got from server database.

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.ruidong.sbu_application.framework.BlankFragment;
import com.example.ruidong.sbu_application.framework.NavigationActivity;
import com.example.ruidong.sbu_application.framework.POI;
import com.example.ruidong.sbu_application.R;
import com.example.ruidong.sbu_application.framework.common.tool.FragmentIdPair;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class EventResultListFragment extends Fragment{
    private NavigationActivity activity ;
    private ListView list;
    private ArrayList<EventPOI> PoiList = new ArrayList<EventPOI>();
    private ArrayList<EventPOI> restoreList;
    private BlankFragment blankFragment = new BlankFragment();
    public EventResultListFragment(){

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity = (NavigationActivity)getActivity();
        View view= inflater.inflate(R.layout.category_result, container, false);
        list = (ListView) view.findViewById(R.id.listview);
        restoreList=removeDuplicateWithOrder(PoiList);
        System.out.print("restoreList Size = " + restoreList.size());
        EventResultListAdapter adapter = new EventResultListAdapter(getActivity(),
                restoreList);
        Collections.sort(restoreList, new Comparator<EventPOI>() {
            public int compare(EventPOI Poi1, EventPOI Poi2) {
                return Poi1.getPoiLabel().compareToIgnoreCase(Poi2.getPoiLabel());
            }
        });
        this.list.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        this.list.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {



                POI currentPOI = restoreList.get(position);
                activity.responseOfClusterResultListItemClick(currentPOI);
            }
        });

        return view;
    }

    public void setPoiList(ArrayList<EventPOI> poiList){
        this.PoiList = poiList;
    }

    public  void setTargetList(ArrayList<POI> resultPoiList){

        for(POI PoiElement : resultPoiList)
        {
            EventPOI eventPOI = (EventPOI)PoiElement;
            PoiList.add(eventPOI);
        }
    }
    public ListView getListView(){
        return this.list;
    }
    public void clearList(){
        PoiList.clear();
    }

    private ArrayList<EventPOI> removeDuplicateWithOrder(ArrayList<EventPOI> list) {
        Set<EventPOI> set = new HashSet<>(list.size());
        set.addAll(list);
        ArrayList<EventPOI> newList = new ArrayList<>(set.size());
        newList.addAll(set);
        return newList;
    }
}