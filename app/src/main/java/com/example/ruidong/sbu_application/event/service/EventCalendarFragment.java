package com.example.ruidong.sbu_application.event.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Locale;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.ruidong.sbu_application.framework.NavigationActivity;
import com.example.ruidong.sbu_application.framework.POI;
import com.example.ruidong.sbu_application.R;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by Ruidong on 6/29/2015.
 */
public class EventCalendarFragment extends Fragment
{
    public GregorianCalendar month, itemmonth;// calendar instances.

    public CalendarAdapter adapter;// adapter instance
    public Handler handler;// for grabbing some event values for showing the dot
    // marker.
    public ArrayList<String> items; // container to store calendar items which
    // needs showing the event marker

    private ScrollView scrollView;
    ArrayList<String> event;
    LinearLayout rLayout;
    ArrayList<String> date;
    ArrayList<POI> desc;

    private View view;
    private NavigationActivity activity;
    private ListView listView;
    private TextView bottomText;
    private ArrayList<EventPOI> listInfo = new ArrayList<>();
    private Utility utility;
    private EventCalendarFragment selfFragment;
    private int listViewTouchAction;
    public EventCalendarFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.event_calendar,null);

        int widthPixels=view.getContext().getResources().getDisplayMetrics().widthPixels;

        scrollView=(ScrollView)view.findViewById(R.id.scrollView);

        listView=(ListView)view.findViewById(R.id.listview);



        bottomText = (TextView)view.findViewById(R.id.bottomText);
        Locale.setDefault(Locale.US);

        activity = (NavigationActivity)getActivity();
        utility =new Utility(activity);
        month = (GregorianCalendar) GregorianCalendar.getInstance();
        itemmonth = (GregorianCalendar) month.clone();
        items = new ArrayList<String>();
        adapter = new CalendarAdapter(getActivity(), month);
        GridViewScrollable gridview = (GridViewScrollable) view.findViewById(R.id.gridview);
        gridview.setExpanded(true);
        gridview.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        handler = new Handler();
        handler.post(calendarUpdater);
        TextView title = (TextView)view.findViewById(R.id.title);
        title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));
        RelativeLayout previous = (RelativeLayout)view.findViewById(R.id.previous);
        previous.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                setPreviousMonth();
                refreshCalendar();
            }
        });
        RelativeLayout next = (RelativeLayout)view.findViewById(R.id.next);
        next.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                setNextMonth();
                refreshCalendar();
            }
        });


        gridview.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                // removing the previous view if added
                desc = new ArrayList<POI>();
                date = new ArrayList<String>();
                listInfo.clear();
                activity.map.clear();
                ((CalendarAdapter) parent.getAdapter()).setSelected(v);
                String selectedGridDate = CalendarAdapter.dayString
                        .get(position);
                System.out.println("+++++++++" + selectedGridDate);
                String[] separatedTime = selectedGridDate.split("-");
                String gridvalueString = separatedTime[2].replaceFirst("^0*",
                        "");// taking last part of date. ie; 2 from 2012-12-02.
                int gridvalue = Integer.parseInt(gridvalueString);
                // navigate to next or previous month on clicking offdays.
                if ((gridvalue > 10) && (position < 8)) {
                    setPreviousMonth();
                    refreshCalendar();
                } else if ((gridvalue < 7) && (position > 28)) {
                    setNextMonth();
                    refreshCalendar();
                }
                ((CalendarAdapter) parent.getAdapter()).setSelected(v);

                for (int i = 0; i < utility.startDates.size(); i++) {
                    if (utility.startDates.get(i).equals(selectedGridDate)) {
                        desc.add(utility.eventPoiList.get(i));
                    }
                }

                setList(desc);
                EventResultListAdapter adapter = new EventResultListAdapter(getActivity(),
                        listInfo);


                listView.setAdapter(adapter);

               setListViewHeight(listView);



                if (desc.size() == 0) {
                    bottomText.setText("No Event in this day !");
                }
                else {
                    NavigationActivity.myMarkerList = desc;
                    activity.setBottomButtonFragmentList(desc, 2);
                    activity.setUpCluster(desc);
                    NavigationActivity.myMarkerList.clear();
                    NavigationActivity.editText.setText(" ");
                    bottomText.setText(" ");
                }

                desc = null;
            }

        });

        scrollView.scrollTo(0, scrollView.getBottom());

        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(listInfo.size()!= 0){
                    activity.setServiceNumber(2);
                    selfFragment = activity.getEventCalendarFragment();
                    FragmentTransaction tran = getFragmentManager().beginTransaction().remove(selfFragment);
                    tran.commit();

                    POI currentPOI = listInfo.get(position);
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
            }
        });

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


    public void setList( ArrayList<POI> resultPoiList){
        for(POI PoiElement : resultPoiList)
        {
            EventPOI eventPoi = (EventPOI)PoiElement;
            listInfo.add(eventPoi);
        }

    }

    protected void setNextMonth() {
        if (month.get(GregorianCalendar.MONTH) == month
                .getActualMaximum(GregorianCalendar.MONTH)) {
            month.set((month.get(GregorianCalendar.YEAR) + 1),
                    month.getActualMinimum(GregorianCalendar.MONTH), 1);
        } else {
            month.set(GregorianCalendar.MONTH,
                    month.get(GregorianCalendar.MONTH) + 1);
        }

    }

    protected void setPreviousMonth() {
        if (month.get(GregorianCalendar.MONTH) == month
                .getActualMinimum(GregorianCalendar.MONTH)) {
            month.set((month.get(GregorianCalendar.YEAR) - 1),
                    month.getActualMaximum(GregorianCalendar.MONTH), 1);
        } else {
            month.set(GregorianCalendar.MONTH,
                    month.get(GregorianCalendar.MONTH) - 1);
        }

    }



    public void refreshCalendar() {
        TextView title = (TextView) view.findViewById(R.id.title);

        adapter.refreshDays();
        adapter.notifyDataSetChanged();
        handler.post(calendarUpdater); // generate some calendar items

        title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));
    }

    public Runnable calendarUpdater = new Runnable() {

        @Override
        public void run() {
            items.clear();

            // Print dates of the current week
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            String itemvalue;
            event = utility.readCalendarEvent(getActivity());
            Log.d("=====Event====", event.toString());
            Log.d("=====Date ARRAY====", utility.startDates.toString());

            for (int i = 0; i < utility.startDates.size(); i++) {
                itemvalue = df.format(itemmonth.getTime());
                itemmonth.add(GregorianCalendar.DATE, 1);
                items.add(utility.startDates.get(i).toString());
            }
            adapter.setItems(items);
            adapter.notifyDataSetChanged();
        }
    };
}
