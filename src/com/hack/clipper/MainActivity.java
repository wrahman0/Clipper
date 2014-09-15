package com.hack.clipper;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.hack.network.OnPostParseInterface;

public class MainActivity extends Activity implements OnPostParseInterface{
	
	public static final String TAG = "Clipper";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
    }

	@Override
	public void onPostExecute(String result) {
		Toast.makeText(getBaseContext(), result.toString(), Toast.LENGTH_LONG).show();
	}
}
