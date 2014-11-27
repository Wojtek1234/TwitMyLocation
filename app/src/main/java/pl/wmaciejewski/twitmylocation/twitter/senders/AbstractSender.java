package pl.wmaciejewski.twitmylocation.twitter.senders;

import android.os.AsyncTask;

import pl.wmaciejewski.twitmylocation.twitter.TwitterUtils;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;

/**
 * Created by w.maciejewski on 2014-11-27.
 */
public abstract class AbstractSender implements TwitSender {
    protected Twitter twitter= TwitterUtils.getInstance().getTwitter();

    @Override
    public void retwit(long id) throws TwitterException {
        twitter.retweetStatus(id);
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
}
