package pl.wmaciejewski.twitmylocation.twitter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.test.AndroidTestCase;
import android.test.UiThreadTest;



import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import pl.wmaciejewski.twitmylocation.MainActivity;
import twitter4j.Twitter;

public class TwitterUtilsTest extends AndroidTestCase implements Observer {
    Context context=getContext();
    CountDownLatch signal;
    TwitterUtils twitterUtils= TwitterUtils.getInstance();
    public void setUp() throws Exception {

        twitterUtils.deleteObservers();
        context=getContext();
        twitterUtils.addObserver(this);
        super.setUp();
        signal = new CountDownLatch(1);
        logginUser();


    }

    /* Run only as separate test */
    @UiThreadTest
    public void testAuthenticat() throws Exception {
        twitterUtils.authenticat(PreferenceManager.getDefaultSharedPreferences(context));
        signal.await(30, TimeUnit.SECONDS);
        assertTrue( twitterUtils.isLogged());
    }

    public void tearDown() throws Exception {
        clearCredentials();
    }



    public void testSendTweet() throws Exception {

    }

    public void testGetTwitterStatusList() throws Exception {

    }





    private void clearCredentials() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        final SharedPreferences.Editor edit = prefs.edit();
        edit.remove(MainActivity.PREF_KEY_OAUTH_TOKEN);
        edit.remove(MainActivity.PREF_KEY_OAUTH_SECRET);
        edit.commit();
    }





    private void logginUser() {
        final String LOGGED_USER_TOKEN = "2869153881-AaWTaeQHKvPBBGBp51l9UTmFmeSiPf6dtOTXWQX";
        final String LOGGED_USER_SECRET = "7bonzofF0HaSc5IwXGIzDXD1azovpQBWCuax15Vyl4qDZ";
        SharedPreferences.Editor e = PreferenceManager.getDefaultSharedPreferences(context).edit();
        e.putString(MainActivity.PREF_KEY_OAUTH_TOKEN, LOGGED_USER_TOKEN);
        e.putString(MainActivity.PREF_KEY_OAUTH_SECRET, LOGGED_USER_SECRET);
        e.commit();

    }

    @Override
    public void update(Observable observable, Object data) {
        signal.countDown();
    }
}