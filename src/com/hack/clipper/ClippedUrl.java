package com.hack.clipper;

public class ClippedUrl {
	
	private String jsonResponse;
	private String originalUrl;
	private String clippedUrl;
	private String timeStamp;
	
	public ClippedUrl(String jsonResponse, String originalUrl, String clippedUrl, String timeStamp) {
		super();
		this.jsonResponse = jsonResponse;
		this.originalUrl = originalUrl;
		this.clippedUrl = clippedUrl;
		this.timeStamp = timeStamp;
	}
	
	public String getJsonResponse() {
		return jsonResponse;
	}
	public void setJsonResponse(String jsonResponse) {
		this.jsonResponse = jsonResponse;
	}
	public String getOriginalUrl() {
		return originalUrl;
	}
	public void setOriginalUrl(String originalUrl) {
		this.originalUrl = originalUrl;
	}
	public String getClippedUrl() {
		return clippedUrl;
	}
	public void setClippedUrl(String clippedUrl) {
		this.clippedUrl = clippedUrl;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	

}
