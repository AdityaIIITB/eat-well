package com.example.android.scheduler;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MealsActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_meals);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.meals, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void breakfast(View view){
    	Intent intent = new Intent(this, SetAlarmActivity.class);
    	//alarm.setAlarm(this);
    	startActivity(intent);
    }
	
	public void lunch(View view){
    	Intent intent = new Intent(this, LunchActivity.class);
    	//alarm.setAlarm(this);
    	startActivity(intent);
    }
	
	public void dinner(View view){
    	Intent intent = new Intent(this, DinnerActivity.class);
    	//alarm.setAlarm(this);
    	startActivity(intent);
    }
}
