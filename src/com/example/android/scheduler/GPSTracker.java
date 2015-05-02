package com.example.android.scheduler;
import android.app.AlertDialog;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
 
public class GPSTracker extends IntentService implements LocationListener {
 
	private final Context mContext;
	public final static String EXTRA_MESSAGE1 = "com.example.android.scheduler.MESSAGE1";
	public final static String EXTRA_MESSAGE2 = "com.example.android.scheduler.MESSAGE2";
	private NotificationManager mNotificationManager;
	public static final int NOTIFICATION_ID = 1;
    // flag for GPS status
    boolean isGPSEnabled = false;
 
    // flag for network status
    boolean isNetworkEnabled = false;
 
    // flag for GPS status
    boolean canGetLocation = false;
 
    Location location; // location
    double latitude; // latitude
    double longitude; // longitude
 
    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
 
    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute
 
    // Declaring a Location Manager
    protected LocationManager locationManager;
    public GPSTracker(){
    	super("GPSTracker");
    	this.mContext = MainActivity.getContext();
    	getLocation();
    }
    public GPSTracker(Context context) {
        super("GPSTracker");
    	this.mContext = context;
        getLocation();
    }
    
    public Location getLocation() {
        try {
            locationManager = (LocationManager) mContext
                    .getSystemService(LOCATION_SERVICE);
 
            // getting GPS status
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);
 
            // getting network status
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
 
            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
            	Log.i("crap","fuck");
            } else {
                this.canGetLocation = true;
                // First get location from Network Provider
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("Network", "Network");
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("GPS Enabled", "GPS Enabled");
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }
            }
 
        } catch (Exception e) {
            e.printStackTrace();
        }
 
        return location;
    }
     
    /**
     * Stop using GPS listener
     * Calling this function will stop using GPS in your app
     * */
    public void stopUsingGPS(){
        if(locationManager != null){
            locationManager.removeUpdates(GPSTracker.this);
        }       
    }
     
    /**
     * Function to get latitude
     * */
    public double getLatitude(){
        if(location != null){
            latitude = location.getLatitude();
        }
         
        // return latitude
        return latitude;
    }
     
    /**
     * Function to get longitude
     * */
    public double getLongitude(){
        if(location != null){
            longitude = location.getLongitude();
        }
         
        // return longitude
        return longitude;
    }
     
    /**
     * Function to check GPS/wifi enabled
     * @return boolean
     * */
    public boolean canGetLocation() {
        return this.canGetLocation;
    }
     
    /**
     * Function to show settings alert dialog
     * On pressing Settings button will lauch Settings Options
     * */
    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
      
        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");
  
        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");
  
        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });
  
        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
            }
        });
  
        // Showing Alert Message
        alertDialog.show();
    }
 
    @Override
    public void onLocationChanged(Location location) {
    }
 
    @Override
    public void onProviderDisabled(String provider) {
    }
 
    @Override
    public void onProviderEnabled(String provider) {
    }
 
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }
 
    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
    
    private void sendNotification(String msg) {
        mNotificationManager = (NotificationManager)
               this.getSystemService(Context.NOTIFICATION_SERVICE);
    
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
            new Intent(this, MainActivity.class), 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
        .setSmallIcon(R.drawable.ic_launcher)
        .setContentTitle(getString(R.string.Restaurant_alert))
        .setStyle(new NotificationCompat.BigTextStyle()
        .bigText(msg))
        .setContentText(msg);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
    
	@Override
	protected void onHandleIntent(Intent intent) {
		SetLocationActivity sla = new SetLocationActivity();
		Log.i("entered","entered");
//		double lati = Double.parseDouble(sla.getInstance().getDefaultLat());
		double lati = Double.parseDouble(MainActivity.pref.getString("Lat",""));
//		double longi = Double.parseDouble(sla.getInstance().getDefaultLon());
		double longi = Double.parseDouble(MainActivity.pref.getString("Lng",""));
		Log.i("lat",String.valueOf(lati));
		Log.i("lon",String.valueOf(longi));
		GPSTracker gps = new GPSTracker(this);
    	if(gps.canGetLocation())
    	{
    		double lat = gps.getLatitude();
    		double lon = gps.getLongitude();
    		Log.i("latitude",String.valueOf(lat));
    		Log.i("longitude",String.valueOf(lon));
    		double diff_lat = Math.abs(lat-lati);
    		double diff_lon = Math.abs(lon-longi);
    		String message1= String.valueOf(lat);
    		String message2= String.valueOf(lon);
    		if (diff_lat>0.014 && diff_lon>0.014){
    			//call an activity
    			Intent dialogIntent = new Intent(this, ListService.class);
//    			dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    			dialogIntent.putExtra(EXTRA_MESSAGE1, message1);
    			dialogIntent.putExtra(EXTRA_MESSAGE2, message2);
    	    	startService(dialogIntent);
    		}
    		else{
    			String msg="Time to have meal!"; 
    			sendNotification(msg);
    			try {
    	            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    	            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
    	            r.play();
    	        } catch (Exception e) {
    	            e.printStackTrace();
    	        }
    		}
    		
    	}
    	else
    	{
    	gps.showSettingsAlert();	
    	
    	}
		
	
    	SampleAlarmReceiver.completeWakefulIntent(intent);
	}
 
}