package pl.wmaciejewski.twitmylocation.maps;

import android.location.Location;
import android.location.LocationManager;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import pl.wmaciejewski.twitmylocation.R;
import pl.wmaciejewski.twitmylocation.twitter.TwitterUtils;

/**
 * Created by w.maciejewski on 2014-11-13.
 */
public class MapPanel implements Observer {
    private final GetLocation getLocation;
    private final View view;
    private Location currentLocation;
    private MarkerBuilder markerBuilder;
    Button finMeOnMap, placeMeOnMap;
    private GoogleMap mMap;
    private Marker lastMarker;

    public MapPanel(View view, GoogleMap map) {
        TwitterUtils.getInstance().addObserver(this);
        this.view = view;
        mMap = map;
        getLocation = new GetLocation((LocationManager)view.getContext().getSystemService(view.getContext().LOCATION_SERVICE));
        getLocation.addObserver(this);
        markerBuilder = new MarkerBuilder(view);
        view.findViewById(R.id.findMeMap).setOnClickListener(new FinMeClick());
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof TwitterUtils) {
            markerBuilder.updateUser(((TwitterUtils) observable).getUser());
            getLocation.getSinglePosition();
        } else {
            currentLocation = (Location)o;
            setUpMap(currentLocation);
        }
    }
    public void showPanel() {
        if (this.view.getVisibility() == View.GONE) this.view.setVisibility(View.VISIBLE);
        else this.view.setVisibility(View.GONE);
    }

    private void setUpMap(Location loc) {
        try {
            lastMarker.remove();
        } catch (NullPointerException ne) {

        }
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


    private class FinMeClick implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            getLocation.getSinglePosition();
        }
    }




}
