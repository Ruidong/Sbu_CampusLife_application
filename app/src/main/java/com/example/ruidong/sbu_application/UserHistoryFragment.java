package com.example.ruidong.sbu_application;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Ruidong on 6/9/2015.
 */
public class UserHistoryFragment extends Fragment {
         private ListView list;
         private ArrayList<InputHistory> historyList = new ArrayList<InputHistory>();
         private InputSearchActivity activity;
         public UserHistoryFragment(){

         }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_history_list,null);
        list = (ListView)view.findViewById(R.id.user_history_list);
        UserHistoryAdapter adapter = new UserHistoryAdapter(getActivity(),historyList);

        Collections.sort(historyList, new Comparator<InputHistory>() {
            public int compare(InputHistory input1, InputHistory input2) {

                return input2.getCount().compareTo(input1.getCount());
            }
        });

        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        activity= (InputSearchActivity) getActivity();
        this.list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String str = historyList.get(position).getTitle();


                for(InputHistory input: historyList){
                    if(str.equals(input.getTitle())){
                        Integer previousCount = input.getCount();
                        historyList.remove(input);
                        activity.saveHistory(new InputHistory(str,previousCount+1));
                        activity.setcountFlag(true);
                        break;
                    }
                }

            if(activity.getcountFlag() == false){
                activity.saveHistory(new InputHistory(str, 1));
            }



                Intent intent = new Intent();
                Bundle  backpack = new Bundle();
                String str1 = historyList.get(position).getTitle();
                backpack.putString("userInput", str1);
                intent.putExtras(backpack);
                getActivity().setResult(Activity.RESULT_OK, intent);
                getActivity().finish();
            }
        });

        return view;
    }

    public void SetHistoryList(ArrayList<InputHistory> historyList){
        this.historyList = historyList;
    }
}
