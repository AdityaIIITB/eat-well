package com.example.android.scheduler;

    import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;


    import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.scheduler.R;
//import com.google.android.maps.MapActivity;
//import com.google.android.maps.MapController;
//import com.google.android.maps.MapView;
//import com.google.android.maps.GeoPoint;
//import com.google.android.maps.MapActivity;
//import com.google.android.maps.MapController;
//import com.google.android.maps.MapView;
//import com.google.android.maps.Overlay;

    /**
     * @author dwivedi ji     * 
     *        */
    public class SearchActivity extends ListActivity {

    private String[] placeName;
    private String[] imageUrl;
    private NotificationManager mNotificationManager;
	public static final int NOTIFICATION_ID = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);




        new GetPlaces(this,getListView()).execute();
    }

    class GetPlaces extends AsyncTask<Void, Void, Void>{
        Context context;
        private ListView listView;
        private ProgressDialog bar;
        public GetPlaces(Context context, ListView listView) {
            // TODO Auto-generated constructor stub
            this.context = context;
            this.listView = listView;
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
//            super.onPreExecute();
//              bar =  new ProgressDialog(context);
//            bar.setIndeterminate(true);
//            bar.setTitle("Loading");
//            bar.show();


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
//        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
//            new Intent(this, SetAlarmActivity.class), 0);
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
//        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
//                mapIntent, 0);
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

        PlacesService service = new PlacesService("AIzaSyCYxA9xjH_roEKFXv-GA9TtY7boYAk-19Q");

       /* 
        Hear you should call the method find nearst place near to central park new delhi then we pass the lat and lang of central park. hear you can be pass you current location lat and lang.The third argument is used to set the specific place if you pass the atm the it will return the list of nearest atm list. if you want to get the every thing then you should be pass "" only   
       */
        Intent intent = getIntent();
        String message1 = intent.getStringExtra(GPSTracker.EXTRA_MESSAGE1);
        String message2 = intent.getStringExtra(GPSTracker.EXTRA_MESSAGE2);
        Log.i("m1",message1);
        double latitude = Double.parseDouble(message1);
        double longitude= Double.parseDouble(message2);

/* hear you should be pass the you current location latitude and langitude, */
          List<Place> findPlaces = service.findPlaces(latitude,longitude,"restaurant");
          String msg = "There are "+findPlaces.size()+" restaurants around for you to check out";
          String msg2= "geo:"+message1+","+message2+"?q=restaurants";
          if (findPlaces.size()>0){
        	  ////
        	  
              sendNotification(msg,msg2);
              finish();
              //startActivity(mapIntent);
        	  ////
          }
          else{
  			 msg="No restaurants around!";
  			 sendNotification1(msg);
  			 finish();
  		}
        	  

            placeName = new String[findPlaces.size()];
            imageUrl = new String[findPlaces.size()];

          for (int i = 0; i < findPlaces.size(); i++) {

              Place placeDetail = findPlaces.get(i);
              placeDetail.getIcon();

            System.out.println(  placeDetail.getName());
            placeName[i] =placeDetail.getName();

            imageUrl[i] =placeDetail.getIcon();

        }





    }


}