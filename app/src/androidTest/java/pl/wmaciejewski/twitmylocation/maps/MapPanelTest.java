package pl.wmaciejewski.twitmylocation.maps;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import pl.wmaciejewski.twitmylocation.R;

public class MapPanelTest extends AndroidTestCase {
    private Context context= new RenamingDelegatingContext(getContext(), "test_");
    private MapPanel mapPanel;
    private String mocLocationProvider;
    private LocationManager lm;

    public void setUp() throws Exception {
        super.setUp();
        LinearLayout ll= (LinearLayout) View.inflate(context, R.id.mapPanel, null);
        mapPanel =new MapPanel(ll);

    }

    public void testFindMeOnMapClick(){
        MockLocationProvider mock = new MockLocationProvider(LocationManager.GPS_PROVIDER, context);
        mock.pushLocation(52.218887, 21.024797);
        Button findMeOnMap=(Button)View.inflate(context, R.id.findMeMap, null);
        findMeOnMap.performClick();
        Location loc=mapPanel.getCurrentLocation();
        assertEquals(mock.getMockLocation(),loc);

    }

    public void tearDown() throws Exception {

    }



}