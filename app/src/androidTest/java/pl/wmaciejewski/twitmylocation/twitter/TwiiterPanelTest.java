package pl.wmaciejewski.twitmylocation.twitter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import oauth.signpost.OAuth;
import pl.wmaciejewski.twitmylocation.R;
import pl.wmaciejewski.twitmylocation.twitter.exception.LoginFailException;

public class TwiiterPanelTest extends AndroidTestCase {

    private Context context= new RenamingDelegatingContext(getContext(), "test_");

    private  TwitterPanel twitterPanel;
    private  static  String test_name="kaczka";
    private  static String test_name2="kaczka";



    public void setUp() throws Exception {
        super.setUp();
        SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(context);
        LinearLayout ll= (LinearLayout)View.inflate(context, R.id.tweetingPanel,null);
        twitterPanel=new TwitterPanel(ll,prefs);
    }

    public void testSignUpButton(){
        Button loginButton=(Button)View.inflate(context, R.id.loginTwitButton,null);
        if(!twitterPanel.isLogged){
            assertEquals(loginButton.getText().toString(),context.getResources().getString(R.string.loginTwitText));
        }else{
            assertEquals(loginButton.getText().toString(),context.getResources().getString(R.string.logoutTwitText));
        }

    }

    public void testLoginClickInterface(){
        Button loginButton=(Button)View.inflate(context, R.id.loginTwitButton,null);
        twitterPanel.setOnTwittListener(new MockInterface() );
        loginButton.performClick();
        test_name=test_name+"1";
        assertEquals(test_name,test_name2);
    }

    public void testTwitClick(){
        Button twitButton=(Button)View.inflate(context,R.id.twitMyLocation,null);
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
        final String LOGGED_USER_TOKEN = "631325406-qtYfFm2hFebeQlRxIjUY8lK9uBAOzgQu1cloziUH";
        final String LOGGED_USER_SECRET = "Z7Ii7khJt3kzZLdscDqfsLb6jOWw0qwFljlAa5I";
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        final SharedPreferences.Editor edit = prefs.edit();
        edit.putString(OAuth.OAUTH_TOKEN, LOGGED_USER_TOKEN);
        edit.putString(OAuth.OAUTH_TOKEN_SECRET,LOGGED_USER_SECRET);
        edit.commit();
        TwitterUtils twitterUtils= TwitterUtils.getInstance();
        try {
            twitterUtils.authenticat(prefs);

        } catch (LoginFailException e) {
            e.printStackTrace();
        }
    }

    private class MockInterface implements TwitterPanel.TwitterListener{

        @Override
        public void onLogingDemand(Intent loggingIntent) {
            test_name2=test_name2+"1";
        }

        @Override
        public void onLogOutDemand() {

        }

        @Override
        public void onFindHashTag(Intent hashTagIntent) {

        }
    }
}