package pl.wmaciejewski.twitmylocation.maps;

import android.location.Location;
import android.location.LocationManager;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.squareup.otto.Subscribe;

import pl.wmaciejewski.twitmylocation.R;
import pl.wmaciejewski.twitmylocation.bus.MessageLogin;

/**
 * Created by w.maciejewski on 2014-11-13.
 */
public class MapPanel{
    private final GetLocation getLocation;
    private final View view;
    private Location currentLocation;
    private MarkerBuilder markerBuilder;
    Button finMeOnMap, placeMeOnMap;
    private GoogleMap mMap;
    private Marker lastMarker;
    private MapsDrawer mapsDrawer;

    public MapPanel(View view, GoogleMap map) {
        this.view = view;
        mMap = map;
        getLocation = new GetLocation((LocationManager)view.getContext().getSystemService(view.getContext().LOCATION_SERVICE));
        markerBuilder = new MarkerBuilder(view);
        mapsDrawer=new MapsDrawer(markerBuilder,map);
        getLocation.addObserver(mapsDrawer);
        view.findViewById(R.id.findMeMap).setOnClickListener(new FinMeClick());
    }

    public Location getCurrentLocation() {
        return mapsDrawer.getCurrentLocation();
    }


    @Subscribe
    public void answerComing(MessageLogin event){
        markerBuilder.updateUser(event.getTwitterUser());
        getLocation.getSinglePosition();
    }


    public void showPanel() {
        if (this.view.getVisibility() == View.GONE) this.view.setVisibility(View.VISIBLE);
        else this.view.setVisibility(View.GONE);
    }



    private class FinMeClick implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            getLocation.getSinglePosition();
        }
    }




}
