package pl.wmaciejewski.twitmylocation.maps;

import android.location.Location;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by w.maciejewski on 2014-11-21.
 */
public class MapsDrawer implements Observer {
    private final MarkerBuilder markerBuilder;
    private GoogleMap mMap;

    private Marker lastMarker;
    private Location currentLocation;

    public MapsDrawer(MarkerBuilder markerBuilder,GoogleMap map){
        this.markerBuilder =markerBuilder;
        mMap=map;
    }

    private void setUpMap(Location loc) {
        if(lastMarker!=null) lastMarker.remove();
        lastMarker = mMap.addMarker(markerBuilder.createMarker(loc));
        centerMapOnMyLocation(loc);
    }



    private void centerMapOnMyLocation(Location loc) {
        if (loc != null) {
            LatLng myLocation = new LatLng(loc.getLatitude(),
                    loc.getLongitude());
            CameraUpdate cu = CameraUpdateFactory.newLatLngZoom(myLocation, 16);
            mMap.animateCamera(cu);
        }
    }

    private void showAllMarkers(List<Marker> markerList) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Marker marker : markerList) {
            builder.include(marker.getPosition());
        }
        LatLngBounds bounds = builder.build();
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 16);
        mMap.animateCamera(cu);
    }


    @Override
    public void update(Observable observable, Object o) {
        currentLocation = (Location)o;
        setUpMap(currentLocation);
    }



    public Location getCurrentLocation() {
        return currentLocation;
    }
}
