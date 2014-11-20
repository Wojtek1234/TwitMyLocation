package pl.wmaciejewski.twitmylocation.twitter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import android.test.UiThreadTest;

import com.squareup.otto.Subscribe;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import pl.wmaciejewski.twitmylocation.MainActivity;
import pl.wmaciejewski.twitmylocation.R;
import pl.wmaciejewski.twitmylocation.bus.BusProvider;
import pl.wmaciejewski.twitmylocation.bus.ListOfStatusEvent;
import pl.wmaciejewski.twitmylocation.bus.MessageLogin;
import twitter4j.Status;

public class TwitterUtilsTest extends AndroidTestCase   {
    private static final String TEST_TWIT=" TEST_TWIT";
    Context context;
    CountDownLatch signal,signal2;
    private List<Status> statuses;
    TwitterUtils twitterUtils= TwitterUtils.getInstance();
    public void setUp() throws Exception {
        BusProvider.getInstance().register(this);
        context=new RenamingDelegatingContext(getContext(), "test_Utils_");;
        super.setUp();
        signal = new CountDownLatch(1);
        signal2= new CountDownLatch(1);
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
    /* Test pass on debug ...*/
    @UiThreadTest
    public void testGetTwitterStatusList() throws Exception {
        Random r = new Random();
        char c = (char)(r.nextInt(26) + 'a');
        String hastag=context.getResources().getString(R.string.programHashTag)+c;
        twitterUtils.sendTweet(hastag+TEST_TWIT);
        Thread.sleep(15000);
        twitterUtils.getListHashTags(hastag);
        signal2.await(30, TimeUnit.SECONDS);
        assertTrue(statuses.size()==1);
        assertEquals(statuses.get(0).getText(),hastag+TEST_TWIT);
        twitterUtils.getTwitter().destroyStatus(statuses.get(0).getId());
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

    @Subscribe
    public void answerAvailable(ListOfStatusEvent event) {
        statuses=event.getStatusList();
        signal2.countDown();

    }

    @Subscribe
    public void answerAvailable2(MessageLogin event) {
        signal.countDown();
    }
}