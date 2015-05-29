package com.example.ruidong.sbu_application;

/**
 * Created by Ruidong on 5/27/2015.
 */



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;


public class ShowListButton extends Fragment {
    private Button button;
    public ShowListButton(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.showlist_button, container, false);

        button=(Button)view.findViewById(R.id.button);

        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                FragmentTransaction tran = getFragmentManager().beginTransaction().show(NavigationActivity.resultFragment);
                tran.commit();

            }
        });



        return view;
    }

}