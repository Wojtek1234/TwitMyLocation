package pl.wmaciejewski.twitmylocation.maps;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.gms.maps.GoogleMap;
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

        ll= (LinearLayout) View.inflate(context, R.layout.map_layout, null);
        GoogleMap mMap = ((SupportMapFragment) mainActivity.getSupportFragmentManager().findFragmentById(R.id.mapFragment))
                .getMap();
        mapPanel =new MapPanel(ll,mMap);

    }

    public void testFindMeOnMapClick(){
        MockLocationProvider mock = new MockLocationProvider(LocationManager.GPS_PROVIDER, context);
        mock.pushLocation(52.218887, 21.024797);
        Button findMeOnMap=(Button)ll.findViewById(R.id.findMeMap);
        findMeOnMap.performClick();
        Location loc=mapPanel.getCurrentLocation();
        assertEquals(mock.getMockLocation(),loc);

    }

    public void tearDown() throws Exception {

    }



}