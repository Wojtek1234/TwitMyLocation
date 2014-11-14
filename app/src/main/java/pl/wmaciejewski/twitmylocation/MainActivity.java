package pl.wmaciejewski.twitmylocation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import pl.wmaciejewski.twitmylocation.twitter.TwitterPanel;


public class MainActivity extends FragmentActivity implements TwitterPanel.TwitterListener{
    private static final int REQUEST_LOGIN =101 ;
    public static final int RESULT_CODE_LOGGED=102;
    public static String PREF_KEY_OAUTH_TOKEN="Key_token";
    public static String PREF_KEY_OAUTH_SECRET="secret_token";
    private SharedPreferences prefs;
    private GoogleMap mMap;
    private TwitterPanel twitterPanel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpMapIfNeeded();
        this.prefs = PreferenceManager.getDefaultSharedPreferences(this);
        twitterPanel=new TwitterPanel((LinearLayout)findViewById(R.id.tweetingPanel),prefs);
        twitterPanel.setOnTwittListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()){
            case R.id.twitAction:
                twitterPanel.showPanel();
                return true;
            case R.id.mapAction:

                return true;
            default:
               return super.onMenuItemSelected(featureId, item);
        }

    }

    private void clearCredentials() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor edit = prefs.edit();
        edit.remove(PREF_KEY_OAUTH_TOKEN);
        edit.remove(PREF_KEY_OAUTH_SECRET);
        edit.commit();
    }


    private void setUpMapIfNeeded() {
        if (mMap == null) {
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment))
                    .getMap();
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }

    @Override
    public void onLogingDemand(Intent loggingIntent) {
        startActivityForResult(loggingIntent, REQUEST_LOGIN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_LOGIN){
            if(resultCode==RESULT_CODE_LOGGED){

                this.prefs = PreferenceManager.getDefaultSharedPreferences(this);
                saveTwitterInfo(data);
                twitterPanel.login(this.prefs);
            }
        }
    }

    @Override
    public void onLogOutDemand() {
        clearCredentials();
        this.prefs = PreferenceManager.getDefaultSharedPreferences(this);
        twitterPanel.login(prefs);
    }

    @Override
    public void onFindHashTag(Intent hashTagIntent) {

    }

    private void saveTwitterInfo(Intent intent){
        SharedPreferences.Editor e = PreferenceManager.getDefaultSharedPreferences(this).edit();
        e.putString(PREF_KEY_OAUTH_TOKEN, (String) intent.getExtras().get(PREF_KEY_OAUTH_TOKEN));
        e.putString(PREF_KEY_OAUTH_SECRET, (String) intent.getExtras().get(PREF_KEY_OAUTH_SECRET));
        e.commit();
    }



}
