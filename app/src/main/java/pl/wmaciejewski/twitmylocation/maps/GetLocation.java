package pl.wmaciejewski.twitmylocation.maps;

import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import java.util.Observable;

/**
 * Created by w.maciejewski on 2014-11-13.
 */
public class GetLocation extends Observable {
    private LocationManager locationManager;
    private HereLocationListener hereLocationListener;



    private Location lastKnowLocation;

    public GetLocation(LocationManager locationManager){


        this.locationManager= locationManager;
        String provider = locationManager.GPS_PROVIDER;
        hereLocationListener=new HereLocationListener();
        updateLastKnowLocation(locationManager.getLastKnownLocation(provider));
        this.locationManager.requestLocationUpdates(provider,5,10,new HereLocationListener() );
    }

    public void getSinglePosition(){
        this.locationManager.requestSingleUpdate(locationManager.GPS_PROVIDER,hereLocationListener,null);
    }



    public void updateLastKnowLocation(Location location) {
        lastKnowLocation=location;
        setChanged();
        notifyObservers(lastKnowLocation);
    }

    public Location getLastKnowLocation() {
        return lastKnowLocation;
    }


    private class HereLocationListener implements android.location.LocationListener{

        @Override
        public void onLocationChanged(Location location) {
            updateLastKnowLocation(location);
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
    }
}
