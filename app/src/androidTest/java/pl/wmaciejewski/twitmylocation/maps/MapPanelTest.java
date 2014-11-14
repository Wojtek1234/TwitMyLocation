package pl.wmaciejewski.twitmylocation.maps;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.test.AndroidTestCase;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import pl.wmaciejewski.twitmylocation.R;

public class MapPanelTest extends AndroidTestCase {
    private Context context;
    private MapPanel mapPanel;
    private String mocLocationProvider;
    private LocationManager lm;
    private LinearLayout ll;

    public void setUp() throws Exception {
        super.setUp();
        context= getContext();

        ll= (LinearLayout) View.inflate(context, R.layout.map_layout, null);
        mapPanel =new MapPanel(ll);

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