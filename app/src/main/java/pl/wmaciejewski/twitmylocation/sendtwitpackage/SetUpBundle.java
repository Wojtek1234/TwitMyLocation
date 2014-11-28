package pl.wmaciejewski.twitmylocation.sendtwitpackage;

import android.graphics.Bitmap;
import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;

import java.io.ByteArrayOutputStream;

import pl.wmaciejewski.twitmylocation.twitter.dialog.TwitDialog;
import twitter4j.Status;

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
    public static Bundle setBundle(LatLng latLng, Bitmap bitmap,String name,String text) {
        Bundle outState=new Bundle();
        outState.putString(SendTwitActivity.NAME_PROPOERTY, name);
        outState.putDouble(SendTwitActivity.LATITUDE_PROPERTY, latLng.latitude);
        outState.putDouble(SendTwitActivity.LONGITUDE_PROPERTY,latLng.longitude);
        outState.putString(SendTwitActivity.EDIT_TEXT_TEXT,text);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        outState.putByteArray(SendTwitActivity.BITMAP_PROPOERTY, byteArray);

        return outState;
    }

    public static Bundle setBundle(LatLng latLng, Bitmap bitmap,String name,String text,long id) {
        Bundle outState=new Bundle();
        outState.putString(SendTwitActivity.NAME_PROPOERTY, name);
        outState.putDouble(SendTwitActivity.LATITUDE_PROPERTY, latLng.latitude);
        outState.putDouble(SendTwitActivity.LONGITUDE_PROPERTY,latLng.longitude);
        outState.putString(SendTwitActivity.EDIT_TEXT_TEXT,text);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        outState.putByteArray(SendTwitActivity.BITMAP_PROPOERTY, byteArray);

        return outState;
    }

    public static Bundle bundleForTwitDialog(Status status){
        Bundle outState=new Bundle();
        outState.putString(TwitDialog.TWIT_USERNAME,status.getUser().getName());
        outState.putString(TwitDialog.TWIT_DATE,status.getCreatedAt().toString());
        outState.putString(TwitDialog.TWIT_IMAGEURL,status.getUser().getBiggerProfileImageURL());
        outState.putLong(TwitDialog.TWIT_ID, status.getId());
        outState.putString(TwitDialog.TWIT_TEXT,status.getText());
        if(status.getMediaEntities().length>0)  outState.putString(TwitDialog.TWIT_IMAGE,status.getMediaEntities()[0].getMediaURL());
        return outState;
    }
}
