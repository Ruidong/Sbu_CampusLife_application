package com.ruidong.courseManager.fragment;

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

import com.example.ruidong.sbu_application.CourseManagerPOI;
import com.example.ruidong.sbu_application.NavigationActivity;
import com.example.ruidong.sbu_application.POI;
import com.example.ruidong.sbu_application.R;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class CourseResultListFragment extends Fragment{
    private NavigationActivity activity ;
    private ListView list;
    private CourseResultListFragment selfFrag;
    private ArrayList<CourseManagerPOI> PoiList = new ArrayList<CourseManagerPOI>();
    private ArrayList<CourseManagerPOI> restoreList;
    public CourseResultListFragment(){

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity = (NavigationActivity)getActivity();
        View view= inflater.inflate(R.layout.category_result, container, false);
        list = (ListView) view.findViewById(R.id.listview);
        restoreList=removeDuplicateWithOrder(PoiList);
        System.out.print("restoreList Size = " + restoreList.size());
        final CourseResultListAdapter adapter = new CourseResultListAdapter(getActivity(),
                restoreList);
        Collections.sort(restoreList, new Comparator<CourseManagerPOI>() {
            public int compare(CourseManagerPOI Poi1, CourseManagerPOI Poi2) {
                return Poi1.getPoiLabel().compareToIgnoreCase(Poi2.getPoiLabel());
            }
        });
        this.list.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        this.list.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                selfFrag = (CourseResultListFragment) NavigationActivity.courseResultFragment;
                FragmentTransaction tran = getFragmentManager().beginTransaction().hide(selfFrag);
                tran.commit();

                if(activity.getDailyClusterResultFragment()!=null) {
                    CourseResultListFragment clusterFrag = activity.getCourseClusterResultFragment();
                    FragmentTransaction tran2 = getFragmentManager().beginTransaction().remove(clusterFrag);
                    tran2.commit();
                }
                POI currentPOI = restoreList.get(position);
                Marker marker = NavigationActivity.mMarkersHashMap2.get(currentPOI);
                activity.setClickedClusterItem(currentPOI);
                if (marker != null) {

                    marker.showInfoWindow();
                }
                activity.setBottomButtonFragment(currentPOI);
                activity.getShowListButton().setVisibility(View.INVISIBLE);
                activity.setClusterItemFragToFalse();
                activity.setClusterResultFragmentFlagToFalse();;

            }
        });

        return view;
    }

    public void setPoiList(ArrayList<CourseManagerPOI> poiList){
        this.PoiList = poiList;
    }
    public  void setTargetList(ArrayList<POI> resultPoiList){

        for(POI PoiElement : resultPoiList)
        {
            CourseManagerPOI CMPoi = (CourseManagerPOI)PoiElement;
            PoiList.add(CMPoi);
        }
    }

    public void clearList(){
        PoiList.clear();
    }

    private ArrayList<CourseManagerPOI> removeDuplicateWithOrder(ArrayList<CourseManagerPOI> list) {
        Set<CourseManagerPOI> set = new HashSet<>(list.size());
        set.addAll(list);
        ArrayList<CourseManagerPOI> newList = new ArrayList<>(set.size());
        newList.addAll(set);
        return newList;
    }
}