package pl.wmaciejewski.twitmylocation.bus;

import com.squareup.otto.Bus;

/**
 * Created by w.maciejewski on 2014-11-06.
 */
public final class BusProvider {
    private static final Bus BUS = new Bus();

    public static Bus getInstance() {
        return BUS;
    }

    private BusProvider() {

    }
}
