package pl.wmaciejewski.twitmylocation.maps;

import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import pl.wmaciejewski.twitmylocation.ListOfStatusHolder;
import pl.wmaciejewski.twitmylocation.R;
import pl.wmaciejewski.twitmylocation.bus.BitmapLoadedEvent;
import pl.wmaciejewski.twitmylocation.bus.BusProvider;
import pl.wmaciejewski.twitmylocation.bus.MarkerOptionsEvent;
import pl.wmaciejewski.twitmylocation.bus.MessageLogin;
import pl.wmaciejewski.twitmylocation.bus.ShowStatusEvent;
import pl.wmaciejewski.twitmylocation.bus.StatusForDialogEvent;
import pl.wmaciejewski.twitmylocation.sendtwitpackage.SetUpBundle;
import pl.wmaciejewski.twitmylocation.twitter.TwitterUser;
import twitter4j.Status;

/**
 * Created by w.maciejewski on 2014-11-13.
 */
public class MapPanel{
    private final GetLocation getLocation;
    private final View view;
    private MarkerBuilder markerBuilder;
    private Button finMeOnMap, placeMeOnMap,clearMap;
    private Bitmap profileImage;
    private MapsDrawer mapsDrawer;


    public MapPanel(View view, GoogleMap map) {
        this.view = view;
        markerBuilder = new MarkerBuilder(view);
        mapsDrawer=new MapsDrawer(markerBuilder,map);
        getLocation = new GetLocation((LocationManager)view.getContext().getSystemService(view.getContext().LOCATION_SERVICE));
        getLocation.addObserver(mapsDrawer);
        getLocation.setMapFirst();

        view.findViewById(R.id.findMeMap).setOnClickListener(new FinMeClick());
        view.findViewById(R.id.clearMarkers).setOnClickListener(new ClearClick());

    }


    public MapPanel(View view, GoogleMap map,TwitterUser twitterUser) {
        this.view = view;
        markerBuilder = new MarkerBuilder(view);
        mapsDrawer=new MapsDrawer(markerBuilder,map);
        getLocation = new GetLocation((LocationManager)view.getContext().getSystemService(view.getContext().LOCATION_SERVICE));
        getLocation.addObserver(mapsDrawer);
        getLocation.setMapFirst();

        view.findViewById(R.id.findMeMap).setOnClickListener(new FinMeClick());
        markerBuilder.updateUser(twitterUser);
    }

    public Location getCurrentLocation() {
        return mapsDrawer.getCurrentLocation();
    }

    @Subscribe
    public void answerComing(MessageLogin event){
        markerBuilder.updateUser(event.getTwitterUser());

    }


    public void doOnListOfStauses(List<Status> statuses){
        List<Status> statusesWithGeoTag = getStatusesWithGeoTag(statuses);
        mapsDrawer.clearMarkers();
        mapsDrawer.setStatusList(statusesWithGeoTag);
        new MarkersCustomBuilder(view,statusesWithGeoTag);
    }

    private List<Status> getStatusesWithGeoTag(List<Status> statuses) {
        List<Status> statusesWithGeoTag=new ArrayList<Status>();
        for(Status status:statuses) if(status.getGeoLocation()!=null) statusesWithGeoTag.add(status);
        return statusesWithGeoTag;
    }

    @Subscribe
    public void answerSetOfMarkers(MarkerOptionsEvent event){
        mapsDrawer.drawMultipleMarkers(event.getMarkerOptionses());
    }

    public Bitmap getProfileImage() {
        return profileImage;
    }

    @Subscribe
    public void answerBitmapGet(BitmapLoadedEvent event){
        profileImage =event.getBitmap();
        getLocation.getSinglePosition();
    }

    @Subscribe
    public void answerClickMarkerStatus(StatusForDialogEvent event){
        try{
            Bundle bundle=SetUpBundle.bundleForTwitDialog(event.getStatus());
            BusProvider.getInstance().post(new ShowStatusEvent(bundle));
        }catch (NullPointerException ne){
            Toast.makeText(view.getContext(),"It is your location, not a Twit :)",Toast.LENGTH_SHORT).show();
        }

    }


    public void showPanel() {
        if (this.view.findViewById(R.id.mapPanel).getVisibility() == View.GONE) this.view.findViewById(R.id.mapPanel).setVisibility(View.VISIBLE);
        else this.view.findViewById(R.id.mapPanel).setVisibility(View.GONE);
    }




    private class FinMeClick implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            getLocation.getSinglePosition();
        }
    }


    private class ClearClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            mapsDrawer.clearMarkers();
            view.findViewById(R.id.listButtonsPanel).setVisibility(View.GONE);
            ListOfStatusHolder.getInstance().clearStatusList();
        }
    }
}
