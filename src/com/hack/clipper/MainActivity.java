package com.hack.clipper;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hack.api.Googly;
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
				mSearchEditText.setText("");
				if (currentService == service[0] && searchQuery != null){
					Tinycc tiny = new Tinycc(MainActivity.this);
					tiny.clipURL(searchQuery);
				}else if (currentService == service[1] && searchQuery != null){

				}else if (currentService == service[2] && searchQuery != null){
					Googly goog = new Googly(MainActivity.this);
					goog.clipURL(searchQuery);
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
			tinycc.setBackground(getResources().getDrawable(R.drawable.rounded_corners));
			currentService = this.service[0];
		}else if(service==this.service[1]){
			bitly.setBackground(getResources().getDrawable(R.drawable.rounded_corners));
			currentService = this.service[1];
		}else if (service==this.service[2]){
			googly.setBackground(getResources().getDrawable(R.drawable.rounded_corners));
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
	public void onPostExecute(ClippedUrl clippedUrl) {

		if (clippedUrl == null){
			return;
		}
		
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.inflate_cards, null);
		TextView clipper = (TextView)view.findViewById(R.id.smallUrlName);
		TextView original_url = (TextView)view.findViewById(R.id.fullUrlName);
		TextView curr_time = (TextView)view.findViewById(R.id.timeStamp);

		//Set the text
		clipper.setText(clippedUrl.getClippedUrl());
		original_url.setText(clippedUrl.getOriginalUrl());
		curr_time.setText(clippedUrl.getTimeStamp());
		view.setTag(clippedUrl);
		ImageButton copy = (ImageButton) view.findViewById(R.id.copy);
		
		copy.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getBaseContext().getSystemService(getBaseContext().CLIPBOARD_SERVICE);
                android.content.ClipData clip = android.content.ClipData.newPlainText("Clipped Url", ((ClippedUrl)v.getTag()).getClippedUrl());
                clipboard.setPrimaryClip(clip);
//				Toast.makeText(getBaseContext(), "Copied: " + ((ClippedUrl)v.getTag()).getClippedUrl() + " to clipboard", Toast.LENGTH_LONG).show();
				
			}
		});
		
		
		
		mUrlHolder.addView(view);
	}
}
