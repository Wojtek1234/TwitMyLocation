package pl.wmaciejewski.twitmylocation.maps;

import android.location.Location;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import twitter4j.Status;

/**
 * Created by w.maciejewski on 2014-11-21.
 */
public class MapsDrawer implements Observer {
    private final MarkerBuilder markerBuilder;
    private GoogleMap mMap;
    private Marker lastMarker;
    private Location currentLocation;
    private List<MarkerOptions> markerOptionses;
    private List<Status> statuses;

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

    public void drawMultipleMarkers(List<MarkerOptions> markerOptionses){
        HashMap<Marker, Status> markerStatusHashMap=new HashMap<Marker, Status>();
        this.markerOptionses=markerOptionses;
        ArrayList<Marker> markers=new ArrayList<Marker>();
        for(int i=0;i<markerOptionses.size();i++){
            markers.add(i, mMap.addMarker(markerOptionses.get(i)));
            markerStatusHashMap.put( markers.get(i),statuses.get(i));

        }
        mMap.setOnMarkerClickListener(new MarkerClickListener(markerStatusHashMap));
        showAllMarkers(markers);
    }


    @Override
    public void update(Observable observable, Object o) {
        currentLocation = (Location)o;
        setUpMap(currentLocation);
    }

    public List<Status> getStatuses() {
        return statuses;
    }

    public List<MarkerOptions> getMarkerOptionses() {
        return markerOptionses;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void setStatusList(List<Status> statusList) {
        this.statuses = statusList;
    }
}
