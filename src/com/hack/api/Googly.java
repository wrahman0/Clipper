package com.hack.api;

import com.hack.network.OnPostParseInterface;

public class Googly extends URLShortener{
	
	private static final String URL = "https://www.googleapis.com/urlshortener/v1/url";
	
	private OnPostParseInterface listener;

	public Googly(OnPostParseInterface listener) {
		this.listener = listener;
	}

	@Override
	public void clipURL(String url) {
		
	}

}
