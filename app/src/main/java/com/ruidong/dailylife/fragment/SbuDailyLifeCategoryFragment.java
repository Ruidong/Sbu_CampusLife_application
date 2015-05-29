package com.ruidong.dailylife.fragment;

/**
 * Created by Ruidong on 5/27/2015.
 */
import java.util.ArrayList;
import java.util.Collection;








import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;


import com.example.ruidong.sbu_application.NavigationActivity;
import com.example.ruidong.sbu_application.POI;
import com.example.ruidong.sbu_application.R;
import com.example.ruidong.sbu_application.ShowListButton;
import com.ruidong.common.parent.fragment.CategoryFragment;
import com.ruidong.common.tool.ApiConnector;

import com.ruidong.specific.service.SbuDailyLifeService;

public class SbuDailyLifeCategoryFragment extends CategoryFragment {

    private GridView grid;
    private Button button;
    private SbuDailyLifeGridViewAdapter adapter;
    public static final ArrayList<String> categories = new ArrayList<String>();
    public SbuCategoryResultFragment resultFragment;
    public ShowListButton showListFragment;
    private NavigationActivity activity = new NavigationActivity();
    public  Collection<POI> resultPoiCollection = null ;
    private SbuDailyLifeService dailyService;
    private FragmentActivity mActivity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.daily_category_view, container, false);


        button=(Button)view.findViewById(R.id.DoneButton);
        grid=(GridView)view.findViewById(R.id.gridView);
        button.setOnClickListener(new ClickEvent());
        dailyService=NavigationActivity.dailyService;


        categories.add("food");
        categories.add("coffee");
        categories.add("gym");
        categories.add("library");
        categories.add("store");
        categories.add("mesuem");

        adapter = new SbuDailyLifeGridViewAdapter(getActivity(), categories);
        grid.setAdapter(adapter);



        return view;

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (FragmentActivity) activity;
    }



    class ClickEvent implements OnClickListener{

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            ArrayList<String> checkedList=new ArrayList<String>();
            for(int i = 0; i < categories.size(); i++) {
                if(adapter.hasChecked(i)){
                    checkedList.add(adapter.getTitle(i));
                }
            }
            doneButtonMethod(checkedList);

        }

    }


    @Override
    public void doneButtonMethod(ArrayList<String> checkedList) {
        Fragment selfFragment= NavigationActivity.MenuFragment;
        FragmentTransaction tran=getFragmentManager().beginTransaction().remove(selfFragment);
        tran.commit();
//
//         for(String str:checkedList){
//        	 resultPoiCollection = service.getTargetPOI(str);
//         }

        String keyword =  checkedList.get(0);

        if( keyword != null ) {

            new GetPOIItem(keyword).execute(new ApiConnector());

        }
        else
            resultPoiCollection = null;


    }


    public void ObtainData(JSONArray jsonArray,String keyword){



        for(int i=0; i<jsonArray.length(); i++){

            JSONObject json = null;
            try{
                json = jsonArray.getJSONObject(i);


                dailyService.storeData(json,keyword);

                System.out.println(".............."+json.getString("POI_Name"));

            } catch (JSONException e){
                e.printStackTrace();
            }
        }


    }



    private class GetPOIItem extends AsyncTask<ApiConnector, Long, JSONArray>{

        private String keyword;

        public GetPOIItem(String str) {
            this.keyword = str;
        }

        @Override
        protected JSONArray doInBackground(ApiConnector... params) {

            //  it is executed on Background thread

            return params[0].GetPOI(keyword);
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray){


            ObtainData(jsonArray,keyword);
            dailyService.dailyFlag=true;
            resultPoiCollection=dailyService.getTargetPOI(keyword);
            NavigationActivity.myMarkerCollection=resultPoiCollection;
            activity.setBottomButtonFragmentList(resultPoiCollection);

            resultFragment=new SbuCategoryResultFragment();
            NavigationActivity.resultFragment=resultFragment;
            ((SbuCategoryResultFragment) resultFragment).setTargetList(resultPoiCollection);

                    FragmentTransaction resultTran=mActivity.getSupportFragmentManager().beginTransaction()
           				 .add(R.id.Category_result_Container,resultFragment);
                    resultTran.commit();

			           showListFragment = new ShowListButton();
			           NavigationActivity.showListFragment=showListFragment;

				  	 FragmentTransaction showTran= mActivity.getSupportFragmentManager().beginTransaction().add(R.id.showListButton, showListFragment);
				  	 showTran.commit();
				  	 FragmentTransaction hideTran= mActivity.getSupportFragmentManager().beginTransaction().hide(showListFragment);
                     hideTran.commit();


            activity.addMarker(resultPoiCollection);


}

        }

        }
