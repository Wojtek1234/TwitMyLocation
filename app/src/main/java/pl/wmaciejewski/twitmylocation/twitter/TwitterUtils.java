package pl.wmaciejewski.twitmylocation.twitter;

import android.content.SharedPreferences;

import java.util.List;

import oauth.signpost.OAuth;
import pl.wmaciejewski.twitmylocation.twitter.exception.LoginFailException;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class TwitterUtils {


    private static TwitterUtils instance;
    private String token,secret;
    private AccessToken accessToken;
    private Twitter twitter;


    public static TwitterUtils getInstance(){
        if(instance==null){
            instance=new TwitterUtils();

        }
        return  instance;
    }

    private TwitterUtils(){}

    public void authenticat(SharedPreferences prefs) throws LoginFailException {
         token = prefs.getString(OAuth.OAUTH_TOKEN, "");
         secret = prefs.getString(OAuth.OAUTH_TOKEN_SECRET, "");
         accessToken = new AccessToken(token,secret);
         twitter = new TwitterFactory().getInstance();
         twitter.setOAuthConsumer(Constants.CONSUMER_KEY, Constants.CONSUMER_SECRET);
         twitter.setOAuthAccessToken(accessToken);
        try {
            twitter.getAccountSettings();

        } catch (TwitterException e) {
           throw new LoginFailException(e.getMessage());
        }
    }


    public  void sendTweet(String msg) throws TwitterException {
        twitter.updateStatus(msg);
    }

    public List<Status> getTwitterStatusList(String searchHashTag) throws TwitterException {

        Query query = new Query(searchHashTag);
        QueryResult twitResult = requestTwittersQuery(query);
        return twitResult.getTweets();

    }

    private QueryResult requestTwittersQuery(Query query) throws TwitterException {
        QueryResult result = twitter.search(query);
        return result;

    }
}
