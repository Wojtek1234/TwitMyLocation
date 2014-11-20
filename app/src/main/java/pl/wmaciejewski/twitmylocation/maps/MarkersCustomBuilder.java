package pl.wmaciejewski.twitmylocation.maps;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import pl.wmaciejewski.twitmylocation.R;
import pl.wmaciejewski.twitmylocation.twitter.TwitterUser;
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

    public  void  startToCreateTwitterMarker(View view, TwitterUser user) {
        if(user.getPhoto()!=null) new LoadPhoto(this,view).execute(user.getPhoto());
        else {
            this.photo= BitmapFactory.decodeResource(view.getResources(), R.drawable.zabka);
            View view1=null;
            getBitmap(this.view);
        }
    }

    private  Bitmap getBitmap(View view) {
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        return view.getDrawingCache();
    }



    @Override
    public void onGetPhoto(ArrayList<Bitmap> bitmap) {

    }
}
