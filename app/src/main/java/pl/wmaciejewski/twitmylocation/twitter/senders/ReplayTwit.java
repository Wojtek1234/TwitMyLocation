package pl.wmaciejewski.twitmylocation.twitter.senders;

import com.google.android.gms.maps.model.LatLng;

import java.io.File;

import twitter4j.GeoLocation;
import twitter4j.StatusUpdate;
import twitter4j.TwitterException;

/**
 * Created by w.maciejewski on 2014-11-27.
 */
public class ReplayTwit extends AbstractSender {

    private long replay_id;

    public ReplayTwit(long replay_id) {
        this.replay_id = replay_id;
    }

    @Override
    public void sendTwit(String msg) throws TwitterException {
        StatusUpdate status = new StatusUpdate(msg);
        status.setInReplyToStatusId(replay_id);
        new UpdateStatus().execute(status);

    }

    @Override
    public void sendTwit(String msg, LatLng loc) throws TwitterException {
        StatusUpdate status =  new StatusUpdate(msg).location(new GeoLocation(loc.latitude,loc.longitude));
        status.setInReplyToStatusId(replay_id);
        new UpdateStatus().execute();
    }

    @Override
    public void sendTwit(String msg, File file) throws TwitterException {
        StatusUpdate status = new StatusUpdate(msg);
        status.setMedia(file);
        status.setInReplyToStatusId(replay_id);
        new UpdateStatus().execute(status);
    }

    @Override
    public void sendTwit(String msg, File file, LatLng loc) throws TwitterException {
        StatusUpdate status = new StatusUpdate(msg);
        status.setMedia(file);
        status.location(new GeoLocation(loc.latitude,loc.longitude));
        status.setInReplyToStatusId(replay_id);
        new UpdateStatus().execute(status);
    }
}
