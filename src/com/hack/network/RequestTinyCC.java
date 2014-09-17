package com.hack.network;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

import com.hack.clipper.ClippedUrl;
import com.hack.clipper.MainActivity;

public class RequestTinyCC extends AsyncTask<String, String, String[]>{	
	
	private OnPostParseInterface listener;
	private String original_url;
	
	public RequestTinyCC(OnPostParseInterface listener, String original_url){
		this.listener = listener;
		this.original_url = original_url;
	}

    @Override
    protected String[] doInBackground(String... url) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response;
        String responseString = null;
        try {
            response = httpclient.execute(new HttpGet(url[0]));
            StatusLine statusLine = response.getStatusLine();
            if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                out.close();
                responseString = out.toString();
            } else{
                //Closes the connection.
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        } catch (ClientProtocolException e) {
        	Log.e(MainActivity.TAG,"Client Protocol Exception");
            e.printStackTrace();
        } catch (IOException e) {
        	Log.e(MainActivity.TAG,"IO Exception");
        	e.printStackTrace();
        }
        String[] resp = new String[2];
        resp[0] = responseString;
        resp[1] = this.original_url;
        return resp;
    }

    @Override
    protected void onPostExecute(String[] result) {
        super.onPostExecute(result);
        Log.e(MainActivity.TAG,result[0]);
        
		try {	
			JSONObject json = new JSONObject(result[0]);
	        String clipped_url;
			clipped_url = json.getJSONObject("results").getString("short_url");
			listener.onPostExecute(new ClippedUrl(result[0],result[1],clipped_url, new SimpleDateFormat("h:mma").format(Calendar.getInstance().getTime())));
		} catch (JSONException e) {
			Log.e(MainActivity.TAG, "Could not construct JSON Object!");
			e.printStackTrace();
			listener.onPostExecute(null);
		}
        
        
    }
}
