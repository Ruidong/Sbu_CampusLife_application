package com.example.ruidong.sbu_application;

/**
 * Created by Ruidong on 5/27/2015.
 */



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ruidong.courseManager.fragment.CourseSumView;
import com.ruidong.dailylife.fragment.SbuDailySumView;
import com.ruidong.event.fragment.EventSumView;

public class BottomButton extends Fragment{

    public TextView text1;
    public TextView text2;
    public Fragment SumViewBot;
    private LinearLayout bottombutton;
    public boolean DailySumFlag;
    private POI bottomButtonPOI;
    private String msg1,msg2;
    private int position;
    private NavigationActivity activity;
    public BottomButton() {

    }

//  A way to send parameters to fragment
    static  BottomButton newInstance(String str1, String str2,int position){

        BottomButton bottomButton = new BottomButton();
        Bundle bundle = new Bundle();
        bundle.putString("msg1", str1);
        bundle.putString("msg2", str2);
        bundle.putInt("position",position);
        bottomButton.setArguments(bundle);
        return bottomButton;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.bottom_button, container, false);
        activity = (NavigationActivity)getActivity();

        text1=(TextView)view.findViewById(R.id.textView1);
        text2=(TextView)view.findViewById(R.id.textView2);
        bottombutton=(LinearLayout)view.findViewById(R.id.bottomInFra);
        msg1=getArguments().getString("msg1");
        msg2=getArguments().getString("msg2");
        text1.setText(msg1);
        text2.setText(msg2);
        position=getArguments().getInt("position");


//      When a user click the bottombutton, create a relative SbuDailySumView Fragment and show it.
        bottombutton.setOnClickListener( new BottomButtonOnClickListener(position));
        return view;
    }

    private class BottomButtonOnClickListener implements OnClickListener {
        private int position;

        BottomButtonOnClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            switch (position) {

                case 0: {
                    SbuDailySumView dailySumView = new SbuDailySumView();
                    dailySumView.setPOI(bottomButtonPOI);
                    dailySumView.setMsg(msg1, msg2);
                    FragmentTransaction dailySumViewStart = getFragmentManager().beginTransaction().add(R.id.summaryView, dailySumView);
                    dailySumViewStart.show(dailySumView);
                    dailySumViewStart.addToBackStack(null);
                    dailySumViewStart.commit();
                    SumViewBot = dailySumView;
                    break;
                    }
                case 1: {
                    CourseSumView courseSumView = new CourseSumView();
                    courseSumView.setPOI(bottomButtonPOI);
                    courseSumView.setMsg(msg1, msg2);
                    FragmentTransaction courseSumViewStart = getFragmentManager().beginTransaction().add(R.id.summaryView, courseSumView);
                    courseSumViewStart.show(courseSumView);
                    courseSumViewStart.addToBackStack(null);
                    courseSumViewStart.commit();
                    SumViewBot = courseSumView;
                    break;
                }
                case 2: {
                    EventSumView eventSumView = new EventSumView();
                    eventSumView.setPOI(bottomButtonPOI);
                    eventSumView.setMsg(msg1, msg2);
                    FragmentTransaction eventSumViewStart = getFragmentManager().beginTransaction().add(R.id.summaryView, eventSumView);
                    eventSumViewStart.show(eventSumView);
                    eventSumViewStart.addToBackStack(null);
                    eventSumViewStart.commit();
                    SumViewBot = eventSumView;
                    break;
                }


            }
        }
    }

    public void setPOI(POI currentPOI){
        this.bottomButtonPOI=currentPOI;
    }


    public void setbottomText1(String str){
        this.msg1=str;
        text1.setText(str);
    }

    public void setbottomText2(String str){
        this.msg2=str;
        text2.setText(str);
    }


}