package pl.wmaciejewski.twitmylocation.maps;

import android.location.Location;
import android.os.AsyncTask;

import com.google.android.gms.maps.GoogleMap;



/**
 * Created by w.maciejewski on 2014-11-14.
 */
public class SetUpMarkerPosition extends AsyncTask<Location,Void,Void> {

    private GoogleMap mMap;
    SetUpMarkerPosition(GoogleMap map){
        mMap=map;
    }
    @Override
    protected Void doInBackground(Location... locations) {

        return null;
    }


}
