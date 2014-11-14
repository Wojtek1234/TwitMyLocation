package pl.wmaciejewski.twitmylocation.maps;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.GoogleMap;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by w.maciejewski on 2014-11-13.
 */
public class MapPanel implements Observer {
    private final GetLocation getLocation;
    private final View view;
    private Location currentLocation;
    Button finMeOnMap,placeMeOnMap;
    private GoogleMap mMap;

    public MapPanel(View view, GoogleMap map){
        this.view=view;
        mMap=map;
        getLocation=new GetLocation((LocationManager) view.getContext().getSystemService(Context.LOCATION_SERVICE) );
        new SetUpMarkerPosition(mMap).execute(getLocation.getLastKnowLocation());

    }


    public Location getCurrentLocation() {
        return currentLocation;
    }

    @Override
    public void update(Observable observable, Object o) {
        currentLocation=(Location)o;
    }



    public void showPanel(){
        if(this.view.getVisibility()==View.GONE) this.view.setVisibility(View.VISIBLE);
        else this.view.setVisibility(View.GONE);

    }




}
