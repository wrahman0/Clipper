package com.hack.clipper;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hack.api.Tinycc;
import com.hack.network.OnPostParseInterface;

public class MainActivity extends Activity implements OnPostParseInterface{
	
	public static final String TAG = "Clipper";
	
	private String[] service = {"tinycc", "bitly", "googly"};
	private String currentService;
	
	private TextView tinycc;
	private TextView bitly;
	private TextView googly;
	private ImageButton mSearch;
	private EditText mSearchEditText;
	private LinearLayout mUrlHolder;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tinycc = (TextView) findViewById(R.id.tinyccView);
        bitly = (TextView) findViewById(R.id.bitlyView);
        googly = (TextView) findViewById(R.id.googlyView);
        mSearch = (ImageButton) findViewById(R.id.search);
        mSearchEditText = (EditText) findViewById(R.id.searchEditText);
        mUrlHolder = (LinearLayout) findViewById(R.id.urlHolder);
        
        tinycc.setOnClickListener(new ClippingServiceClickListener());
        bitly.setOnClickListener(new ClippingServiceClickListener());
        googly.setOnClickListener(new ClippingServiceClickListener());
        
        //Default Service is TinyCC
        setService(service[0]);
        
        mSearch.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String searchQuery = getSearchContent();
				if (currentService == service[0] && searchQuery != null){
					Tinycc tiny = new Tinycc(MainActivity.this);
					tiny.clipURL(searchQuery);
				}else if (currentService == service[1] && searchQuery != null){
					
				}else if (currentService == service[2] && searchQuery != null){
					
				}
			}
		});
    }
    
    private String getSearchContent(){
    	return mSearchEditText.getText().toString().isEmpty() ? "":mSearchEditText.getText().toString();
    }
    
    private void setService (String service){
    	tinycc.setBackgroundColor(Color.TRANSPARENT);
    	bitly.setBackgroundColor(Color.TRANSPARENT);
    	googly.setBackgroundColor(Color.TRANSPARENT);
    	
    	if(service==this.service[0]){
    		tinycc.setBackgroundColor(Color.GRAY);
    		currentService = this.service[0];
    	}else if(service==this.service[1]){
    		bitly.setBackgroundColor(Color.GRAY);
    		currentService = this.service[1];
    	}else if (service==this.service[2]){
    		googly.setBackgroundColor(Color.GRAY);
    		currentService = this.service[2];
    	}
    	
    }
    
    private class ClippingServiceClickListener implements OnClickListener {
        @Override
        public void onClick(View v) {
        	switch(v.getId()){
        	case R.id.tinyccView:
        		setService(service[0]);
        		break;
        	case R.id.bitlyView:
        		setService(service[1]);
        		break;
        	case R.id.googlyView:
        		setService(service[2]);
        		break;
        	}
        }
    }
    

	@Override
	public void onPostExecute(String result) {
		try {
			JSONObject json = new JSONObject(result);
			String clipped_url = json.getJSONObject("results").getString("short_url");
			LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view = inflater.inflate(R.layout.inflate_url, null);
			TextView clipper = (TextView)view.findViewById(R.id.smallUrlName);
			clipper.setText(clipped_url);
			mUrlHolder.addView(view);
//			Toast.makeText(getBaseContext(), clipped_url, Toast.LENGTH_LONG).show();
		} catch (JSONException e) {
			Log.e(MainActivity.TAG, "Unable to construct a JSON object");
			e.printStackTrace();
			return;
		}
	}
}
