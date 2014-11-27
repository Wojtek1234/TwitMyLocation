package pl.wmaciejewski.twitmylocation.twitter.senders;

import com.google.android.gms.maps.model.LatLng;

import java.io.File;

import pl.wmaciejewski.twitmylocation.twitter.TwitterUtils;
import twitter4j.GeoLocation;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;

/**
 * Created by Wojtek M on 2014-11-23.
 */
public class SendTwit extends AbstractSender{

    private final Twitter twitter= TwitterUtils.getInstance().getTwitter();
    public SendTwit(){

    }

    @Override
    public void sendTwit(String msg) throws TwitterException {
        StatusUpdate status = new StatusUpdate(msg);
        new UpdateStatus().execute(status);
    }
    @Override
    public void sendTwit(String msg,LatLng loc) throws TwitterException {
        new UpdateStatus().execute(new StatusUpdate(msg).location(new GeoLocation(loc.latitude,loc.longitude)));
    }
    @Override
    public void sendTwit(String msg,File file) throws TwitterException {
        StatusUpdate status = new StatusUpdate(msg);
        status.setMedia(file);
        new UpdateStatus().execute(status);
    }
    @Override
    public void sendTwit(String msg,File file,LatLng loc) throws TwitterException {
        StatusUpdate status = new StatusUpdate(msg);
        status.setMedia(file);
        status.location(new GeoLocation(loc.latitude,loc.longitude));
        new UpdateStatus().execute(status);
    }





}
