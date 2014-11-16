package pl.wmaciejewski.twitmylocation.maps;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

/**
 * Created by w.maciejewski on 2014-11-13.
 */
public class MockLocationProvider {
    String providerName;
    Context ctx;

    public Location getMockLocation() {
        return mockLocation;
    }

    public void setMockLocation(Location mockLocation) {
        this.mockLocation = mockLocation;
    }

    private Location mockLocation;

    public MockLocationProvider(String name, Context ctx) {
        this.providerName = name;
        this.ctx = ctx;

        LocationManager lm = (LocationManager) ctx.getSystemService(
                Context.LOCATION_SERVICE);
        lm.addTestProvider(providerName, false, false, false, false, false,
                true, true, 0, 5);
        lm.setTestProviderEnabled(providerName, true);
    }

    public void pushLocation(double lat, double lon) {
        LocationManager lm = (LocationManager) ctx.getSystemService(
                Context.LOCATION_SERVICE);

        mockLocation = new Location(providerName);
        mockLocation.setLatitude(lat);
        mockLocation.setLongitude(lon);
        mockLocation.setAltitude(0);
        mockLocation.setTime(System.currentTimeMillis());
        mockLocation.setElapsedRealtimeNanos(System.nanoTime());
        mockLocation.setAccuracy(10);
        lm.setTestProviderLocation(providerName, mockLocation);
    }

    public void shutdown() {
        LocationManager lm = (LocationManager) ctx.getSystemService(
                Context.LOCATION_SERVICE);
        lm.removeTestProvider(providerName);
    }
}