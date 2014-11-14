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
    private HereLocationListener locationListener;



    private Location lastKnowLocation;

    public GetLocation(LocationManager locationManager){

        Criteria crit = getCriteria();
        this.locationManager= locationManager;
        String bestProvider = locationManager.getBestProvider(crit, false);
        lastKnowLocation=locationManager.getLastKnownLocation(bestProvider);
        this.locationManager.requestLocationUpdates(bestProvider,5,10,new HereLocationListener() );
    }

    private Criteria getCriteria() {
        Criteria crit = new Criteria();
        crit.setAccuracy(Criteria.ACCURACY_FINE);
        crit.setPowerRequirement(Criteria.POWER_LOW);
        return crit;
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
