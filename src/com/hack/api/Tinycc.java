package com.hack.api;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import android.util.Log;

import com.hack.clipper.MainActivity;
import com.hack.network.OnPostParseInterface;
import com.hack.network.RequestTinyCC;
import com.hack.processing.URLLogic;


public class Tinycc extends URLShortener{
	
	private static final String API_KEY = "a4df9f72-eb34-4b35-b2e8-a2d0eea6b461";
	private static final String URL_FIRST = "http://tiny.cc/?c=rest_api&m=shorten&version=2.0.3&format=json&longUrl=";
	private static final String URL_SECOND = "&login=wrahman&apiKey=";
	
	private OnPostParseInterface listener;
	
	public Tinycc (OnPostParseInterface listener){
		this.listener = listener;
	}
	
	@Override
	public void clipURL(String url) {
		String encoded_url;
		try {
			url = URLLogic.validateUrl(url);
			encoded_url = URLEncoder.encode(url, "utf-8");
			Log.e(MainActivity.TAG, "SHORTENING: " + encoded_url);
		} catch (UnsupportedEncodingException e) {
			Log.e(MainActivity.TAG,"Unable to encode URL");
			e.printStackTrace();
			return;
		}
		RequestTinyCC requestClippingService = new RequestTinyCC(listener, url);
		requestClippingService.execute(URL_FIRST + encoded_url + URL_SECOND + API_KEY);
	}
	
	

}
