/*
 * Copyright 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.scheduler;



import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.content.Context;
/**
 * This sample demonstrates how to schedule an alarm that causes a service to
 * be started. This is useful when you want to schedule alarms that initiate
 * long-running operations, such as retrieving a daily forecast.
 * This particular sample retrieves content from the Google home page once a day and  
 * checks it for the search string "doodle". If it finds this string, that indicates 
 * that the page contains a custom doodle instead of the standard Google logo.
 */
public class MainActivity extends FragmentActivity {
//	private GoogleMap map;
   private static Context con;
   GoogleMap  map;
   SampleAlarmReceiver alarm = new SampleAlarmReceiver();
   static SharedPreferences pref;
   static SharedPreferences pref2;
    
   
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	MainActivity.pref = getApplicationContext().getSharedPreferences("LatLng",Context.MODE_PRIVATE);
    	MainActivity.pref2 = getApplicationContext().getSharedPreferences("check",Context.MODE_PRIVATE);
    	if(!((MainActivity.pref.contains("Lat")) && (MainActivity.pref.contains("Lng"))))

    	{
    		Log.i("entered","entered");
    		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
    				this);
     
    			// set title
    			alertDialogBuilder.setTitle("Set your home location first");
     
    			// set dialog message
    			alertDialogBuilder
    				.setMessage("Click yes to set location!")
    				.setCancelable(false)
    				.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
    					public void onClick(DialogInterface dialog,int id) {
    						// if this button is clicked, close
    						// current activity
    						dialog.cancel();
    						Intent intent = new Intent(MainActivity.this, SetLocationActivity.class);
    				    	//alarm.setAlarm(this);
    				    	startActivity(intent);
    					}
    				  })
    				.setNegativeButton("No",new DialogInterface.OnClickListener() {
    					public void onClick(DialogInterface dialog,int id) {
    						// if this button is clicked, just close
    						MainActivity.this.finish();
    						// the dialog box and do nothing
    					}
    				});
     
    				// create alert dialog
    				AlertDialog alertDialog = alertDialogBuilder.create();
     
    				// show it
    				alertDialog.show();
    			}
    	else 
    		Log.i("entered","enteredelse");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(MainActivity.pref2.getString("check","0").equals("0")){
	        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
			
			// set title
			alertDialogBuilder.setTitle("NOTE");
			
			// set dialog message
			Log.i("check","working");
			alertDialogBuilder
			.setMessage("Keep your gps on at all times for better performance")
			.setCancelable(false)
			.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					// if this button is clicked, close
					// current activity
					dialog.cancel();
				}
			})
			.setNegativeButton("Don't show again",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					// if this button is clicked, just close
					MainActivity.pref2.edit().putString("check","1").commit();
					System.out.println(MainActivity.pref2.getString("check",""));
					dialog.cancel();
					// the dialog box and do nothing
				}
			});
			AlertDialog alertDialog = alertDialogBuilder.create();
			
			// show it
			alertDialog.show();
		}
//        Intent intent = new Intent(this, SearchActivity.class);
//    	startActivity(intent);
        /*Uri mapUri = Uri.parse("geo:12.845091,77.663209?q=restaurants");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);*/
       /* GPSTracker gps = new GPSTracker(this);
        double lat=gps.getLatitude();
        Log.i("testing",String.valueOf(lat));
        double lon = gps.getLongitude();
        Log.i("longitude",String.valueOf(lon));
        
        
       /*map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                .getMap();
      map.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
      map.setMyLocationEnabled(true);
      
      map.setOnMapClickListener(new OnMapClickListener() {
    	  
          @Override
          public void onMapClick(LatLng latLng) {

              // Creating a marker
              MarkerOptions markerOptions = new MarkerOptions();

              // Setting the position for the marker
              markerOptions.position(latLng);

              // Setting the title for the marker.
              // This will be displayed on taping the marker
              markerOptions.title(latLng.latitude + " : " + latLng.longitude);

              // Clears the previously touched position
              map.clear();

              // Animating to the touched position
              map.animateCamera(CameraUpdateFactory.newLatLng(latLng));

              // Placing a marker on the touched position
              map.addMarker(markerOptions);
          }
      });
*/
        
    }
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
   
    
    public void alarm(View view){
    	Intent intent = new Intent(this, MealsActivity.class);
    	//alarm.setAlarm(this);
    	startActivity(intent);
    }
    
    
    public void location(View view){
    	
    	Intent intent = new Intent(this, SetLocationActivity.class);
    	//alarm.setAlarm(this);
    	startActivity(intent);
    }
    
    /*public void setLocation(View view){
    	Intent service1 = new Intent(getContext(),GPSTracker.class);
        startWakefulService(context, service1);
    }
*/
    // Menu options to set and cancel the alarm.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       /* switch (item.getItemId()) {
            // When the user clicks START ALARM, set the alarm.
            case R.id.start_action:
            	
            	Intent intent = new Intent(this, SetAlarmActivity.class);
            	//alarm.setAlarm(this);
            	startActivity(intent);
                return true;
            // When the user clicks CANCEL ALARM, cancel the alarm. 
            case R.id.cancel_action:
                alarm.cancelAlarm(this);
                return true;
        }*/
        return false;
    }

public static Context getContext(){
	return MainActivity.con;
}
}
