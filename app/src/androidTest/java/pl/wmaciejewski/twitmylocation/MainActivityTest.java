package pl.wmaciejewski.twitmylocation;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.test.ActivityInstrumentationTestCase2;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by Wojtek on 2014-11-12.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private Activity activity;
    private Intent mStartIntent;
    private Configuration originalConfig; // Treat as read-only - test against for config changes
    private Configuration changeableConfig;
    private Resources instrumentResources;

    public MainActivityTest() {
        super(MainActivity.class);
    }


    @Override
    protected void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
        instrumentResources=getInstrumentation().getTargetContext().getResources();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();

    }

    public void testUI(){
        View mapFragment=activity.findViewById(R.id.mapFragment);
        assertNotNull(mapFragment);

        LinearLayout tweeterLayout= (LinearLayout) activity.findViewById(R.id.tweetingPanel);
        assertTrue(tweeterLayout.getVisibility()== View.GONE);

        LinearLayout mapLayout= (LinearLayout) activity.findViewById(R.id.mapPanel);
        assertTrue(mapLayout.getVisibility()== View.GONE);
    }

 /*   public void testOnClicSignUpButton(){
        Button loginButton= (Button) activity.findViewById(R.id.loginTwitButton);
        loginButton.performClick();
        final Intent launchIntent =getStartedActivityIntent() ;
        assertNotNull(launchIntent);
        assertEquals(launchIntent.getClass().getName(),RequestTokenActivity.class.getName());
    }*/



    public void testMenuTweetButton(){
        MenuItem twitItem= (MenuItem) activity.findViewById(R.id.twitAction);
        activity.onOptionsItemSelected(twitItem);
        LinearLayout tweeterLayout= (LinearLayout) activity.findViewById(R.id.tweetingPanel);
        assertTrue(tweeterLayout.getVisibility()== View.VISIBLE);
    }


    public void testMenuMapButton(){
        MenuItem twitItem= (MenuItem) activity.findViewById(R.id.mapAction);
        activity.onOptionsItemSelected(twitItem);
        LinearLayout mapLayout= (LinearLayout) activity.findViewById(R.id.mapPanel);
        assertTrue(mapLayout.getVisibility()== View.VISIBLE);
    }

 /*   public void testChangeOrientation(){
        changeableConfig.orientation = Configuration.ORIENTATION_LANDSCAPE; // Phone switches to landscape
        instrumentResources.updateConfiguration(changeableConfig, instrumentResources.getDisplayMetrics()); // Now the phone is "in landscape"
        assertEquals(activity.getResources().getConfiguration().orientation, Configuration.ORIENTATION_PORTRAIT); // The activity, however, should be in portrait still


    }*/
}
