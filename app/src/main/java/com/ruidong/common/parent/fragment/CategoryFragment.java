package com.ruidong.common.parent.fragment;

/**
 * Created by Ruidong on 5/27/2015.
 */
import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ruidong.sbu_application.NavigationActivity;

public abstract class CategoryFragment extends Fragment{


    private FragmentActivity mActivity;

    public CategoryFragment() {

    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return super.onCreateView(inflater, container, savedInstanceState);
    }




    public abstract void doneButtonMethod(ArrayList<String> checkedList);

}
