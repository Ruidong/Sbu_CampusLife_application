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

import com.ruidong.dailylife.fragment.SbuDailySumView;

public class BottomButton extends Fragment{

    public TextView text1;
    public TextView text2;
    public Fragment dailySumViewBot;
    private LinearLayout bottombutton;
    public boolean DailySumFlag;
    private POI bottomButtonPOI;
    String msg1,msg2;
    public BottomButton() {

    }

    static  BottomButton newInstance(String str1, String str2){

        BottomButton bottomButton = new BottomButton();
        Bundle bundle = new Bundle();
        bundle.putString("msg1", str1);
        bundle.putString("msg2", str2);
        bottomButton.setArguments(bundle);
        return bottomButton;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.bottom_button, container, false);


        text1=(TextView)view.findViewById(R.id.textView1);
        text2=(TextView)view.findViewById(R.id.textView2);
        bottombutton=(LinearLayout)view.findViewById(R.id.bottomInFra);
        msg1=getArguments().getString("msg1");
        msg2=getArguments().getString("msg2");
        text1.setText(msg1);
        text2.setText(msg2);




        bottombutton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                SbuDailySumView dailySumView = new SbuDailySumView();
                dailySumView.setPOI(bottomButtonPOI);

                dailySumView.setMsg(msg1,msg2);
                FragmentTransaction dailySumViewStart = getFragmentManager().beginTransaction().add(R.id.summaryView,dailySumView);
                dailySumViewStart.show(dailySumView);
                dailySumViewStart.commit();
                dailySumViewBot=dailySumView;
                DailySumFlag=true;
            }
        });

        return view;
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