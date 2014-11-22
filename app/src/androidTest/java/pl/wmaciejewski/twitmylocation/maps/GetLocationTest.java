package pl.wmaciejewski.twitmylocation.maps;

import android.content.Context;
import android.location.LocationManager;
import android.test.AndroidTestCase;

public class GetLocationTest extends AndroidTestCase {
    private Context context;
    private GetLocation getLocation;
    private MockLocationProvider mock;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        context=getContext();

        mock = new MockLocationProvider(LocationManager.GPS_PROVIDER, context);
    }
    public void testCurrentLocation() {
        //TODO create good location test....
        mock.pushLocation(52.218887, 21.024797);
        getLocation = new GetLocation((LocationManager)context.getSystemService(context.LOCATION_SERVICE));



        assertEquals(mock.getMockLocation().getLongitude(), getLocation.getLastKnowLocation().getLongitude());
        assertEquals(mock.getMockLocation().getLatitude(), getLocation.getLastKnowLocation().getLatitude());

        mock.pushLocation(52.218887, 25.024797);

        assertEquals(mock.getMockLocation().getLongitude(), getLocation.getLastKnowLocation().getLongitude());
        assertEquals(mock.getMockLocation().getLatitude(), getLocation.getLastKnowLocation().getLatitude());

    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        mock.shutdown();
    }
}