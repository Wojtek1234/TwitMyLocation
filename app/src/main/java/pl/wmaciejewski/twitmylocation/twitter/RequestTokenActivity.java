package pl.wmaciejewski.twitmylocation.twitter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;
import pl.wmaciejewski.twitmylocation.MainActivity;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.auth.RequestToken;


public class RequestTokenActivity extends Activity {

    final String TAG = getClass().getName();




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Twitter twitter = TwitterUtils.getInstance().getTwitter();
        RetrieveAccessTokenTask retrieveAccessTokenTask=new RetrieveAccessTokenTask();
        retrieveAccessTokenTask.execute(twitter);

    }

    public void startURLActivity(RequestToken requestToken){
        //TODO http://javatechig.com/android/how-to-integrate-twitter-in-android-application
        this.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(requestToken.getAuthenticationURL())));
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

    private void sendResultIntent(){
        Intent resultIntent=new Intent(this,MainActivity.class);
        setResult(MainActivity.RESULT_CODE_LOGGED, resultIntent);
        finish();
    }


    public class RetrieveAccessTokenTask extends AsyncTask<Twitter, Void, RequestToken> {


        @Override
        protected RequestToken doInBackground(Twitter... params) {
            try {
               return  params[0].getOAuthRequestToken(Constants.OAUTH_CALLBACK_URL);


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
