package com.example.ruidong.sbu_application.framework.common.tool;

import android.support.v4.app.Fragment;

/**
 * Created by Ruidong on 9/1/2015.
 */
public class FragmentIdPair {
    private Fragment fragment;
    private int fragmentLayoutID;
    private int fragmentCategoryNumber;

    public FragmentIdPair(Fragment fragment, int Id, int number ){
        this.fragment=fragment;
        this.fragmentLayoutID=Id;
        this.fragmentCategoryNumber=number;
    }

    public Fragment getFragment(){
        return this.fragment;
    }
    public int getFragmentLayoutID(){
        return this.fragmentLayoutID;
    }
    public int getFragmentCategoryNumber(){
        return this.fragmentCategoryNumber;
    }
}
