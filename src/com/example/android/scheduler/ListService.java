package com.example.android.scheduler;
import java.util.List;

import com.example.android.scheduler.SearchActivity.GetPlaces;

import android.app.AlertDialog;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
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
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.ListView;

public class ListService extends IntentService{
	
	private Intent ir=null; 
	private NotificationManager mNotificationManager;
	public static final int NOTIFICATION_ID = 1;
	
	public ListService() {
        super("ListService");
    }
	
	 @Override
	   protected void onHandleIntent(Intent intent){
		 Log.i("entered","enteredlist");
	 }
	 
	 @Override
	public int onStartCommand (Intent intent, int flags, int startId) {
		    ir=intent;
		    Log.i("entered","enteredonstart");
//		    findNearLocation();
		    new GetPlaces(this).execute();
			return 0; 
		}
	 
	 class GetPlaces extends AsyncTask<Void, Void, Void>{
	        Context context;
//	        private ListView listView;
//	        private ProgressDialog bar;
	        public GetPlaces(Context context) {
	            // TODO Auto-generated constructor stub
	            this.context = context;
//	            this.listView = listView;
	        }

	        @Override
	        protected void onPostExecute(Void result) {
	            // TODO Auto-generated method stub
	            //super.onPostExecute(result);
	            //bar.dismiss();
	              //this.listView.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, placeName));

	        }

	        @Override
	        protected void onPreExecute() {
	            // TODO Auto-generated method stub
//	            super.onPreExecute();
//	              bar =  new ProgressDialog(context);
//	            bar.setIndeterminate(true);
//	            bar.setTitle("Loading");
//	            bar.show();


	        }

	        @Override
	        protected Void doInBackground(Void... arg0) {
	            // TODO Auto-generated method stub
	            findNearLocation();
	            return null;
	        }

	    }
	 
	 private void sendNotification(String msg,String msg2) {
	        mNotificationManager = (NotificationManager)
	               this.getSystemService(Context.NOTIFICATION_SERVICE);
	        Uri mapUri = Uri.parse(msg2);
	        Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapUri);
	        mapIntent.setPackage("com.google.android.apps.maps");
//	        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
//	            new Intent(this, SetAlarmActivity.class), 0);
	        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
	                mapIntent, 0);
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
	    private void sendNotification1(String msg) {
	        mNotificationManager = (NotificationManager)
	               this.getSystemService(Context.NOTIFICATION_SERVICE);
	        
	        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
	            new Intent(this, MainActivity.class), 0);
//	        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
//	                mapIntent, 0);
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
	    
	    public void findNearLocation()   {
	    	
	    	Log.i("entered","enteredlistfunction");

	        PlacesService service = new PlacesService("AIzaSyCYxA9xjH_roEKFXv-GA9TtY7boYAk-19Q");

	       /* 
	        Hear you should call the method find nearst place near to central park new delhi then we pass the lat and lang of central park. hear you can be pass you current location lat and lang.The third argument is used to set the specific place if you pass the atm the it will return the list of nearest atm list. if you want to get the every thing then you should be pass "" only   
	       */
	        String message1 = ir.getStringExtra(GPSTracker.EXTRA_MESSAGE1);
	        String message2 = ir.getStringExtra(GPSTracker.EXTRA_MESSAGE2);
	        Log.i("m1",message1);
	        Log.i("m1",message2);
	        double latitude = Double.parseDouble(message1);
	        double longitude= Double.parseDouble(message2);

	/* hear you should be pass the you current location latitude and langitude, */
	          List<Place> findPlaces = service.findPlaces(latitude,longitude,"restaurant");
	          String msg = "There are "+findPlaces.size()+" restaurants around for you to check out";
	          String msg2= "geo:"+message1+","+message2+"?q=restaurants";
	          if (findPlaces.size()>0){
	        	  ////
	        	  
	              sendNotification(msg,msg2);
	              
	              try {
	                  Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
	                  Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
	                  r.play();
	              } catch (Exception e) {
	                  e.printStackTrace();
	              }
	              //startActivity(mapIntent);
	        	  ////
	          }
	          else{
	  			 msg="No restaurants around!";
	  			 
	  			 sendNotification1(msg);
	  			try {
	  	            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
	  	            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
	  	            r.play();
	  	        } catch (Exception e) {
	  	            e.printStackTrace();
	  	        }
	  		}
	    }
	 
}
