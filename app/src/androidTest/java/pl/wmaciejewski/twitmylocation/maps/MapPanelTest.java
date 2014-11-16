package pl.wmaciejewski.twitmylocation.maps;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;

import pl.wmaciejewski.twitmylocation.MainActivity;
import pl.wmaciejewski.twitmylocation.R;

public class MapPanelTest extends ActivityInstrumentationTestCase2<MainActivity> {
    private Context context;
    private MapPanel mapPanel;
    private String mocLocationProvider;
    private LocationManager lm;
    private LinearLayout ll;

    public MapPanelTest() {
        super(MainActivity.class);
    }

    public void setUp() throws Exception {
        super.setUp();
        MainActivity mainActivity=getActivity();

        ll= (LinearLayout) mainActivity.findViewById(R.id.mapPanel);
        GoogleMap mMap = ((MapFragment) mainActivity.getFragmentManager().findFragmentById(R.id.mapFragment))
                .getMap();
        mapPanel =new MapPanel(ll,mMap);

    }

    public void testFindMeOnMapClick(){


    }

    public void tearDown() throws Exception {

    }



}