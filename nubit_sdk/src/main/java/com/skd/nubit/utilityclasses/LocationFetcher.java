package com.skd.nubit.utilityclasses;


import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

public class LocationFetcher {
    private Context context;

    public LocationFetcher(Context context) {
        this.context = context;
    }

    // Method to fetch last known location
    public Location getLastLocation() {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null) {
            try {
                // Check for permission before accessing location
                if (context.checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                        android.content.pm.PackageManager.PERMISSION_GRANTED ||
                        context.checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                                android.content.pm.PackageManager.PERMISSION_GRANTED) {

                    // Fetch last known location from GPS provider
                    Location lastKnownLocationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (lastKnownLocationGPS != null) {
                        return lastKnownLocationGPS;
                    }

                    // If GPS provider does not provide location, try fetching from network provider
                    return locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
