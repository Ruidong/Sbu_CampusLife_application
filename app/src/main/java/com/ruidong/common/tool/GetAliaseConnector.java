package com.ruidong.common.tool;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class GetAliaseConnector {

    public JSONArray GetPOI(){

        String url = "http://1-dot-river-formula-96210.appspot.com/aliasesServlet";

        //Get HttpResponse Object from url,
        //Get HttpEntity from Http Response Object


        HttpEntity httpEntity = null;

        try{
            DefaultHttpClient httpClient = new DefaultHttpClient();  // Default HttpClient
            HttpPost httpPost = new HttpPost(url);
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            HttpResponse httpResponse = httpClient.execute(httpPost);

            httpEntity = httpResponse.getEntity();

        }catch(ClientProtocolException e){

            // Signals error in http proctocol
            e.printStackTrace();

        }catch(IOException e){
            e.printStackTrace();
        }

        JSONArray jsonArray = null;

        if(httpEntity != null){
            try{
                String entityResponse = EntityUtils.toString(httpEntity);

                Log.e("Entity Response :", entityResponse);

                jsonArray = new JSONArray(entityResponse);

            }catch(JSONException e){
                e.printStackTrace();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        return jsonArray;
    }
}