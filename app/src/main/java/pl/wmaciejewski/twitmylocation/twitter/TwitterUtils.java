package pl.wmaciejewski.twitmylocation.twitter;

import android.content.SharedPreferences;
import android.location.Location;
import android.os.AsyncTask;

import java.util.List;

import pl.wmaciejewski.twitmylocation.MainActivity;
import pl.wmaciejewski.twitmylocation.bus.BusProvider;
import pl.wmaciejewski.twitmylocation.bus.ListOfStatusEvent;
import pl.wmaciejewski.twitmylocation.bus.MessageLogin;
import twitter4j.GeoLocation;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterUtils {


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

            BusProvider.getInstance().post(new MessageLogin(logged,user));

    }

    public TwitterUser getUser() {
        return user;
    }


    public void sendTweet(String msg) throws TwitterException {
        twitter.updateStatus(msg);
    }
    public void sendTweetWithLocation(String msg,Location loc) throws TwitterException {
        twitter.updateStatus(new StatusUpdate(msg).location(new GeoLocation(loc.getLatitude(),loc.getLongitude())));
    }

    private List<Status> getTwitterStatusList(String searchHashTag) throws TwitterException {
        searchHashTag=searchHashTag.replace('#', ' ');
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

    private void presentListOfStatuses(List<Status> statuses) {
        BusProvider.getInstance().post(new ListOfStatusEvent(statuses));
    }

    public void getListHashTags(String string) {
        GetTwitterStatusesList getTwitterStatusesList=new GetTwitterStatusesList();
        getTwitterStatusesList.execute(string);
    }


    private class TryToAutorize extends AsyncTask<AccessToken, Void, Twitter> {
         boolean failed=false;
        @Override
        protected Twitter doInBackground(AccessToken... params) {
            Twitter twitter = getBrandNewTwitter();
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

    private class  GetTwitterStatusesList extends AsyncTask<String,Void,List<Status>>{
        @Override
        protected List<twitter4j.Status> doInBackground(String... strings) {
            try {
                return getTwitterStatusList(strings[0]);
            } catch (TwitterException e) {
                e.printStackTrace();
                return  null;
            }
        }
        @Override
        protected void onPostExecute(List<twitter4j.Status> statuses) {
            presentListOfStatuses(statuses);
        }
    }


}
