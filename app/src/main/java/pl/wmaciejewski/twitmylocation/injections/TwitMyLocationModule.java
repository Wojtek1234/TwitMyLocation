package pl.wmaciejewski.twitmylocation.injections;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pl.wmaciejewski.twitmylocation.twitter.TwitterPanel;
import pl.wmaciejewski.twitmylocation.twitter.TwitterUtils;

/**
 * Created by w.maciejewski on 2014-12-02.
 */

@Module(
        injects = TwitterPanel.class,
        complete = false
)
public class TwitMyLocationModule {
   @Provides
   @Singleton
    TwitterUtils proviedTwitterUtils(){
        return new TwitterUtils();
    }
}

