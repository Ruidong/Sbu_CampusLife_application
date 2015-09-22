package com.example.ruidong.sbu_application.event.service;

/**
 * Created by Ruidong on 7/1/2015.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
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

import java.util.ArrayList;


public class EventSumView extends SumViewFragment {
    private TextView text1;
    private TextView text2;
    private ImageView listDetialedImage;
    private NestedListView listView;
    private LinearLayout listViewMainButton;
    private EventPOI poi;
    private LinearLayout detailedListView;
    private String msg1,msg2;
    private Button hideListViewButton;
    private Fragment selfFragment;
    private BottomButton bottomButton;
    private ScrollView scrollView;
    private NavigationActivity activity;
    public EventSumView(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.course_sum_view, container, false);
        activity = (NavigationActivity)getActivity();
        detailedListView=(LinearLayout)view.findViewById(R.id.myDetailedListView);
        listViewMainButton=(LinearLayout)view.findViewById(R.id.ListViewMainButton);
        text1=(TextView)view.findViewById(R.id.textView3);
        text2=(TextView)view.findViewById(R.id.textView4);
        listView=(NestedListView)view.findViewById(R.id.listview);
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
            }
        });

        ArrayList<String> listInfo=getTargetList();
        EventSumViewAdapter adapter = new EventSumViewAdapter(getActivity(),listInfo);
        listView.setAdapter(adapter);
//        setListViewHeight(listView);
        scrollView.scrollTo(0, scrollView.getBottom());
        return view;
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
        this.poi=(EventPOI)currentPOI;
    }

    public void setMsg(String str1,String str2){
        this.msg1=str1;
        this.msg2=str2;
    }


    private void setListText(){
        text1.setText(msg1);
        text2.setText(msg2);
    }

    private ArrayList<String> getTargetList(){

        String[] values = new String[] {"Event Name:  "+poi.getEventName(),"Event Time:  "+poi.getEventTime(),
                "Event Location:  "+poi.getEventLocation(),"Event Contact Email:  "+poi.getEventContactEmail(), "Event Description:  "+poi.getEventDescription()};

        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < values.length; ++i) {
            list.add(values[i]);
        }

        return list;
    }

}
