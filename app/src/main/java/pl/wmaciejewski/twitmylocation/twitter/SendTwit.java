package pl.wmaciejewski.twitmylocation.twitter;

import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;

import java.io.File;

import twitter4j.GeoLocation;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;

/**
 * Created by Wojtek M on 2014-11-23.
 */
public class SendTwit {

    private final Twitter twitter=TwitterUtils.getInstance().getTwitter();


    public SendTwit(){

    }


    public void sendTwit(String msg) throws TwitterException {
        StatusUpdate status = new StatusUpdate(msg);
        new UpdateStatus().execute(status);
    }

    public void sendTwit(String msg,LatLng loc) throws TwitterException {
        new UpdateStatus().execute(new StatusUpdate(msg).location(new GeoLocation(loc.latitude,loc.longitude)));
    }
    public void sendTwit(String msg,File file) throws TwitterException {
        StatusUpdate status = new StatusUpdate(msg);
        status.setMedia(file);
        new UpdateStatus().execute(status);
    }
    public void sendTwit(String msg,File file,LatLng loc) throws TwitterException {

        StatusUpdate status = new StatusUpdate(msg);
        status.setMedia(file);
        status.location(new GeoLocation(loc.latitude,loc.longitude));
        new UpdateStatus().execute(status);

    }

    public void retwit(long id) throws TwitterException {
        twitter.retweetStatus(id);
    }

    private class UpdateStatus extends AsyncTask<StatusUpdate,Void,Void>{


        @Override
        protected Void doInBackground(StatusUpdate... statusUpdates) {
            try {
                twitter.updateStatus(statusUpdates[0]);
            } catch (TwitterException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
