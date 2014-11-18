package pl.wmaciejewski.twitmylocation.bus;

import pl.wmaciejewski.twitmylocation.twitter.TwitterUser;

/**
 * Created by w.maciejewski on 2014-11-17.
 */
public class MessageLogin {


    private boolean logginStatus=false;
    private TwitterUser twitterUser;



    public MessageLogin(boolean logginStatus,TwitterUser twitterUser){
        this.logginStatus=logginStatus;
        this.twitterUser=twitterUser;

    }

    public boolean isLogginStatus() {
        return logginStatus;
    }
    public TwitterUser getTwitterUser() {
        return twitterUser;
    }




}
