package pl.wmaciejewski.twitmylocation.twitter;

import android.content.SharedPreferences;
import android.os.AsyncTask;

import java.util.List;
import java.util.Observable;

import pl.wmaciejewski.twitmylocation.MainActivity;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterUtils extends Observable{


    private static TwitterUtils instance;
    private String token, secret;
    private AccessToken accessToken;
    private TwitterUser user;



    public Twitter getTwitter() {
        return twitter;
    }

    private Twitter twitter;
    private boolean logged=false;



    public static TwitterUtils getInstance() {
        if (instance == null) {
            instance = new TwitterUtils();

        }
        return instance;
    }

    private TwitterUtils() {
    }

    public void authenticat(SharedPreferences prefs) {
        token = prefs.getString(MainActivity.PREF_KEY_OAUTH_TOKEN, "");
        secret = prefs.getString(MainActivity.PREF_KEY_OAUTH_SECRET, "");
        accessToken = new AccessToken(token, secret);
        TryToAutorize tryToAutorize = new TryToAutorize();
        tryToAutorize.execute(accessToken);
    }


    private void setUpTwitter(Twitter twitter, boolean failed){
            logged=!failed;
            this.twitter=twitter;
            setChanged();
            notifyObservers();

            //TODO change observer to BUS provider

    }

    public TwitterUser getUser() {
        return user;
    }


    public void sendTweet(String msg) throws TwitterException {
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

    public boolean isLogged() {
        return logged;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }

    public Twitter getBrandNewTwitter() {
        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
        configurationBuilder.setOAuthConsumerKey(Constants.CONSUMER_KEY);
        configurationBuilder.setOAuthConsumerSecret(Constants.CONSUMER_SECRET);
        Configuration configuration = configurationBuilder.build();

        return new TwitterFactory(configuration).getInstance();
    }


    private class TryToAutorize extends AsyncTask<AccessToken, Void, Twitter> {
         boolean failed=false;
        @Override
        protected Twitter doInBackground(AccessToken... params) {
            Twitter twitter = new TwitterFactory().getInstance();
            twitter.setOAuthConsumer(Constants.CONSUMER_KEY, Constants.CONSUMER_SECRET);
            twitter.setOAuthAccessToken(params[0]);
            try {
                twitter.getAccountSettings();
                user=new TwitterUser(twitter.showUser(twitter.getId()));
                return twitter;
            } catch (TwitterException e) {
                failed=true;
                user=new TwitterUser();
                return getBrandNewTwitter();
            }
        }
        @Override
        protected void onPostExecute(Twitter twitter) {
            setUpTwitter(twitter,failed);
        }
    }
}
