package pl.wmaciejewski.twitmylocation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import oauth.signpost.OAuth;
import pl.wmaciejewski.twitmylocation.twitter.RequestTokenActivity;


public class MainActivity extends FragmentActivity {
    private SharedPreferences prefs;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.prefs = PreferenceManager.getDefaultSharedPreferences(this);
    }



    public void login(View view) {
        Intent i = new Intent(getApplicationContext(), RequestTokenActivity.class);
        startActivity(i);
    }

    @Override
    protected void onNewIntent(Intent intent) {



    }
    private void clearCredentials() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor edit = prefs.edit();
        edit.remove(OAuth.OAUTH_TOKEN);
        edit.remove(OAuth.OAUTH_TOKEN_SECRET);
        edit.commit();
    }
}
