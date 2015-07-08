package com.example.ruidong.sbu_application.courseManager.service;

/**
 * Created by Ruidong on 6/17/2015.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ruidong.sbu_application.R;
import com.example.ruidong.sbu_application.framework.NavigationActivity;

public class LoginFragment extends Fragment {

    private TextView title;
    private EditText username;
    private EditText password;
    private Button loginButton;
    private NavigationActivity activity;
    private LoginFragment selfFragmnt;
    private CourseHistoryFragment courseHistoryFragment;
    private String GPAData;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment,null);
        title=(TextView)view.findViewById(R.id.textView5);
        username=(EditText)view.findViewById(R.id.usernameEdit);
        password=(EditText)view.findViewById(R.id.passwordEdit);
        loginButton =(Button)view.findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new LoginListner());
        activity=(NavigationActivity)getActivity();
        return view;
    }

    class LoginListner implements OnClickListener{

        @Override
        public void onClick(View v) {
//            new GetGPA().execute(new ApiConnectorGetGPA());

//            new Login().execute(new ApiConnectorLogin());

            authen_login();

        }
    }
    public void authen_login() {
        if((username.getText().toString().equals("109905574"))&&(password.getText().toString().equals("ruidong"))){
            SharedPreferences preferences = this.getActivity().getSharedPreferences("UserInfomation" , Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("username", username.getText().toString());
            editor.commit();
            courseHistoryFragment = new CourseHistoryFragment();
            FragmentTransaction tran1= activity.getSupportFragmentManager().beginTransaction().add(R.id.CourseHistory_container, courseHistoryFragment);
            tran1.addToBackStack(null);
            tran1.commit();
            selfFragmnt = activity.getLoginFragment();
            FragmentTransaction tran2 = activity.getSupportFragmentManager().beginTransaction().remove(selfFragmnt);
            tran2.commit();
        }
        else if (!username.getText().toString().equals("109905574")){
            Toast.makeText(getActivity(), "Username doesn't exist", Toast.LENGTH_LONG).show();
        }
        else if (username.getText().toString().equals("109905574")&&(!password.getText().toString().equals("ruidong"))){
            Toast.makeText(getActivity(), "Invalid password,please try again", Toast.LENGTH_LONG).show();
        }
    }

     public CourseHistoryFragment getCourseHistoryFragment (){
         return this.courseHistoryFragment;
     }


//    public void authen_login(JSONObject jsonObject) throws JSONException {
//
//        if(jsonObject !=null){
//
//            SharedPreferences preferences = this.getActivity().getSharedPreferences("UserInfomation" , Context.MODE_PRIVATE);
//            SharedPreferences.Editor editor = preferences.edit();
//            editor.putString("username", username.getText().toString());
//            editor.commit();
//            courseHistoryFragment = new CourseHistoryFragment();
//            FragmentTransaction tran1= activity.getSupportFragmentManager().beginTransaction().add(R.id.CourseHistory_container, courseHistoryFragment);
//            tran1.addToBackStack(null);
//            tran1.commitAllowingStateLoss();
//            selfFragmnt = activity.getLoginFragment();
//            FragmentTransaction tran2 = activity.getSupportFragmentManager().beginTransaction().remove(selfFragmnt);
//            tran2.commitAllowingStateLoss();
//            activity.getCourseService().setData(jsonObject);
//        }
//
//        else
//            Toast.makeText(getActivity(), "login failed", Toast.LENGTH_LONG).show();
//
//
//
//    }
//    private class Login extends AsyncTask<ApiConnectorLogin, Long, JSONObject> {
//
//        @Override
//        protected JSONObject doInBackground(ApiConnectorLogin... params) {
//
//            return params[0].GetPOI(username.getText().toString(),password.getText().toString());
//        }
//
//        @Override
//        protected void onPostExecute(JSONObject jsonObject){
//
//            try {
//                authen_login(jsonObject);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//
//    }
//    private class GetGPA extends AsyncTask<ApiConnectorGetGPA, Long, JSONObject> {
//
//        @Override
//        protected JSONObject doInBackground(ApiConnectorGetGPA... params) {
//
//            return params[0].GetPOI(username.getText().toString(),password.getText().toString());
//        }
//
//        @Override
//        protected void onPostExecute(JSONObject jsonObject){
//            try {
//                storeData(jsonObject);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//
//    }
//
//    public void storeData(JSONObject jsonObject) throws JSONException {
//
//        JSONArray array = jsonObject.getJSONArray("Course History");
//        for(int i=0; i<array.length(); i++){
//
//            JSONObject json = null;
//            try{
//                json = array.getJSONObject(i);
//                GPAData = json.getString("GPA");
//                System.out.println("==================="+GPAData);
//            } catch (JSONException e){
//                e.printStackTrace();
//            }
//        }
//    }
//    public String GetGPA(){
//        return this.GPAData;
//    }

}

