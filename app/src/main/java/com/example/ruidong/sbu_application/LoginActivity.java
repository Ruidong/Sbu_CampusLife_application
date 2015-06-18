package com.example.ruidong.sbu_application;

/**
 * Created by Ruidong on 6/17/2015.
 */
import org.json.JSONArray;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends FragmentActivity {

    private TextView title;
    private EditText username;
    private EditText password;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        title=(TextView)findViewById(R.id.title);
        username=(EditText)findViewById(R.id.usernameEdit);
        password=(EditText)findViewById(R.id.passwordEdit);
        button=(Button)findViewById(R.id.loginButton);
        LoginListner listener = new LoginListner();
        button.setOnClickListener(listener);
    }

    class LoginListner implements OnClickListener{

        @Override
        public void onClick(View v) {
               authen_login();

        }
    }
    public void authen_login() {
        if((username.getText().toString().equals("109905574"))&&(password.getText().toString().equals("ruidong"))){
            Intent intent=new Intent();
            intent.setClass(LoginActivity.this, NavigationActivity.class);
            startActivity(intent);
        }
        else if (!username.getText().toString().equals("109905574")){
            Toast.makeText(getApplicationContext(), "Username doesn't exist", Toast.LENGTH_LONG).show();
        }
        else if (username.getText().toString().equals("109905574")&&(!password.getText().toString().equals("ruidong"))){
            Toast.makeText(getApplicationContext(), "Invalid password,please try again", Toast.LENGTH_LONG).show();
        }
    }




//    public void authen_login(JSONArray jsonArray) {
//
//        if(jsonArray !=null){
//
//            Intent intent=new Intent();
//            intent.setClass(LoginActivity.this, SecondActivity.class);
//            startActivity(intent);
//        }
//
//        else
//            Toast.makeText(getApplicationContext(), "login failed", Toast.LENGTH_LONG).show();
//
//
//
//    }
//    private class GetPOIItem extends AsyncTask<ApiConnectorLogin, Long, JSONArray>{
//
//        @Override
//        protected JSONArray doInBackground(ApiConnectorLogin... params) {
//
//            return params[0].GetPOI(username.getText().toString(),password.getText().toString());
//        }
//
//        @Override
//        protected void onPostExecute(JSONArray jsonArray){
//
//            authen_login(jsonArray);
//        }
//
//    }

}

