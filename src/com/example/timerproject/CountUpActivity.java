package com.example.timerproject;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class CountUpActivity extends Activity {
	
	private boolean timerHasStarted = false;
	private Button playPauseButton;
	private Button resetButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_count_up);
		// Show the Up button in the action bar.
		setupActionBar();
		
		playPauseButton = (Button) findViewById(R.id.playPauseButton);
		resetButton = (Button) findViewById(R.id.resetButton);
	}
	
	
	public void playPause(View view) {
		if(!timerHasStarted) {
			timerHasStarted = true;
			Intent i = new Intent(this, MyService.class);
			i.putExtra("method"
					, "count_up");
			startService(i);
			playPauseButton.setText("PAUSE");
		}
		else {
			Intent i = new Intent(this, MyService.class);
			i.putExtra("method"
					, "pause");
			startService(i);
			timerHasStarted = false;
			playPauseButton.setText("PLAY");
		}
	}
	
	public void reset(View view) {
		Intent i = new Intent(this, MyService.class);
		i.putExtra("method"
				, "reset");
		startService(i);
		//timerHasStarted = false;
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
		getMenuInflater().inflate(R.menu.count_up, menu);
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

}
