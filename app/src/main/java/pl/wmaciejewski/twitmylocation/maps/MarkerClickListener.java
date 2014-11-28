package pl.wmaciejewski.twitmylocation.maps;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.HashMap;

import pl.wmaciejewski.twitmylocation.bus.BusProvider;
import pl.wmaciejewski.twitmylocation.bus.StatusForDialogEvent;
import twitter4j.Status;

/**
 * Created by w.maciejewski on 2014-11-21.
 */
public class MarkerClickListener implements GoogleMap.OnMarkerClickListener {
    private HashMap<Marker, Status> markerStatusHashMap;
    MarkerClickListener(HashMap<Marker, Status> markerStatusHashMap){

        this.markerStatusHashMap=markerStatusHashMap;
    }
    @Override
    public boolean onMarkerClick(Marker marker) {


        try{
            BusProvider.getInstance().post(new StatusForDialogEvent(marker,markerStatusHashMap.get(marker)));
        }catch(NullPointerException ne){
            ne.printStackTrace();
        }
        return false;
    }
}
