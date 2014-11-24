package pl.wmaciejewski.twitmylocation.sendtwitpackage;

import android.graphics.Bitmap;
import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;

import java.io.ByteArrayOutputStream;

/**
 * Created by w.maciejewski on 2014-11-24.
 */
public class SetUpBundle {

    public static Bundle setBundle(LatLng latLng, Bitmap bitmap,String name) {
        Bundle outState=new Bundle();
        outState.putString(SendTwitActivity.NAME_PROPOERTY, name);
        outState.putDouble(SendTwitActivity.LATITUDE_PROPERTY,latLng.latitude);
        outState.putDouble(SendTwitActivity.LONGITUDE_PROPERTY,latLng.longitude);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        outState.putByteArray(SendTwitActivity.BITMAP_PROPOERTY, byteArray);
        return outState;
    }
}
