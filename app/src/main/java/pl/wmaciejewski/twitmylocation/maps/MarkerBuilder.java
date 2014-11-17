package pl.wmaciejewski.twitmylocation.maps;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.AsyncTask;
import android.view.View;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import pl.wmaciejewski.twitmylocation.R;
import pl.wmaciejewski.twitmylocation.twitter.TwitterUser;

/**
 * Created by Wojtek on 2014-11-16.
 */
public class MarkerBuilder {
    private final View view;
    private BitmapDescriptor markerBitmap;
    private String markerTitle;
    private TwitterUser twitterUser;

    public MarkerBuilder(View view) {
        this.view = view;
        markerTitle="anonymus";
        setMarkerBitmap( BitmapFactory.decodeResource(view.getResources(), R.drawable.zabka));
    }

    private void setMarkerBitmap(Bitmap bitmap) {
        markerBitmap = BitmapDescriptorFactory.fromBitmap(bitmap);
    }


    public void setMarkerTitle(String title) {
        this.markerTitle = title;
    }


    public MarkerOptions createMarker(Location loc) {
        return new MarkerOptions().position(new LatLng(loc.getLatitude(), loc.getLongitude())).title(markerTitle).icon(markerBitmap);
    }

    public void updateUser(TwitterUser user) {
        markerTitle = user.getName();
        if(user.getPhoto()!=null) new LoadPhoto().execute(user.getPhoto());
        else  setMarkerBitmap( BitmapFactory.decodeResource(view.getResources(), R.drawable.zabka));



    }


    private class LoadPhoto extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                return Picasso.with(view.getContext()).load(params[0]).get();
            } catch (IOException e) {
                return BitmapFactory.decodeResource(view.getResources(), R.drawable.zabka);
            }catch(IllegalArgumentException ie){
                return BitmapFactory.decodeResource(view.getResources(), R.drawable.zabka);
            }
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            setMarkerBitmap(bitmap);
        }
    }


}
