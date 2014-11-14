package pl.wmaciejewski.twitmylocation.twitter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.test.AndroidTestCase;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import oauth.signpost.OAuth;
import pl.wmaciejewski.twitmylocation.R;

public class TwiiterPanelTest extends AndroidTestCase {

    private Context context;

    private  TwitterPanel twitterPanel;
    private  static  String test_name="kaczka";
    private  static String test_name2="kaczka";
    private static String test_namel="baczka";
    private static String  test_namel2="baczka";
    private LinearLayout ll;


    public void setUp() throws Exception {
        super.setUp();
        test_name="kaczka";
        test_name2="kaczka";
        test_namel="baczka";
        test_namel2="baczka";
        context= getContext();
        SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(context);
        ll= (LinearLayout)View.inflate(context, R.layout.twiting_layout,null);
        twitterPanel=new TwitterPanel(ll,prefs);
    }

    public void testSignUpButton(){
        Button loginButton=(Button)ll.findViewById(R.id.loginTwitButton);
        if(!twitterPanel.isLogged()){
            assertEquals(loginButton.getText().toString(),context.getResources().getString(R.string.loginTwitText));
        }else{
            assertEquals(loginButton.getText().toString(),context.getResources().getString(R.string.logoutTwitText));
        }

    }

    public void testLoginClickInterface(){
        Button loginButton=(Button)ll.findViewById(R.id.loginTwitButton);
        twitterPanel.setOnTwittListener(new MockInterface() );
        loginButton.performClick();
        test_name=test_name+"1";
        assertEquals(test_name,test_name2);
    }

    public void testHashTagClickInterface(){
        Button hashTag=(Button)ll.findViewById(R.id.findByHash);
        twitterPanel.setOnTwittListener(new MockInterface() );
        hashTag.performClick();
        test_name=test_name+"1";
        assertEquals(test_name,test_name2);
    }

    public void testLogoutClickInterface(){
        Button logout=(Button)ll.findViewById(R.id.loginTwitButton);
        twitterPanel.setOnTwittListener(new MockInterface() );
        twitterPanel.setLogged(false);
        logout.performClick();
        test_namel=test_namel+"1";
        assertFalse(test_namel.equals(test_namel2));
        twitterPanel.setLogged(true);
        logout.performClick();
        assertTrue(test_namel.equals(test_namel2));
    }

    public void testTwitClick(){
        Button twitButton=(Button)ll.findViewById(R.id.twitMyLocation);
        assertTrue(twitButton.hasOnClickListeners());
    }


    public void tearDown() throws Exception {

    }

    private void clearCredentials() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        final SharedPreferences.Editor edit = prefs.edit();
        edit.remove(OAuth.OAUTH_TOKEN);
        edit.remove(OAuth.OAUTH_TOKEN_SECRET);
        edit.commit();
    }



    private void logginUser(SharedPreferences prefs) {
        final String LOGGED_USER_TOKEN = "2869153881-AaWTaeQHKvPBBGBp51l9UTmFmeSiPf6dtOTXWQX";
        final String LOGGED_USER_SECRET = "7bonzofF0HaSc5IwXGIzDXD1azovpQBWCuax15Vyl4qDZ";
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        final SharedPreferences.Editor edit = prefs.edit();
        edit.putString(OAuth.OAUTH_TOKEN, LOGGED_USER_TOKEN);
        edit.putString(OAuth.OAUTH_TOKEN_SECRET,LOGGED_USER_SECRET);
        edit.commit();
        TwitterUtils twitterUtils= TwitterUtils.getInstance();
        twitterUtils.authenticat(prefs);

    }

    private class MockInterface implements TwitterPanel.TwitterListener{

        @Override
        public void onLogingDemand(Intent loggingIntent) {
            test_name2=test_name2+"1";
        }

        @Override
        public void onLogOutDemand() {
            test_namel2=test_namel2+"1";
        }

        @Override
        public void onFindHashTag(Intent hashTagIntent) {
            test_name2=test_name2+"1";
        }
    }
}