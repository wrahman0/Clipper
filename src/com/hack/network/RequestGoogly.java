package com.hack.network;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;
import android.util.Log;

import com.hack.clipper.ClippedUrl;
import com.hack.clipper.MainActivity;

public class RequestGoogly extends AsyncTask<String, String, String[]>{
	
	private OnPostParseInterface listener;
	private String original_url;
	
	public RequestGoogly(OnPostParseInterface listener, String original_url){
		this.listener = listener;
		this.original_url = original_url;
	}

	
	@Override
	protected String[] doInBackground(String... params) {

		HttpClient httpclient = new DefaultHttpClient();
	    HttpPost httppost = new HttpPost(params[0]);
	    
	    try {
	        // Add your data
	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	        nameValuePairs.add(new BasicNameValuePair("longUrl", "https://www.googleapis.com/urlshortener/v1/url"));
	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

	        // Execute HTTP Post Request
	        HttpResponse response = httpclient.execute(httppost);
	        Log.i(MainActivity.TAG,"Response from the google servers: " + response.toString());
	        
	        String[] resp = new String[2];
		    resp[0] = params[0];
		    resp[1] = response.toString();
			
		    return resp;
	        
	    } catch (ClientProtocolException e) {
	        // TODO Auto-generated catch block
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	    }
	    
	    return null;   
	}

	@Override
	protected void onPostExecute(String[] result) {
		super.onPostExecute(result);
		if (result!=null){
			listener.onPostExecute(new ClippedUrl(result[1], result[0], "Clipped Holder", new SimpleDateFormat("h:mma").format(Calendar.getInstance().getTime())));	
		}	
	}	
	
}
