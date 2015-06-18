package com.ruidong.courseManager.fragment;

/**
 * Created by Ruidong on 5/27/2015.
 */

// This is the detailed information View for a specific POI!

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

import com.example.ruidong.sbu_application.BottomButton;
import com.example.ruidong.sbu_application.CourseManagerPOI;
import com.example.ruidong.sbu_application.NavigationActivity;
import com.example.ruidong.sbu_application.POI;
import com.example.ruidong.sbu_application.R;
import com.example.ruidong.sbu_application.SbuDailyLifePOI;
import com.ruidong.common.parent.fragment.SumViewFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class CourseSumView extends SumViewFragment {
    private TextView text1;
    private TextView text2;
    private ImageView listDetialedImage;
    private ListView listView;
    private LinearLayout listViewMainButton;
    private CourseManagerPOI poi;
    private LinearLayout detailedListView;
    private String msg1,msg2;
    private Button hideListViewButton;
    private Fragment selfFragment;
    private BottomButton bottomButton;
    private ScrollView scrollView;
    public CourseSumView(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.course_sum_view, container, false);

        detailedListView=(LinearLayout)view.findViewById(R.id.myDetailedListView);
        listViewMainButton=(LinearLayout)view.findViewById(R.id.ListViewMainButton);
        text1=(TextView)view.findViewById(R.id.textView3);
        text2=(TextView)view.findViewById(R.id.textView4);
        listView=(ListView)view.findViewById(R.id.listview);
        scrollView=(ScrollView)view.findViewById(R.id.scrollView);
        hideListViewButton=(Button)view.findViewById(R.id.hideListView);
        setListText();


        hideListViewButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                bottomButton= NavigationActivity.bottomFrag;
                selfFragment= bottomButton.SumViewBot;
                FragmentTransaction tran=getFragmentManager().beginTransaction().remove(selfFragment);
                tran.commit();

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
        this.poi=(CourseManagerPOI)currentPOI;
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

        String[] values = new String[] {"CourseNumber:  "+poi.getCourseNumber(),"CourseName:  "+poi.getCourseName(),
                "CourseTime:  "+poi.getCourseTime(),"Course location:  "+poi.getCourseLocation(),"CourseInstructor:  "+poi.getCourseInstructor()};

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