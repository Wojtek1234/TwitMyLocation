package pl.wmaciejewski.twitmylocation.maps;

import android.graphics.Bitmap;
import android.view.View;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import pl.wmaciejewski.twitmylocation.bus.BusProvider;
import pl.wmaciejewski.twitmylocation.bus.MarkerOptionsEvent;
import twitter4j.Status;

/**
 * Created by w.maciejewski on 2014-11-20.
 */
public class MarkersCustomBuilder implements LoadPhoto.GetPhoto{


    private View view;
    private Bitmap photo;
    private List<Status> statusList;
    private List<MarkerOptions> markerOptionses;

    public MarkersCustomBuilder(View view,List<Status> statusList){
        this.view=view;
        this.statusList=statusList;
        String[] strings=new String[statusList.size()];
        for(int i=0;i<strings.length;i++){
            strings[i]=statusList.get(i).getUser().getProfileImageURL();
        }
        new LoadPhoto(this,view).execute(strings);
    }


    private  void  createSetOfMarkers(ArrayList<Bitmap> bitmaps){

        ArrayList<MarkerOptions> markerOptionses=new ArrayList<MarkerOptions>();
        for(int i=0;i<statusList.size();i++){
            Status status=statusList.get(i);
            Bitmap bitmap=bitmaps.get(i);
            LatLng loc=new LatLng(status.getGeoLocation().getLatitude(),status.getGeoLocation().getLongitude());
            String title=status.getUser().getName();
            markerOptionses.add(createMarker(loc,title,bitmap));
        }

        BusProvider.getInstance().post(new MarkerOptionsEvent(markerOptionses));

    }


    private MarkerOptions createMarker(LatLng loc,String title,Bitmap markerBitmap) {
        return new MarkerOptions().position(loc).title(title).icon(setMarkerBitmap(markerBitmap));
    }

    private BitmapDescriptor setMarkerBitmap(Bitmap bitmap) {
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }




    @Override
    public void onGetPhoto(ArrayList<Bitmap> bitmap) {
        createSetOfMarkers(bitmap);
    }
}
