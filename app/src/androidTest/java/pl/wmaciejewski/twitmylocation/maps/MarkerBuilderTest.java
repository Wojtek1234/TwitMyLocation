package pl.wmaciejewski.twitmylocation.maps;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.test.AndroidTestCase;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.gms.maps.model.MarkerOptions;

import pl.wmaciejewski.twitmylocation.MainActivity;
import pl.wmaciejewski.twitmylocation.R;
import pl.wmaciejewski.twitmylocation.twitter.TwitterUtils;

public class MarkerBuilderTest extends AndroidTestCase {
    private Context context;
    private MarkerBuilder markerBuilder;
    private MockLocationProvider mock;

    public void setUp() throws Exception {
        super.setUp();
        context= getContext();
        logginUser(PreferenceManager.getDefaultSharedPreferences(context));

        while(!TwitterUtils.getInstance().isLogged()) {
            Thread.sleep(1000);
        }
        LayoutInflater aboutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=aboutInflater.inflate(R.layout.map_layout,null);
        markerBuilder=new MarkerBuilder(view.findViewById(R.id.mapPanel));
        markerBuilder.updateUser(TwitterUtils.getInstance().getUser());
        mock = new MockLocationProvider(LocationManager.GPS_PROVIDER, context);
        mock.pushLocation(52.218887, 21.024797);
    }


    public void testMarkerBild(){
       MarkerOptions markerOptions=markerBuilder.createMarker(mock.getMockLocation());
       assertEquals(markerOptions.getPosition().latitude ,mock.getMockLocation().getLatitude());
       assertEquals(markerOptions.getPosition().longitude,mock.getMockLocation().getLongitude());
       assertEquals(markerOptions.getTitle(),TwitterUtils.getInstance().getUser().getName());
    }

    public void tearDown() throws Exception {
        mock.shutdown();
    }









    private void logginUser(SharedPreferences prefs) {
        final String LOGGED_USER_TOKEN = "2869153881-AaWTaeQHKvPBBGBp51l9UTmFmeSiPf6dtOTXWQX";
        final String LOGGED_USER_SECRET = "7bonzofF0HaSc5IwXGIzDXD1azovpQBWCuax15Vyl4qDZ";
        SharedPreferences.Editor e = PreferenceManager.getDefaultSharedPreferences(context).edit();
        e.putString(MainActivity.PREF_KEY_OAUTH_TOKEN, LOGGED_USER_TOKEN);
        e.putString(MainActivity.PREF_KEY_OAUTH_SECRET, LOGGED_USER_SECRET);
        TwitterUtils twitterUtils= TwitterUtils.getInstance();
        twitterUtils.authenticat(prefs);

    }


}