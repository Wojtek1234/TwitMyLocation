package pl.wmaciejewski.twitmylocation.twitter.senders;

import twitter4j.StatusUpdate;

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
