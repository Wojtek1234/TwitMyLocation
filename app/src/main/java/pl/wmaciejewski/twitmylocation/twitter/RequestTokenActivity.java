package pl.wmaciejewski.twitmylocation.twitter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;

import pl.wmaciejewski.twitmylocation.MainActivity;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;


public class RequestTokenActivity extends Activity {
    public static final int WEBVIEW_REQUEST_CODE = 254;
    public static final int WEBVIEW_REQUEST_LOGGED = 255;


    private RequestToken requestToken;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Twitter twitter = TwitterUtils.getInstance().getTwitter();
        RetrieveAccessTokenTask retrieveAccessTokenTask = new RetrieveAccessTokenTask();
        retrieveAccessTokenTask.execute(twitter);

    }

    public void startURLActivity(RequestToken requestToken) {
        //TODO http://javatechig.com/android/how-to-integrate-twitter-in-android-application
        this.requestToken = requestToken;
        final Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra(WebViewActivity.EXTRA_URL, requestToken.getAuthenticationURL());
        startActivityForResult(intent, WEBVIEW_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == this.WEBVIEW_REQUEST_CODE) {
            if (resultCode == this.WEBVIEW_REQUEST_LOGGED) {
                try {
                    String verifier = data.getExtras().getString(Constants.VERIFIER);
                    Twitter twitter = TwitterUtils.getInstance().getTwitter();
                    AccessToken accessToken = twitter.getOAuthAccessToken(requestToken, verifier);
                    Intent intent = saveTwitterInfo(accessToken);
                    sendResultIntent(intent);
                } catch (TwitterException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Uri uri = getIntent().getData();
        if (uri != null && uri.toString().startsWith(Constants.OAUTH_CALLBACK_URL)) {
            String verifier = uri.getQueryParameter(Constants.AUTHORIZE_URL);
        }
    }

    private Intent saveTwitterInfo(AccessToken accessToken) {
        Intent resultIntent = new Intent(this, MainActivity.class);
        setResult(MainActivity.RESULT_CODE_LOGGED, resultIntent);
        resultIntent.putExtra(MainActivity.PREF_KEY_OAUTH_TOKEN, accessToken.getToken());
        resultIntent.putExtra(MainActivity.PREF_KEY_OAUTH_SECRET, accessToken.getTokenSecret());
        return resultIntent;
    }

    private void sendResultIntent(Intent resultIntent) {

        setResult(MainActivity.RESULT_CODE_LOGGED, resultIntent);
        finish();
    }


    public class RetrieveAccessTokenTask extends AsyncTask<Twitter, Void, RequestToken> {


        @Override
        protected RequestToken doInBackground(Twitter... params) {
            try {
                return params[0].getOAuthRequestToken(Constants.OAUTH_CALLBACK_URL);


            } catch (TwitterException e) {
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(RequestToken requestToken) {
            startURLActivity(requestToken);
        }
    }


}
