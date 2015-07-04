package com.ruidong.common.tool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;


public class ApiConnectorLogin {
	
	public JSONObject GetPOI(String username, String password){
		
		String url = "http://172.20.248.169:8080/HtmlUnitTest2/GetCourseHistoryServlet" ;
		
		//Get HttpResponse Object from url,
		//Get HttpEntity from Http Response Object
		
		HttpEntity httpEntity = null;
		
		try{
			DefaultHttpClient httpClient = new DefaultHttpClient();  // Default HttpClient
			HttpPost httpPost = new HttpPost(url);
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("username", username));
			nvps.add(new BasicNameValuePair("password", password));
			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
			HttpResponse httpResponse = httpClient.execute(httpPost);
			
			httpEntity = httpResponse.getEntity();
			
		}catch(ClientProtocolException e){
			
			// Signals error in http proctocol
			e.printStackTrace();
			
		}catch(IOException e){
			e.printStackTrace();
		}

		JSONObject jsonObject = null;
		
		if(httpEntity != null){
			try{
				String entityResponse = EntityUtils.toString(httpEntity);
				
				Log.e("Entity Response :", entityResponse);

				jsonObject = new JSONObject(entityResponse);
				
			}catch(JSONException e){
				e.printStackTrace();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		
		return jsonObject;
	}
}
