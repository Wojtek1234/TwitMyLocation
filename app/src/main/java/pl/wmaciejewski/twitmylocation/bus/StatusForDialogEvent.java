package pl.wmaciejewski.twitmylocation.bus;

import com.google.android.gms.maps.model.Marker;

import twitter4j.Status;

/**
 * Created by w.maciejewski on 2014-11-21.
 */
public class StatusForDialogEvent  {

    private final Marker marker;
    private final Status status;


    public StatusForDialogEvent(Marker marker, Status status) {
        this.marker = marker;
        this.status = status;
    }

    public Marker getMarker() {
        return marker;
    }

    public Status getStatus() {
        return status;
    }

}
