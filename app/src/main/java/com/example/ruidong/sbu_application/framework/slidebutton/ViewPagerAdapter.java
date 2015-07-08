package com.example.ruidong.sbu_application.framework.slidebutton;

/**
 * Created by Ruidong on 5/27/2015.
 */
import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


import com.example.ruidong.sbu_application.framework.BottomButton;

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