package pl.wmaciejewski.twitmylocation.injections;

import android.app.Activity;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pl.wmaciejewski.twitmylocation.MainActivity;
import pl.wmaciejewski.twitmylocation.sendtwitpackage.SendTwitActivity;
import pl.wmaciejewski.twitmylocation.twitter.TwitterPanel;
import pl.wmaciejewski.twitmylocation.twitter.senders.AbstractSender;

/**
 * Created by w.maciejewski on 2014-12-02.
 */

@Module(
        complete = true,    // Here we enable object graph validation
        library = true,
        addsTo = TwitMyLocationModule.class, // Important for object graph validation at compile time
        injects = {
                TwitterPanel.class,
                MainActivity.class,
                AbstractSender.class,
                SendTwitActivity.class,
        }
)
public class ActivityModule {
    private final BaseActivity activity;

    public ActivityModule(BaseActivity baseActivity) {
        this.activity = baseActivity;
    }



    @Provides
    @Singleton
    @ForActivity
    Context providesActivityContext() {
        return activity;
    }

    @Provides
    @Singleton
    Activity providesActivity() {
        return activity;
    }

}


