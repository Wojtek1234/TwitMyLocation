package pl.wmaciejewski.twitmylocation.maps;

import android.location.Criteria;
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

    public GetLocation(LocationManager locationManager) {
        Criteria criteria=new Criteria();
        String bestProvider =locationManager.getBestProvider(criteria,false);
        this.locationManager = locationManager;
        String provider = locationManager.GPS_PROVIDER;
        hereLocationListener = new HereLocationListener();
        updateLastKnowLocation(locationManager.getLastKnownLocation(provider));


        for (String provider1 : locationManager.getAllProviders()) {

            updateLastKnowLocation(locationManager.getLastKnownLocation(provider1));
        }


        provider = locationManager.GPS_PROVIDER;
        this.locationManager.requestLocationUpdates(provider, 5, 10, new HereLocationListener());
    }

    public void setMapFirst() {
        if (lastKnowLocation != null) updateLastKnowLocation(lastKnowLocation);

    }

    public void getSinglePosition() {
        setMapFirst();
        this.locationManager.requestSingleUpdate(locationManager.GPS_PROVIDER, hereLocationListener, null);
    }


    public void updateLastKnowLocation(Location location) {
        if (lastKnowLocation == null) {
            lastKnowLocation = location;
            setChanged();
            notifyObservers(lastKnowLocation);
            return;
        }
        if(location!=null){
            if (location.getTime() - lastKnowLocation.getTime() > 0) {
                lastKnowLocation = location;

            }
            setChanged();
            notifyObservers(lastKnowLocation);
        }


    }

    public Location getLastKnowLocation() {
        return lastKnowLocation;
    }


    private class HereLocationListener implements android.location.LocationListener {

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
