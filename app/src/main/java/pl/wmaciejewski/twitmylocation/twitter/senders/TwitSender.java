package pl.wmaciejewski.twitmylocation.twitter.senders;

import com.google.android.gms.maps.model.LatLng;

import java.io.File;

import twitter4j.TwitterException;

/**
 * Created by w.maciejewski on 2014-11-27.
 */
public interface TwitSender {

    public void sendTwit(String msg) throws TwitterException;
    public void sendTwit(String msg,LatLng loc) throws TwitterException;
    public void sendTwit(String msg,File file) throws TwitterException;
    public void sendTwit(String msg,File file,LatLng loc) throws TwitterException;
    public void retwit(long id)  throws TwitterException ;
}
