package pl.wmaciejewski.twitmylocation;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import pl.wmaciejewski.twitmylocation.bus.BusProvider;
import pl.wmaciejewski.twitmylocation.maps.MapPanel;
import pl.wmaciejewski.twitmylocation.sendtwitpackage.SendTwitActivity;
import pl.wmaciejewski.twitmylocation.sendtwitpackage.SetUpBundle;
import pl.wmaciejewski.twitmylocation.twitter.TwitterPanel;
import pl.wmaciejewski.twitmylocation.twitter.TwitterUtils;
import pl.wmaciejewski.twitmylocation.twitter.dialog.FindHashTagDialog;
import twitter4j.Status;
import twitter4j.Twitter;


public class MainActivity extends FragmentActivity implements TwitterPanel.TwitterListener{
    private static final int REQUEST_LOGIN =101 ;
    public static final int RESULT_CODE_LOGGED=102;
    public static String PREF_KEY_OAUTH_TOKEN="Key_token";
    public static String PREF_KEY_OAUTH_SECRET="secret_token";
    private SharedPreferences prefs;
    private GoogleMap mMap;
    private TwitterPanel twitterPanel;
    private MapPanel mapPanel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpMapIfNeeded();
        checkLocationSettings();
        this.prefs = PreferenceManager.getDefaultSharedPreferences(this);
        twitterPanel=new TwitterPanel((LinearLayout)findViewById(R.id.tweetingPanel),prefs);
        twitterPanel.setOnTwittListener(this);
        mapPanel=new MapPanel(findViewById(R.id.mapPanel),mMap);

        BusProvider.getInstance().register(mapPanel);
        BusProvider.getInstance().register(twitterPanel);

        twitterPanel.setForDialog(new FindHashTagDialog(),getSupportFragmentManager());

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void checkLocationSettings() {
        LocationManager locationManager = (LocationManager) this
                .getSystemService(LOCATION_SERVICE);
        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) showSettingsAlert();
        if(!locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) showSettingsAlert();
    }

    @Override
    protected void onDestroy() {
        try{
            BusProvider.getInstance().unregister(mapPanel);
            BusProvider.getInstance().unregister(twitterPanel);
        }catch (Exception e){

        }

        super.onDestroy();
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
            case R.id.twitAction:  twitterPanel.showPanel();
                return true;
            case R.id.mapAction: mapPanel.showPanel();
                return true;
            default: return super.onMenuItemSelected(featureId, item);
        }
    }

    private void setUpMapIfNeeded() {
        if (mMap == null)  mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment))
                    .getMap();
        mMap.setMyLocationEnabled(true);
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
    public void onFindHashTag(List<Status> statusList) {
        mapPanel.doOnListOfStauses(statusList);
    }


    @Override
    public void onTwitLocation(Twitter twitter) {
        if(mapPanel.getCurrentLocation()!=null){
            Location loc=mapPanel.getCurrentLocation();
            LatLng latLng=new LatLng(loc.getLatitude(),loc.getLongitude());
            Bitmap bitmap=mapPanel.getProfileImage();
            Intent intent=new Intent(this,SendTwitActivity.class);
            intent.putExtras( SetUpBundle.setBundle(latLng, bitmap, TwitterUtils.getInstance().getUser().getName()));
            startActivity(intent);
        }
    }



    private void clearCredentials() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor edit = prefs.edit();
        edit.remove(PREF_KEY_OAUTH_TOKEN);
        edit.remove(PREF_KEY_OAUTH_SECRET);
        edit.commit();
    }
    private void saveTwitterInfo(Intent intent){
        SharedPreferences.Editor e = PreferenceManager.getDefaultSharedPreferences(this).edit();
        e.putString(PREF_KEY_OAUTH_TOKEN, (String) intent.getExtras().get(PREF_KEY_OAUTH_TOKEN));
        e.putString(PREF_KEY_OAUTH_SECRET, (String) intent.getExtras().get(PREF_KEY_OAUTH_SECRET));
        e.commit();
    }

    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Location warning");
        alertDialog.setMessage(getResources().getString(R.string.gsr_not_enable));
        alertDialog.setPositiveButton(getResources().getString(R.string.go_to_setting), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });
        alertDialog.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }


}
