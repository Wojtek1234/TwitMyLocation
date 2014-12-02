package pl.wmaciejewski.twitmylocation.injections;

import android.content.Context;
import android.location.LocationManager;

import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pl.wmaciejewski.twitmylocation.TwitMyLocationApplication;
import pl.wmaciejewski.twitmylocation.twitter.TwitterPanel;
import pl.wmaciejewski.twitmylocation.twitter.TwitterUtils;
import pl.wmaciejewski.twitmylocation.twitter.senders.AbstractSender;

/**
 * Created by w.maciejewski on 2014-12-02.
 */

@Module(injects = {TwitterPanel.class,AbstractSender.class},
        library = true, complete = false)

public class AndroidModule {

    static Context sApplicationContext=null;



    @Provides
    @Singleton
    @ForApplication
    Context provideApplicationContext() {
        return sApplicationContext;
    }

    @Provides @Singleton
    LocationManager provideLocationManager() {
        return (LocationManager) sApplicationContext.getSystemService(Context.LOCATION_SERVICE);
    }

    @Provides @Singleton
    Bus provideBus() {
        return new Bus();
    }


}


