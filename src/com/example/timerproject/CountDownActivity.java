package com.example.timerproject;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;

public class CountDownActivity extends Activity {
    public final static String EXTRA_MESSAGE = "com.example.timerproject.MESSAGE";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_count_down);
		// Show the Up button in the action bar.
		setupActionBar();
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.count_down, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void ten_sec(View view){
		Intent intent = new Intent(this, CountDownTimerPreset.class);
	    intent.putExtra(EXTRA_MESSAGE, "10");
		startActivity(intent);
	}
	
	public void twenty_four_sec(View view){
		Intent intent = new Intent(this, CountDownTimerPreset.class);
	    intent.putExtra(EXTRA_MESSAGE
, "24");
		startActivity(intent);
	}
	
	public void sixty_sec(View view){
		Intent intent = new Intent(this, CountDownTimerPreset.class);
		intent.putExtra(EXTRA_MESSAGE
				, "60");
		startActivity(intent);
	}
	
	public void ten_min(View view){
		Intent intent = new Intent(this, CountDownTimerPreset.class);
		intent.putExtra(EXTRA_MESSAGE
				, "600");
		startActivity(intent);
	}
	
	public void thirty_min(View view){
		Intent intent = new Intent(this, CountDownTimerPreset.class);
		intent.putExtra(EXTRA_MESSAGE
				, "1800");
		startActivity(intent);
	}
	
	public void sixty_min(View view){
		Intent intent = new Intent(this, CountDownTimerPreset.class);
		intent.putExtra(EXTRA_MESSAGE
				, "3600");
		startActivity(intent);
	}
	
	public void manual_set(View view){
		Intent intent = new Intent(this, CountDownTimerPreset.class);
		intent.putExtra(EXTRA_MESSAGE
				, "Manual Set");
		startActivity(intent);
	}

}
