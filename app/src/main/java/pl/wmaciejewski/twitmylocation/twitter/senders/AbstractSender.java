package pl.wmaciejewski.twitmylocation.twitter.senders;

import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;
import com.google.inject.Inject;

import java.io.File;

import pl.wmaciejewski.twitmylocation.twitter.TwitterUtils;
import twitter4j.GeoLocation;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;

/**
 * Created by w.maciejewski on 2014-11-27.
 */
public abstract class AbstractSender implements TwitSender {
    @Inject TwitterUtils twitterUtils;
    protected Twitter twitter= twitterUtils.getTwitter();
    protected abstract StatusUpdate createStatusUpdate(String msg);

    @Override
    public void sendTwit(String msg) throws TwitterException {
        StatusUpdate status =  createStatusUpdate(msg);
        new UpdateStatus().execute(status);
    }
    @Override
    public void sendTwit(String msg,LatLng loc) throws TwitterException {
        new UpdateStatus().execute(createStatusUpdate(msg).location(new GeoLocation(loc.latitude,loc.longitude)));
    }
    @Override
    public void sendTwit(String msg,File file) throws TwitterException {
        StatusUpdate status = createStatusUpdate(msg);
        status.setMedia(file);
        new UpdateStatus().execute(status);
    }
    @Override
    public void sendTwit(String msg,File file,LatLng loc) throws TwitterException {
        StatusUpdate status = createStatusUpdate(msg);
        status.setMedia(file);
        status.location(new GeoLocation(loc.latitude,loc.longitude));
        new UpdateStatus().execute(status);
    }
    @Override
    public void retwit(long id) throws TwitterException {
        RetwitStatus retwitStatus=new RetwitStatus();
        retwitStatus.execute(id);
    }
    protected class UpdateStatus extends AsyncTask<StatusUpdate,Void,Void> {


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

    protected class RetwitStatus extends AsyncTask<Long,Void,Void> {


        @Override
        protected Void doInBackground(Long... statusUpdates) {
            try {
                twitter.retweetStatus(statusUpdates[0]);
            } catch (TwitterException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
