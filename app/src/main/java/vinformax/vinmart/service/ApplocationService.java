package vinformax.vinmart.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class ApplocationService extends Service implements LocationListener {

    private static String TAG = ApplocationService.class.getName();

    boolean isGpsEnabled = false;
    boolean isNetworkEnabled = false;
    boolean isGpsTrackingEnabled = false;
    private final Context mContext;
    Location location;
    double latitude;
    double longitude;

    int geocoderMaxResults = 1;

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BW_UPDATES = 10000 * 100 * 1; //1 minute

    protected LocationManager locationManager;
    private String provider_info;

    public ApplocationService(Context context) {
        this.mContext = context;
        getLocation();

    }
    public void getLocation() {
        try {
            locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
            isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if (isGpsEnabled) {
                this.isGpsTrackingEnabled = true;
                Log.d(TAG, "Application use gps services");
                provider_info = LocationManager.GPS_PROVIDER;

            } else if (isNetworkEnabled) {
                this.isGpsTrackingEnabled = true;
                Log.d(TAG, "Application use Network ");
                provider_info = LocationManager.GPS_PROVIDER;
            }
            if (!provider_info.isEmpty()) {
                locationManager.requestLocationUpdates(provider_info, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                if (locationManager != null) {
                    location = locationManager.getLastKnownLocation(provider_info);

                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Impossible to connect to LocationManager", e);
        }
    }

    public double getLatitude() {
        if (location != null) {
            latitude = location.getLatitude();
        }
        return latitude;
    }

    public double getLongitude() {
        if (location != null) {
            longitude = location.getLongitude();

        }
        return longitude;
    }

    public boolean isGpsTrackingEnabled() {
        return this.isGpsTrackingEnabled;
    }


/*public void showSettingAlert()
{
    AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
    alertDialog.setTitle(R.string.GPSAlertDialogTitle);
    alertDialog.setMessage(R.string.GPSAlertDialogMessage);

    alertDialog.setNegativeButton(R.string.cancel)

}*/

    public List<Address> getGeocoderAddress(Context context)
    {
        if(location != null)
        {
            Geocoder geocoder = new Geocoder(context, Locale.ENGLISH);
            try {

                List<Address> addresses = geocoder.getFromLocation(latitude, longitude,this.geocoderMaxResults);
                return addresses;

            }
            catch (IOException e) {
                e.printStackTrace();
                Log.d(TAG,"immposibale to connect ",e);
            }

        }
        return null;
    }


    public String getAddressLine(Context context)
    {
        List<Address> addresses = getGeocoderAddress(context);
        if (addresses != null && addresses.size()>0)
        {
            Address address = addresses.get(0);
            String addressLine = address.getAddressLine(0);

            return addressLine;
        }
        else {
            return null;
        }
    }
    public String getState(Context context)
    {
        List<Address> addresses = getGeocoderAddress(context);
        if (addresses != null && addresses.size()>0)
        {
            Address address = addresses.get(0);
            String state = address.getAdminArea();

            return state;
        }
        else {
            return null;
        }
    }

    public String getCity(Context context)
    {
        List<Address> addresses = getGeocoderAddress(context);
        if (addresses != null && addresses.size()>0)
        {
            Address address = addresses.get(0);
            String city = address.getSubAdminArea();
            return city;
        }
        else {
            return null;
        }
    }

    public String getCountry(Context context)
    {
        List<Address> addresses = getGeocoderAddress(context);
        if (addresses != null && addresses.size()>0)
        {
            Address address = addresses.get(0);
            String country = address.getCountryName();

            return country;
        }
        else {
            return null;
        }
    }


    public String getPostalcode(Context context)
    {
        List<Address> addresses = getGeocoderAddress(context);
        if (addresses != null && addresses.size()>0)
        {
            Address address = addresses.get(0);
            String postalcode = address.getPostalCode();
            return postalcode;
        }
        else {
            return null;
        }
    }

    public String getSublocality(Context context)
    {
        List<Address> addresses = getGeocoderAddress(context);
        if (addresses != null && addresses.size()>0)
        {
            Address address = addresses.get(0);
            String location = address.getSubLocality();
            return location;
        }
        else {
            return null;
        }
    }



    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}
