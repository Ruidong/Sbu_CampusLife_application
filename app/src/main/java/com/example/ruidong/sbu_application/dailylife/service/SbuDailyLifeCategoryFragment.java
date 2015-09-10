package com.example.ruidong.sbu_application.dailylife.service;

/**
 * Created by Ruidong on 5/27/2015.
 */

// This is the Category Grid View Fragment of SBU daily life service.
// It will obtain relative data from server using HTTP protocol, the operation is selecting multiple
// interested categories and click Done button. The result is show relative marker and a listView on the screen,
// same as input some keywords from Search EditText.
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import com.example.ruidong.sbu_application.framework.NavigationActivity;
import com.example.ruidong.sbu_application.framework.POI;
import com.example.ruidong.sbu_application.R;
//import com.example.ruidong.sbu_application.ShowListButton;
import com.example.ruidong.sbu_application.framework.CategoryFragment;
import com.example.ruidong.sbu_application.framework.common.tool.FragmentIdPair;

public class SbuDailyLifeCategoryFragment extends CategoryFragment {

    private GridView grid;
    private Button button;
    private SbuDailyLifeGridViewAdapter adapter;
    public static final ArrayList<String> categories = new ArrayList<String>();
    private  ArrayList<String> restoreList;
    public SbuCategoryResultFragment resultFragment;
//    public ShowListButton showListFragment;
    private NavigationActivity activity;
    public  ArrayList<POI> resultPoiList = new ArrayList<>() ;
    private SbuDailyLifeService dailyService;
    private FragmentActivity mActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.daily_category_view, container, false);
        activity = (NavigationActivity)getActivity();
        button=(Button)view.findViewById(R.id.DoneButton);
        grid=(GridView)view.findViewById(R.id.gridView);
        button.setOnClickListener(new ClickEvent());
        dailyService=NavigationActivity.dailyService;
    //  Six recommended categories
        categories.add("food");
        categories.add("coffee");
        categories.add("gym");
        categories.add("library");
        categories.add("store");
        categories.add("mesuem");
        restoreList=removeDuplicateWithOrder(categories);
        adapter = new SbuDailyLifeGridViewAdapter(getActivity(), restoreList);
        Collections.sort(restoreList, new Comparator<String>() {
            public int compare(String str1, String str2) {
                return str1.compareToIgnoreCase(str2);
            }
        });

        grid.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return view;

    }
    private ArrayList<String> removeDuplicateWithOrder(ArrayList<String> list) {
        Set<String> set = new HashSet<String>(list.size());
        set.addAll(list);
        ArrayList<String> newList = new ArrayList<String>(set.size());
        newList.addAll(set);
        return newList;
    }

    // Using this method ot solve the problem of getActivity = null, when calling getActivity() method
    // after using HTTP protocol to connect server.
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (FragmentActivity) activity;
    }

    class ClickEvent implements OnClickListener{
        // The ArrayList is used for multiple selection, but there is a bug about it right now.
        //  Only single selection runs well.
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
        FragmentTransaction tran = activity.getSupportFragmentManager().beginTransaction().remove(activity.backButtonStack.peek().getFragment());
        tran.commit();
        if(checkedList!=null) {
            for (int i = 0; i < checkedList.size(); i++) {
                String keyword = checkedList.get(i);
                if (keyword != null) {
                    NavigationActivity.map.clear();
                    if (activity.getClusterManager() != null) {
                        activity.getClusterManager().clearItems();
                    }
                    dailyService.dailyFlag = true;
                    resultPoiList.addAll(dailyService.getTargetPOI(keyword));

                }
            }
            NavigationActivity.myMarkerList = resultPoiList;
            activity.setBottomButtonFragmentList(resultPoiList,0);

            resultFragment = new SbuCategoryResultFragment();
            NavigationActivity.dailyResultFragment = resultFragment;
            ((SbuCategoryResultFragment) resultFragment).setTargetList(resultPoiList);

            FragmentTransaction resultTran = mActivity.getSupportFragmentManager().beginTransaction()
                    .add(R.id.Category_result_Container, resultFragment);
            resultTran.commit();
            FragmentIdPair pair = new FragmentIdPair(resultFragment,R.id.Category_result_Container,2);
            activity.backButtonStack.push(pair);

            activity.getShowListButton().setVisibility(View.INVISIBLE);

            activity.setUpCluster(resultPoiList);
            resultPoiList.clear();
            NavigationActivity.myMarkerList.clear();
            NavigationActivity.showResultListFlag = true;

          }
            else
            resultPoiList = null;
        }
    }