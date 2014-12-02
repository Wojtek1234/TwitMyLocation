package pl.wmaciejewski.twitmylocation.injections;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pl.wmaciejewski.twitmylocation.TwitMyLocationApplication;
import pl.wmaciejewski.twitmylocation.twitter.TwitterPanel;
import pl.wmaciejewski.twitmylocation.twitter.TwitterUtils;

/**
 * Created by w.maciejewski on 2014-12-02.
 */

@Module(
        complete = true,    // Here it enables object graph validation
        library = true,
        addsTo = AndroidModule.class, // Important for object graph validation at compile time
        injects = {
                TwitMyLocationApplication.class,
        }
)
public class TwitMyLocationModule {
   @Provides
   @Singleton
    TwitterUtils proviedTwitterUtils(){
        return new TwitterUtils();
    }
}

