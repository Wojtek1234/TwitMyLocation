package pl.wmaciejewski.twitmylocation.maps;

import android.location.Location;
import android.view.View;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by w.maciejewski on 2014-11-13.
 */
public class MapPanel implements Observer {
    private Location currentLocation;
    public MapPanel(View view){

    }


    public Location getCurrentLocation() {
        return currentLocation;
    }

    @Override
    public void update(Observable observable, Object o) {
        currentLocation=(Location)o;
    }
}
