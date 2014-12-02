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


    public SendTwit(){

    }


    @Override
    protected StatusUpdate createStatusUpdate(String msg) {
        return   new StatusUpdate(msg);
    }
}
