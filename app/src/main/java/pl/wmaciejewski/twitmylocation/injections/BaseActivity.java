package pl.wmaciejewski.twitmylocation.injections;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import java.util.Arrays;
import java.util.List;

import dagger.ObjectGraph;
import pl.wmaciejewski.twitmylocation.TwitMyLocationApplication;

/**
 * Created by w.maciejewski on 2014-12-02.
 */
public abstract class BaseActivity extends FragmentActivity {
    private ObjectGraph activityGraph;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create the activity graph by .plus-ing our modules onto the application graph.
        AbstractApplication application = (AbstractApplication) getApplication();
        activityGraph = application.getObjectGraph().plus(getModules());

        // Inject ourselves so subclasses will have dependencies fulfilled when this method returns.
        activityGraph.inject(this);
    }


    @Override protected void onDestroy() {
        // Eagerly clear the reference to the activity graph to allow it to be garbage collected as
        // soon as possible.
        activityGraph = null;

        super.onDestroy();
    }


    protected abstract Object[] getModules();

    /** Inject the supplied {@code object} using the activity-specific graph. */
    public void inject(Object object) {
        activityGraph.inject(object);
    }
    public ObjectGraph getObjectGraph() {
        return activityGraph;
    }

}
