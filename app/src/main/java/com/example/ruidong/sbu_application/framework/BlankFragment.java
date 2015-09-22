package com.example.ruidong.sbu_application.framework;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ruidong.sbu_application.R;

/**
 * Created by Ruidong on 9/21/2015.
 */
public class BlankFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.blank_fragment,container,false);
        return view;

    }
}
