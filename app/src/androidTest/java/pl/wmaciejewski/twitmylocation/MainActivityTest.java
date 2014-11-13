package pl.wmaciejewski.twitmylocation;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.test.ActivityUnitTestCase;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import pl.wmaciejewski.twitmylocation.twitter.RequestTokenActivity;

/**
 * Created by Wojtek on 2014-11-12.
 */
public class MainActivityTest extends ActivityUnitTestCase<MainActivity> {

    private MainActivity activity;

    public MainActivityTest(Class<MainActivity> activityClass) {
        super(activityClass);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Intent intent = new Intent(getInstrumentation().getTargetContext(),
                MainActivity.class);
        startActivity(intent, null, null);
        activity = getActivity();
    }


    public void testUI(){
        Fragment mapFragment=activity.findViewById(R.id.mapFragment);
        assertNotNull(mapFragment);

        LinearLayout tweeterLayout=activity.findViewById(R.id.tweetingPanel);
        assertTrue(tweeterLayout.getVisibility()== View.GONE);

        LinearLayout mapLayout=activity.findViewById(R.id.mapPanel);
        assertTrue(mapLayout.getVisibility()== View.GONE);
    }

    public void testOnClicSignUpButton(){
        Button loginButton=activity.findViewById(R.id.loginButton);
        loginButton.performClick();
        final Intent launchIntent = getStartedActivityIntent();
        assertNotNull(launchIntent);
        assertEquals(launchIntent.getClass().getName(),RequestTokenActivity.class.getName());
    }



    public void testMenuTweetButton(){
        MenuItem twitItem=activity.findViewById(R.menu.twitAction);
        activity.onOptionsItemSelected(twitItem);
        LinearLayout tweeterLayout=activity.findViewById(R.id.tweetingPanel);
        assertTrue(tweeterLayout.getVisibility()== View.VISIBLE);
    }


    public void testMenuMapButton(){
        MenuItem twitItem=activity.findViewById(R.menu.mapAction);
        activity.onOptionsItemSelected(twitItem);
        LinearLayout mapLayout=activity.findViewById(R.id.mapPanel);
        assertTrue(mapLayout.getVisibility()== View.VISIBLE);
    }
}
