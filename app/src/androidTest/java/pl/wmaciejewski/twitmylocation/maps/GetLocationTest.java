package pl.wmaciejewski.twitmylocation.maps;

import android.content.Context;
import android.location.LocationManager;
import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

public class GetLocationTest extends AndroidTestCase {
    private Context context= new RenamingDelegatingContext(getContext(), "test_");
    private GetLocation getLocation;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        this.getLocation=new GetLocation((LocationManager)context.getSystemService(Context.LOCATION_SERVICE));
    }
    public void testCurrentLocation(){
        MockLocationProvider mock = new MockLocationProvider(LocationManager.GPS_PROVIDER, context);
        mock.pushLocation(52.218887, 21.024797);
        GetLocation getLocation=new GetLocation((LocationManager)context.getSystemService(Context.LOCATION_SERVICE));

        assertEquals(mock.getMockLocation(),getLocation.getLastKnowLocation());

        mock.pushLocation(52.218887, 31.024797);
        assertEquals(mock.getMockLocation(),getLocation.getLastKnowLocation());
    }







}