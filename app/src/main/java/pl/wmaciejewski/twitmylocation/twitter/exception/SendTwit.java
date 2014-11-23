package pl.wmaciejewski.twitmylocation.twitter.exception;

import android.location.Location;

import java.io.File;

import twitter4j.GeoLocation;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;

/**
 * Created by Wojtek M on 2014-11-23.
 */
public class SendTwit {

    private final Twitter twitter;


    public SendTwit(Twitter twitter) {
        this.twitter = twitter;
    }

    public void sendTwit(String msg) throws TwitterException {
        twitter.updateStatus(msg);
    }
    public void sendTwit(String msg,Location loc) throws TwitterException {
        twitter.updateStatus(new StatusUpdate(msg).location(new GeoLocation(loc.getLatitude(),loc.getLongitude())));
    }
    public void sendTwit(String msg,File file) throws TwitterException {

        StatusUpdate status = new StatusUpdate(msg);
        status.setMedia(file);
        twitter.updateStatus(status);
    }
    public void sendTwit(String msg,File file,Location loc) throws TwitterException {

        StatusUpdate status = new StatusUpdate(msg);
        status.setMedia(file);
        status.location(new GeoLocation(loc.getLatitude(),loc.getLongitude()));
        twitter.updateStatus(status);
    }

}
