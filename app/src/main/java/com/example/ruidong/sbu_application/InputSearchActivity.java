package com.example.ruidong.sbu_application;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Ruidong on 6/9/2015.
 */
public class InputSearchActivity extends FragmentActivity {
    private Button back;
    private Button done;
    private EditText editText;
    private UserHistoryFragment userHistoryFragment;
    private ArrayList<InputHistory> historyList = new ArrayList<InputHistory>();
    private SharedPreferences historyPreference;
    private Button clear;
    private boolean countFlag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
              setContentView(R.layout.input_search);
        back = (Button)findViewById(R.id.back);
        done = (Button)findViewById(R.id.done);
        clear = (Button)findViewById(R.id.clear);
        editText = (EditText)findViewById(R.id.userInput);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String str = editText.getText().toString();
                if(historyList !=null){
                for(InputHistory input: historyList){
                    if(str.equals(input.getTitle())){
                        Integer previousCount = input.getCount();
                        historyList.remove(input);
                        saveHistory(new InputHistory(str,previousCount+1));
                        countFlag = true;
                        break;
                    }
                 }
                }
                if(countFlag == false){
                    saveHistory(new InputHistory(str,1));
                }

                Intent intent = new Intent();
                Bundle backpack = new Bundle();
                backpack.putString("userInput", str);
                intent.putExtras(backpack);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preference = getSharedPreferences("historyPreference", Context.MODE_PRIVATE);
                preference.edit().clear().commit();
            }
        });

        historyList =loadHistroy();
        if(historyList !=null)
        {
        userHistoryFragment=new UserHistoryFragment();
        userHistoryFragment.SetHistoryList(historyList);
        FragmentTransaction tran = getSupportFragmentManager().beginTransaction().add(R.id.user_history_container,userHistoryFragment);
        tran.commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    public void saveHistory(InputHistory input){
        historyPreference = getSharedPreferences("historyPreference", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = historyPreference.edit();
        if(historyList == null){
            historyList = new ArrayList<InputHistory>();
        }
        historyList.add(input);

        String json = new Gson().toJson(historyList);
        editor.putString("InputHistory", json);

        editor.commit();

    }

    public ArrayList<InputHistory> loadHistroy(){

            SharedPreferences preference = getSharedPreferences("historyPreference", Context.MODE_PRIVATE);
            Type type = new TypeToken<ArrayList<InputHistory>>(){}.getType();
            String json = preference.getString("InputHistory", "");
            ArrayList<InputHistory> inputList = new Gson().fromJson(json, type);
            return inputList;

    }

    public void setcountFlag(boolean b){
        countFlag = b;
    }
    public boolean getcountFlag(){
        return this.countFlag;
    }

}
