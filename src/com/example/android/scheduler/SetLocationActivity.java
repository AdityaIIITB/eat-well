package com.example.android.scheduler;


import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;

public class SetLocationActivity extends FragmentActivity {
	GoogleMap  map;
	
	static String lat;
	static String lon;
	SetLocationActivity SLA;
//	public final static String EXTRA_MESSAGE = "com.example.a.MESSAGE";
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_location);
		map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                .getMap();
//		MainActivity.pref = this.getSharedPreferences("LatLng",Context.MODE_PRIVATE);
		if((MainActivity.pref.contains("Lat")) && (MainActivity.pref.contains("Lng")))
				  {   
				  lat = MainActivity.pref.getString("Lat","");
				  lon = MainActivity.pref.getString("Lng","");    
				  LatLng l = new LatLng(Double.parseDouble(lat),Double.parseDouble(lon));
				  Log.i("lat",lat);
				  Log.i("lon",lon);
				  }
		map.setOnMapClickListener(new OnMapClickListener() {
	    	  
	          @Override
	          public void onMapClick(LatLng latLng) {
	        	  MainActivity.pref.edit().putString("Lat",String.valueOf(latLng.latitude)).commit();
	        	  MainActivity.pref.edit().putString("Lng",String.valueOf(latLng.longitude)).commit();
	        	  
//	        	  Intent intent = new Intent(SetLocationActivity.this, MainActivity.class);
//	        	  intent.putExtra(EXTRA_MESSAGE, message);
	        	  
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
	              SLA = SetLocationActivity.this;
	            
	          }
	      });
	}
	
	public void back(View view){
		SetLocationActivity.this.finish();
	}
	
	public SetLocationActivity getInstance(){
		   return   SLA;
		 }
	public String getDefaultLat()
	{
	 return lat;	
	}
	
	public String getDefaultLon()
	{
	 return lon;	
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.set_location, menu);
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
