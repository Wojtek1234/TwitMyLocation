package pl.wmaciejewski.twitmylocation;

import android.app.Instrumentation;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;

import pl.wmaciejewski.twitmylocation.twitter.RequestTokenActivity;

/**
 * Created by Wojtek on 2014-11-12.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private MainActivity activity;


    public MainActivityTest() {
        super(MainActivity.class);
    }


    @Override
    protected void setUp() throws Exception {
        super.setUp();
        activity = getActivity();

    }

    @Override
    protected void tearDown() throws Exception {

        activity.finish();
        super.tearDown();
    }

    public void testUI(){
        View mapFragment=activity.findViewById(R.id.mapFragment);
        assertNotNull(mapFragment);

        LinearLayout tweeterLayout= (LinearLayout) activity.findViewById(R.id.tweetingPanel);
        assertTrue(tweeterLayout.getVisibility()== View.GONE);

        LinearLayout mapLayout= (LinearLayout) activity.findViewById(R.id.mapPanel);
        assertTrue(mapLayout.getVisibility()== View.GONE);

        assertNotNull(activity.findViewById(R.id.findByHash));
        assertNotNull(activity.findViewById(R.id.findMeMap));
        assertNotNull(activity.findViewById(R.id.loginTwitButton));
        assertNotNull(activity.findViewById(R.id.twitMyLocation));
        assertNotNull(activity.findViewById(R.id.placeMeOnMap));



    }


/*    public void testInterfaceFunction(){
        Intent intent= new Intent(activity, RequestTokenActivity.class);
        Instrumentation.ActivityMonitor am = getInstrumentation().addMonitor(RequestTokenActivity.class.getName(), null, false);
        activity.onLogingDemand(intent);

        RequestTokenActivity requestTokenActivity =(RequestTokenActivity)am.waitForActivityWithTimeout( 1000);
        assertNotNull(requestTokenActivity);
        assertEquals(RequestTokenActivity.class,requestTokenActivity.getClass());
        requestTokenActivity.finish();
        getInstrumentation().removeMonitor(am);


    }*/




    public void testMenuButtons(){

        getInstrumentation().sendKeyDownUpSync(KeyEvent.KEYCODE_MENU);
        getInstrumentation().invokeMenuActionSync(activity, R.id.twitAction, 0);

        LinearLayout tweeterLayout= (LinearLayout) activity.findViewById(R.id.tweetingPanel);
        assertTrue(tweeterLayout.getVisibility()== View.VISIBLE);
        getInstrumentation().invokeMenuActionSync(activity, R.id.mapAction, 0);
        LinearLayout mapLayout= (LinearLayout) activity.findViewById(R.id.mapPanel);
        assertTrue(mapLayout.getVisibility()== View.VISIBLE);
    }





}
