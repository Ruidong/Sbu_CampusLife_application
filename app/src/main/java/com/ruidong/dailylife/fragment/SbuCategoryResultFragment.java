package com.ruidong.dailylife.fragment;

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

import java.util.ArrayList;
import java.util.Collections;
        import java.util.Comparator;
        import java.util.HashSet;
        import java.util.Set;


        import com.example.ruidong.sbu_application.NavigationActivity;
        import com.example.ruidong.sbu_application.POI;
        import com.example.ruidong.sbu_application.R;
        import com.example.ruidong.sbu_application.SbuDailyLifePOI;
import com.google.android.gms.maps.model.Marker;



        import android.os.Bundle;
        import android.support.v4.app.Fragment;
        import android.support.v4.app.FragmentTransaction;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.AdapterView;
        import android.widget.AdapterView.OnItemClickListener;

        import android.widget.ListView;

public class SbuCategoryResultFragment extends Fragment{
    private NavigationActivity activity ;
    private ListView list;
    private SbuCategoryResultFragment selfFrag;
    private ArrayList<SbuDailyLifePOI> PoiList = new ArrayList<SbuDailyLifePOI>();
    private ArrayList<SbuDailyLifePOI> restoreList;
    public SbuCategoryResultFragment(){

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity = (NavigationActivity)getActivity();
        View view= inflater.inflate(R.layout.category_result, container, false);
        list = (ListView) view.findViewById(R.id.listview);
        restoreList=removeDuplicateWithOrder(PoiList);
        System.out.print("restoreList Size = " + restoreList.size());
        final SbuCategoryResultAdapter adapter = new SbuCategoryResultAdapter(getActivity(),
                restoreList);
        Collections.sort(restoreList, new Comparator<SbuDailyLifePOI>() {
            public int compare(SbuDailyLifePOI Poi1, SbuDailyLifePOI Poi2) {
                return Poi1.getPoiLabel().compareToIgnoreCase(Poi2.getPoiLabel());
            }
        });
        this.list.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        this.list.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                selfFrag = (SbuCategoryResultFragment) NavigationActivity.dailyResultFragment;
                FragmentTransaction tran = getFragmentManager().beginTransaction().hide(selfFrag);
                tran.commit();

                if(activity.getDailyClusterResultFragment()!=null) {
                    SbuCategoryResultFragment clusterFrag = activity.getDailyClusterResultFragment();
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

    public void setPoiList(ArrayList<SbuDailyLifePOI> poiList){
        this.PoiList = poiList;
    }
    public  void setTargetList(ArrayList<POI> resultPoiList){

        for(POI PoiElement : resultPoiList)
        {
            SbuDailyLifePOI SDLPoi = (SbuDailyLifePOI)PoiElement;
            PoiList.add(SDLPoi);
        }
    }

    public void clearList(){
        PoiList.clear();
    }

    private ArrayList<SbuDailyLifePOI> removeDuplicateWithOrder(ArrayList<SbuDailyLifePOI> list) {
        Set<SbuDailyLifePOI> set = new HashSet<SbuDailyLifePOI>(list.size());
        set.addAll(list);
        ArrayList<SbuDailyLifePOI> newList = new ArrayList<SbuDailyLifePOI>(set.size());
        newList.addAll(set);
        return newList;
    }
}