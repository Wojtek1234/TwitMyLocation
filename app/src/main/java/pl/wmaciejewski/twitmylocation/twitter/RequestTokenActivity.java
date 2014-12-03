package pl.wmaciejewski.twitmylocation.twitter;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.inject.Inject;

import pl.wmaciejewski.twitmylocation.MainActivity;
import roboguice.activity.RoboActivity;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;


public class RequestTokenActivity extends RoboActivity {
    public static final int WEBVIEW_REQUEST_CODE = 254;
    public static final int WEBVIEW_REQUEST_LOGGED = 255;
    public static final int WEBVIEW_REQUEST_NOTLOGGED=256;
    private RequestToken requestToken;

    @Inject TwitterUtils twitterUtils;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Twitter twitter =twitterUtils.getTwitter();
        RetrieveResultTokenTask retrieveAccessTokenTask = new RetrieveResultTokenTask();
        retrieveAccessTokenTask.execute(twitter);

    }

    public void startURLActivity(RequestToken requestToken) {
        try {
            //TODO http://javatechig.com/android/how-to-integrate-twitter-in-android-application
            this.requestToken = requestToken;
            final Intent intent = new Intent(this, WebViewActivity.class);
            intent.putExtra(WebViewActivity.EXTRA_URL, requestToken.getAuthenticationURL());
            startActivityForResult(intent, WEBVIEW_REQUEST_CODE);
        } catch (NullPointerException ne) {
            //TODO dialog z fuckupem
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == this.WEBVIEW_REQUEST_CODE) {
            if (resultCode == this.WEBVIEW_REQUEST_LOGGED) {
                String verifier = data.getExtras().getString(Constants.VERIFIER);
                Twitter twitter = twitterUtils.getTwitter();
                RetrieveAccesTokenTask retrieveAccesTokenTask = new RetrieveAccesTokenTask(verifier);
                retrieveAccesTokenTask.execute(twitter);
            }else if(resultCode==WEBVIEW_REQUEST_NOTLOGGED){
                finish();
            }
        }
    }


    private Intent saveTwitterInfo(AccessToken accessToken) {
        Intent resultIntent = new Intent(this, MainActivity.class);
        setResult(MainActivity.RESULT_CODE_LOGGED, resultIntent);
        resultIntent.putExtra(MainActivity.PREF_KEY_OAUTH_TOKEN, accessToken.getToken());
        resultIntent.putExtra(MainActivity.PREF_KEY_OAUTH_SECRET, accessToken.getTokenSecret());
        return resultIntent;
    }

    private void sendResultIntent(AccessToken accessToken) {
        try {
            Intent resultIntent = saveTwitterInfo(accessToken);
            setResult(MainActivity.RESULT_CODE_LOGGED, resultIntent);
            finish();
        } catch (NullPointerException ne) {
            //TODO dialog z fuckupem
            finish();
        }
    }


    public class RetrieveResultTokenTask extends AsyncTask<Twitter, Void, RequestToken> {
        @Override
        protected RequestToken doInBackground(Twitter... params) {
            try {
                return params[0].getOAuthRequestToken(Constants.OAUTH_CALLBACK_URL);

            } catch (TwitterException e) {
                e.printStackTrace();
                Twitter twitter;
                twitter = twitterUtils.getBrandNewTwitter();
                try {
                    return twitter.getOAuthRequestToken(Constants.OAUTH_CALLBACK_URL);
                } catch (TwitterException e1) {
                    e1.printStackTrace();
                    return null;
                }
            }
        }

        @Override
        protected void onPostExecute(RequestToken requestToken) {
            startURLActivity(requestToken);
        }
    }

    public class RetrieveAccesTokenTask extends AsyncTask<Twitter, Void, AccessToken> {

        String verifier;

        public RetrieveAccesTokenTask(String string) {
            verifier = string;
        }

        @Override
        protected AccessToken doInBackground(Twitter... params) {

            try {
                return params[0].getOAuthAccessToken(requestToken, verifier);
            } catch (TwitterException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(AccessToken accessToken) {
            sendResultIntent(accessToken);
        }
    }

}
