package com.example.timerproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
	
public class MainActivity extends Activity {
	Button count_up_button;
	Button count_down_button;
	Button connect_button;
	Button disconnect_button;

	
	public void Connect(View view) {
		count_down_button = (Button)findViewById(R.id.button1);
		count_up_button = (Button)findViewById(R.id.button2);
		connect_button = (Button)findViewById(R.id.button3);
		disconnect_button = (Button)findViewById(R.id.button4);
		disconnect_button.setEnabled(true);
		count_up_button.setEnabled(true);
		count_down_button.setEnabled(true);
		
		connect_button.setEnabled(false);
		Intent i = new Intent(this, MyService.class);
		i.putExtra("method"
				, "connect");
		startService(i);
		
	} 
	
	public void Disconnect(View view) {
		connect_button = (Button)findViewById(R.id.button3);
		disconnect_button = (Button)findViewById(R.id.button4);
		connect_button.setEnabled(true);
		count_up_button.setEnabled(false);
		count_down_button.setEnabled(false);
		disconnect_button.setEnabled(false);

		Intent i = new Intent(this, MyService.class);
		i.putExtra("method"
				, "disconnect");
		startService(i);
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void countUp(View view){
		Intent intent = new Intent(this, CountUpActivity.class);
	   /* intent.putExtra("com.example.timerproject.MESSAGE"
, "countup");*/
		startActivity(intent);
	}
	
	public void countDown(View view){
		Intent intent = new Intent(this, CountDownActivity.class);
		/*intent.putExtra("com.example.timerproject.MESSAGE"
				, "countdown");*/
		startActivity(intent);
	}

}
