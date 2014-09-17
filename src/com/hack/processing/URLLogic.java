package com.hack.processing;

public class URLLogic {

	public static String validateUrl(String url){
		if (url.indexOf("http") != 0){
			url = "http://" + url;
		}
		return url;
	}
	
}
