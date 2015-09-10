package com.example.ruidong.sbu_application.dailylife.service;

/**
 * Created by Ruidong on 5/27/2015.
 */

// This is the detailed information View for a specific POI!
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;





import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.ruidong.sbu_application.framework.BottomButton;
import com.example.ruidong.sbu_application.framework.NavigationActivity;
import com.example.ruidong.sbu_application.framework.POI;
import com.example.ruidong.sbu_application.R;
import com.example.ruidong.sbu_application.framework.SumViewFragment;
import com.example.ruidong.sbu_application.framework.common.tool.FragmentIdPair;


public class SbuDailySumView extends SumViewFragment {
    private TextView text1;
    private ImageView listDetialedImage;
    private ListView listView;
    private LinearLayout listViewMainButton;
    private SbuDailyLifePOI poi;
    private LinearLayout detailedListView;
    private String msg1,msg2;
    private Button hideListViewButton;
    private Fragment selfFragment;
    private BottomButton bottomButton;
    private ScrollView scrollView;
    private NavigationActivity activity;
    private int layoutID;
    public SbuDailySumView(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.daily_sum_view, container, false);
        activity=(NavigationActivity)getActivity();
        detailedListView=(LinearLayout)view.findViewById(R.id.myDetailedListView);
        listViewMainButton=(LinearLayout)view.findViewById(R.id.ListViewMainButton);
        text1=(TextView)view.findViewById(R.id.textView3);
        listDetialedImage=(ImageView)view.findViewById(R.id.ListViewImage);
        listView=(ListView)view.findViewById(R.id.listview);
        scrollView=(ScrollView)view.findViewById(R.id.scrollView);
        hideListViewButton=(Button)view.findViewById(R.id.hideListView);
        setListText();


        hideListViewButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                    selfFragment=activity.getSumViewFragment();
                    FragmentTransaction tran=getFragmentManager().beginTransaction().remove(selfFragment);
                    activity.backButtonStack.pop();
                    tran.commit();
                    if(!activity.backButtonStack.isEmpty()){
                        FragmentIdPair nextPair = activity.backButtonStack.peek();
                        FragmentTransaction tran2 = activity.getSupportFragmentManager().beginTransaction().add(nextPair.getFragmentLayoutID(),nextPair.getFragment());
                        tran2.commit();

                    }
            }
        });

        ArrayList<String> listInfo=getTargetList();
        final StableArrayAdapter adapter = new StableArrayAdapter(getActivity(),
                android.R.layout.simple_list_item_1, listInfo);
        listView.setAdapter(adapter);

        setListViewHeight(listView);
        scrollView.scrollTo(0, scrollView.getBottom());

        return view;
    }

    public int getLayoutID(){
        return this.layoutID;
    }
    public void setLayoutID(int ID){
        this.layoutID = ID;
    }

    public void setListViewHeight(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    @Override
    public void setPOI(POI currentPOI){
        this.poi=(SbuDailyLifePOI)currentPOI;
    }

    public void setMsg(String str1,String str2){
        this.msg1=str1;
        this.msg2=str2;
    }

    private void setListText(){
        text1.setText(msg1);
    }

    private ArrayList<String> getTargetList(){

        String[] values = new String[] {"Location:  "+poi.getPoiLocation(),
                "Available Time:  "+poi.getPoiTime(),"Contact Phone:  "+poi.getPoiPhone(),"Description:  "+poi.getPoiDescription()};

        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < values.length; ++i) {
            list.add(values[i]);
        }

        return list;
    }
    private  class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }
        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}