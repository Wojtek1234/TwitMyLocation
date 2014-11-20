package pl.wmaciejewski.twitmylocation.maps;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

import pl.wmaciejewski.twitmylocation.R;

/**
 * Created by w.maciejewski on 2014-11-20.
 */
public class LoadPhoto extends AsyncTask<String, Void, ArrayList<Bitmap>> {

    private View view;
    private GetPhoto getPhoto;

    public  LoadPhoto(GetPhoto getPhoto,View view){
        this.getPhoto=getPhoto;
        this.view=view;
    }
    @Override
    protected ArrayList<Bitmap> doInBackground(String... params) {
        ArrayList<Bitmap> bitmaps=new ArrayList<Bitmap>();
        for(int i=0;i<params.length;i++){
            try {
                bitmaps.add(Picasso.with(view.getContext()).load(params[0]).get());
            } catch (IOException e) {
                bitmaps.add(BitmapFactory.decodeResource(view.getResources(), R.drawable.zabka));
            }catch(IllegalArgumentException ie){
                bitmaps.add (BitmapFactory.decodeResource(view.getResources(), R.drawable.zabka));
            }
        }

        return bitmaps;
    }

    @Override
    protected void onPostExecute(ArrayList<Bitmap> bitmap) {
        getPhoto.onGetPhoto(bitmap);
    }

    public interface GetPhoto{
        public void onGetPhoto(ArrayList<Bitmap> bitmap);
    }
}