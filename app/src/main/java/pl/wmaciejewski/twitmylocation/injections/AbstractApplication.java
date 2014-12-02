package pl.wmaciejewski.twitmylocation.injections;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import dagger.ObjectGraph;

/**
 * Created by Wojtek on 2014-12-02.
 */
public abstract class AbstractApplication extends Application  {

    private ObjectGraph mObjectGraph;

    @Override
    public void onCreate() {
        super.onCreate();

        AndroidModule androidModule = new AndroidModule();

        androidModule.sApplicationContext = this.getApplicationContext();

        List<Object> modules = new ArrayList<Object>();
        modules.add(androidModule);
        modules.addAll(getAppModules());

        mObjectGraph = ObjectGraph.create(modules.toArray());

        mObjectGraph.inject(this);
    }

    protected abstract List<Object> getAppModules();


    public void inject(Object object) {
        mObjectGraph.inject(object);
    }


    public ObjectGraph getObjectGraph() {
        return mObjectGraph;
    }
}