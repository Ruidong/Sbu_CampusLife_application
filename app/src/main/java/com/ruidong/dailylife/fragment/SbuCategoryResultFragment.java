package com.ruidong.dailylife.fragment;

/**
 * Created by Ruidong on 5/27/2015.
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;


import com.example.ruidong.sbu_application.NavigationActivity;
import com.example.ruidong.sbu_application.POI;
import com.example.ruidong.sbu_application.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
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
import android.widget.TextView;

public class SbuCategoryResultFragment extends Fragment{
    private NavigationActivity activity = new NavigationActivity();
    private ListView list;
    private SbuCategoryResultFragment selfFrag;
    private ArrayList<String> lableList = new ArrayList<String>();
    private ArrayList<String> locationList = new ArrayList<String>();
    private ArrayList<String> timeList = new ArrayList<String>();

    private HashMap<Integer, POI> listHashMap1 = new HashMap<Integer, POI>();

    private HashMap<String,Integer> listHashMap2= new HashMap<String, Integer>();

    public SbuCategoryResultFragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.category_result, container, false);
        list = (ListView) view.findViewById(R.id.listview);


        final SbuCategoryResultAdapter adapter = new SbuCategoryResultAdapter(getActivity(),
                lableList, locationList,timeList);
        this.list.setAdapter(adapter);

        this.list.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                selfFrag = (SbuCategoryResultFragment) NavigationActivity.resultFragment;
                FragmentTransaction tran = getFragmentManager().beginTransaction().hide(selfFrag);
                tran.commit();

                TextView textView1=(TextView) view.findViewById(R.id.lableText);
                String str = textView1.getText().toString();

                Integer currentPoiId= listHashMap2.get(str);

                POI currentPOI= listHashMap1.get(currentPoiId);

                LatLng  latlng =new LatLng (currentPOI.getmLatitude(),currentPOI.getmLongitude());
                NavigationActivity.map.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 14));
                Marker marker=NavigationActivity.mMarkersHashMap2.get(currentPOI);

                marker.showInfoWindow();

                activity.setBottomButtonFragment(currentPOI);

                FragmentTransaction hideTran = getFragmentManager().beginTransaction().hide(NavigationActivity.showListFragment);
                hideTran.commit();
            }
        });

        return view;
    }
    public  void setTargetList(Collection<POI> resultPoiCollection){

        for(POI PoiElement: resultPoiCollection)
        {
            Integer poiId= Integer.valueOf(PoiElement.getPoiId());
            listHashMap1.put(poiId,PoiElement);
            String str1 = PoiElement.getPoiLabel();
            lableList.add(str1);
            System.out.println("+++++++++++++++"+str1);

            String str2 = PoiElement.getPoiLocation();
            locationList.add(str2);
            System.out.println("+++++++++++++++  location"+str2);

            String str3 = PoiElement.getPoiFundDetail();
            timeList.add(str3);
            listHashMap2.put(str1, poiId);
            System.out.println("+++++++++++++++ time"+str3);
        }

    }

}