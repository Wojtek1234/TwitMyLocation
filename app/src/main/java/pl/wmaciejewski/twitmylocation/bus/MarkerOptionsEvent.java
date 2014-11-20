package pl.wmaciejewski.twitmylocation.bus;

import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

/**
 * Created by w.maciejewski on 2014-11-20.
 */
public class MarkerOptionsEvent {
    private List<MarkerOptions> markerOptionses;

    public MarkerOptionsEvent(List<MarkerOptions> markerOptionsList){
        this.markerOptionses=markerOptionsList;
    }

    public List<MarkerOptions> getMarkerOptionses() {
        return markerOptionses;
    }
}
