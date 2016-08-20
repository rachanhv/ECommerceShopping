package vinformax.vinmart.service;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import vinformax.vinmart.common.SplashScreensActivity;


public class Checknetworkconnection {

	 public static boolean isConnectingToInternet(Context _context){
	        ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
	          if (connectivity != null) 
	          {
	              NetworkInfo[] info = connectivity.getAllNetworkInfo();
	              if (info != null) 
	                  for (int i = 0; i < info.length; i++) 
	                      if (info[i].getState() == NetworkInfo.State.CONNECTED)
	                      {
	                          return true;
	                      }
	 
	          }

		 if(! _context.getClass().equals(SplashScreensActivity.class)) {
			 Intent intent = new Intent(_context, SplashScreensActivity.class);
			 _context.startActivity(intent);
			 ((Activity) _context).finish();
		 }
	          return false;
	    }
}
