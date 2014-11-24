package pl.wmaciejewski.twitmylocation.bus;

import android.graphics.Bitmap;

/**
 * Created by Wojtek M on 2014-11-22.
 */
public class BitmapLoadedEvent {


    private final Bitmap bitmap;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public BitmapLoadedEvent(Bitmap bitmap){
        this.bitmap = bitmap;

    }
}
