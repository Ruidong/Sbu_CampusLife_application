package com.ruidong.slidebutton;

/**
 * Created by Ruidong on 5/27/2015.
 */
import java.util.ArrayList;
import java.util.Collection;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.widget.TextView;


import com.example.ruidong.sbu_application.BottomButton;
import com.ruidong.specific.service.SbuDailyLifeService;

public class ViewPagerAdapter extends FragmentStatePagerAdapter{


    public ArrayList<BottomButton> bottomButtonList ;

    public ViewPagerAdapter(FragmentManager fm, ArrayList<BottomButton> fragments){

        super(fm);
        this.bottomButtonList=fragments;
    }


    @Override
    public Fragment getItem(int position) {
        // TODO Auto-generated method stub
        return bottomButtonList.get(position);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return bottomButtonList.size();
    }

}