package pl.wmaciejewski.twitmylocation.injections;

import android.content.Context;
import android.location.LocationManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pl.wmaciejewski.twitmylocation.twitter.TwitterPanel;
import pl.wmaciejewski.twitmylocation.twitter.TwitterUtils;

import static android.content.Context.LOCATION_SERVICE;
/**
 * Created by w.maciejewski on 2014-12-02.
 */

@Module(injects = {TwitterPanel.class},
        library = true, complete = false)

public class AndroidModule {
    private final TwitMyLocationApplication application;

    public AndroidModule(TwitMyLocationApplication twitMyLocationApplication) {
        this.application=twitMyLocationApplication;
    }

    @Provides
    @Singleton
    @ForApplication
    Context provideApplicationContext() {
        return application;
    }

    @Provides @Singleton
    LocationManager provideLocationManager() {
        return (LocationManager) application.getSystemService(LOCATION_SERVICE);
    }

    @Provides
    @Singleton
    @ForApplication
    TwitterUtils proviedTwitterUtils(){
        return new TwitterUtils();
    }
}
