package com.example.android.scheduler;

import java.util.Calendar;

import android.support.v7.app.ActionBarActivity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Spinner;

public class DinnerActivity extends ActionBarActivity {

	private AlarmManager alarmMgr;
	private PendingIntent alarmIntent;
	private Spinner spinner1, spinner2,spinner3;
	private Button btnSubmit;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_alarm);
		addListenerOnButton();
	}
	
	public void addListenerOnButton() {
		 
		spinner1 = (Spinner) findViewById(R.id.spinner1);
		spinner2 = (Spinner) findViewById(R.id.spinner2);
		spinner3 = (Spinner) findViewById(R.id.spinner3);
		btnSubmit = (Button) findViewById(R.id.button2);
		//String hours,minutes,am;
		
		btnSubmit.setOnClickListener(new OnClickListener() {
	 
		  @Override
		  public void onClick(View v) {
	 
		    String hours  =String.valueOf(spinner1.getSelectedItem()); 
	        String minutes= String.valueOf(spinner2.getSelectedItem());
			String am = String.valueOf(spinner3.getSelectedItem());
			setAlarm(DinnerActivity.this,hours,minutes,am);
			finish();
		  }
	 
		});
	  }
	
	public void setAlarm(Context context,String hours,String minutes,String am) {
	        alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
	        Intent intent = new Intent(context, SampleAlarmReceiver.class);
	        alarmIntent = PendingIntent.getBroadcast(context, 2, intent, 0);

	        Calendar calendar = Calendar.getInstance();
	        calendar.setTimeInMillis(System.currentTimeMillis());
	        // Set the alarm's trigger time to 8:30 a.m.
	        if(am.equals("AM")){
	        	if (hours.equals("12")){
	        		calendar.set(Calendar.HOUR_OF_DAY,0);}
	        	else
	        		calendar.set(Calendar.HOUR_OF_DAY,Integer.parseInt(hours));
	        	}
	        else{
	        	if (hours.equals("12")){
	        		calendar.set(Calendar.HOUR_OF_DAY,12);}
	        	else
	        		calendar.set(Calendar.HOUR_OF_DAY,(Integer.parseInt(hours)+12));
	        }
	        calendar.set(Calendar.MINUTE, Integer.parseInt(minutes));
	  
	        /* 
	         * If you don't have precise time requirements, use an inexact repeating alarm
	         * the minimize the drain on the device battery.
	         * 
	         * The call below specifies the alarm type, the trigger time, the interval at
	         * which the alarm is fired, and the alarm's associated PendingIntent.
	         * It uses the alarm type RTC_WAKEUP ("Real Time Clock" wake up), which wakes up 
	         * the device and triggers the alarm according to the time of the device's clock. 
	         * 
	         * Alternatively, you can use the alarm type ELAPSED_REALTIME_WAKEUP to trigger 
	         * an alarm based on how much time has elapsed since the device was booted. This 
	         * is the preferred choice if your alarm is based on elapsed time--for example, if 
	         * you simply want your alarm to fire every 60 minutes. You only need to use 
	         * RTC_WAKEUP if you want your alarm to fire at a particular date/time. Remember 
	         * that clock-based time may not translate well to other locales, and that your 
	         * app's behavior could be affected by the user changing the device's time setting.
	         * 
	         * Here are some examples of ELAPSED_REALTIME_WAKEUP:
	         * 
	         * // Wake up the device to fire a one-time alarm in one minute.
	         * alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, 
	         *         SystemClock.elapsedRealtime() +
	         *         60*1000, alarmIntent);
	         *        
	         * // Wake up the device to fire the alarm in 30 minutes, and every 30 minutes
	         * // after that.
	         * alarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, 
	         *         AlarmManager.INTERVAL_HALF_HOUR, 
	         *         AlarmManager.INTERVAL_HALF_HOUR, alarmIntent);
	         */
	        
	        // Set the alarm to fire at approximately 8:30 a.m., according to the device's
	        // clock, and to repeat once a day.
	        alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP,  
	                calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, alarmIntent);
	        
	        // Enable {@code SampleBootReceiver} to automatically restart the alarm when the
	        // device is rebooted.
	        ComponentName receiver = new ComponentName(context, SampleBootReceiver.class);
	        PackageManager pm = context.getPackageManager();

	        pm.setComponentEnabledSetting(receiver,
	                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
	                PackageManager.DONT_KILL_APP);
	        
	    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.set_alarm, menu);
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
}
