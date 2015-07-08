package com.example.ruidong.sbu_application.framework;

/**
 * Created by Ruidong on 5/27/2015.
 */






import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ruidong.sbu_application.framework.POI;

public abstract class SumViewFragment extends Fragment{

    public POI poi;

    public SumViewFragment(){

    }


    public abstract void setPOI(POI currentPOI);


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        return null;
    }



}

