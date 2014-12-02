package pl.wmaciejewski.twitmylocation.injections;

import android.app.Application;

import java.util.Arrays;
import java.util.List;

import dagger.ObjectGraph;

/**
 * Created by w.maciejewski on 2014-12-02.
 */
public class TwitMyLocationApplication extends Application {


    private ObjectGraph applicationGraph;

    @Override public void onCreate() {
        super.onCreate();

        applicationGraph = ObjectGraph.create(getModules().toArray());
    }

    protected List<Object> getModules() {
        return Arrays.asList(
                new AndroidModule(this),
                new TwitMyLocationModule()
        );
    }

    public void inject(Object object) {
        applicationGraph.inject(object);
    }

    public ObjectGraph getApplicationGraph() {
        return applicationGraph;
    }
}
