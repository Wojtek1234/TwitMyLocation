package pl.wmaciejewski.twitmylocation.maps;

import android.content.Context;
import android.location.LocationManager;
import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

public class GetLocationTest extends AndroidTestCase {
    private Context context;
    private GetLocation getLocation;
    private MockLocationProvider mock;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        context= new RenamingDelegatingContext(getContext(), "test_");
        this.getLocation=new GetLocation((LocationManager)context.getSystemService(Context.LOCATION_SERVICE));
        mock = new MockLocationProvider(LocationManager.GPS_PROVIDER, context);
    }
    public void testCurrentLocation() {

        mock.pushLocation(52.218887, 21.024797);
        GetLocation getLocation = new GetLocation((LocationManager) context.getSystemService(Context.LOCATION_SERVICE));

        assertEquals(mock.getMockLocation().getLongitude(), getLocation.getLastKnowLocation().getLongitude());
        assertEquals(mock.getMockLocation().getLatitude(), getLocation.getLastKnowLocation().getLatitude());

    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        mock.shutdown();
    }
}