package pl.wmaciejewski.twitmylocation.maps;

import android.location.Location;
import android.location.LocationManager;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.GoogleMap;
import com.squareup.otto.Subscribe;

import java.util.List;

import pl.wmaciejewski.twitmylocation.R;
import pl.wmaciejewski.twitmylocation.bus.BitmapLoadedEvent;
import pl.wmaciejewski.twitmylocation.bus.ListOfStatusEvent;
import pl.wmaciejewski.twitmylocation.bus.MarkerOptionsEvent;
import pl.wmaciejewski.twitmylocation.bus.MessageLogin;
import pl.wmaciejewski.twitmylocation.bus.StatusForDialogEvent;
import twitter4j.Status;

/**
 * Created by w.maciejewski on 2014-11-13.
 */
public class MapPanel{
    private final GetLocation getLocation;
    private final View view;
    private MarkerBuilder markerBuilder;
    private Button finMeOnMap, placeMeOnMap;
    private MapsDrawer mapsDrawer;
    private List<Status> statusList;


    public MapPanel(View view, GoogleMap map) {
        this.view = view;
        markerBuilder = new MarkerBuilder(view);
        mapsDrawer=new MapsDrawer(markerBuilder,map);
        getLocation = new GetLocation((LocationManager)view.getContext().getSystemService(view.getContext().LOCATION_SERVICE));
        getLocation.addObserver(mapsDrawer);
        getLocation.setMapFirst();

        view.findViewById(R.id.findMeMap).setOnClickListener(new FinMeClick());
    }

    public Location getCurrentLocation() {
        return mapsDrawer.getCurrentLocation();
    }

    @Subscribe
    public void answerComing(MessageLogin event){
        markerBuilder.updateUser(event.getTwitterUser());

    }

    @Subscribe
    public void answerStatusList(ListOfStatusEvent listOfStatusEvent){
        mapsDrawer.setStatusList(listOfStatusEvent.getStatusList());
        new MarkersCustomBuilder(view,statusList);
    }

    @Subscribe
    public void answerSetOfMarkers(MarkerOptionsEvent event){
        mapsDrawer.drawMultipleMarkers(event.getMarkerOptionses());
    }

    @Subscribe
    public void answerBitmapGet(BitmapLoadedEvent event){
        getLocation.getSinglePosition();
    }

    @Subscribe
    public void answerClickMarkerStatus(StatusForDialogEvent event){
        Status status=event.getStatus();
        mapsDrawer.getMarkerOptionses().get(mapsDrawer.getStatuses().indexOf(status));//Straszna ta konstrukcja wim, ale nic innego nie wymyśliłem
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
