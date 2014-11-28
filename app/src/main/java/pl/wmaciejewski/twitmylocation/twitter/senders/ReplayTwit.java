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
    protected StatusUpdate createStatusUpdate(String msg) {
        StatusUpdate status = new StatusUpdate(msg);
        status.setInReplyToStatusId(replay_id);
        return status;
    }
}
