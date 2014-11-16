package pl.wmaciejewski.twitmylocation.maps;

import android.graphics.Bitmap;
import android.location.Location;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Wojtek on 2014-11-16.
 */
public class MarkerBuilder {
    private BitmapDescriptor markerBitmap;
    private String markerTitle;


    public void setMarkerBitmap(Bitmap bitmap){
        markerBitmap=BitmapDescriptorFactory.fromBitmap(bitmap);
    }
    public void setMarkerTitle(String title){
        this.markerTitle=title;
    }

    public MarkerOptions createMarker(Location loc){
       return new MarkerOptions().position(new LatLng(loc.getLatitude(), loc.getLongitude())).title(markerTitle).icon(markerBitmap);
    }
}
