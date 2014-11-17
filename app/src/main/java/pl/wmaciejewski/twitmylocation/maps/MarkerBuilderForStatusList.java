package pl.wmaciejewski.twitmylocation.maps;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

import pl.wmaciejewski.twitmylocation.R;

/**
 * Created by w.maciejewski on 2014-11-17.
 */
public class MarkerBuilderForStatusList {
    private View view;



    public MarkerBuilderForStatusList(View view,GoogleMap map){
        this.view=view;
    }



    private class LoadPhotos extends AsyncTask<String, Void, ArrayList<Bitmap>> {
        @Override
        protected ArrayList<Bitmap> doInBackground(String... params) {

            ArrayList<Bitmap> bitmaps = new ArrayList<Bitmap>();

            for (String string : params) {
                try {
                    bitmaps.add(Picasso.with(view.getContext()).load(params[0]).get());
                } catch (IOException e) {
                    bitmaps.add(BitmapFactory.decodeResource(view.getResources(), R.drawable.zabka));
                }
            }
            return bitmaps;

        }
    }
}
