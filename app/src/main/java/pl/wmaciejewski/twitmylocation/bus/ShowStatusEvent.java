package pl.wmaciejewski.twitmylocation.bus;

import android.os.Bundle;

/**
 * Created by w.maciejewski on 2014-11-27.
 */
public class ShowStatusEvent {
    private Bundle bundle;

    public ShowStatusEvent(Bundle bundle) {
        this.bundle = bundle;
    }

    public Bundle getBundle() {
        return bundle;
    }
}
