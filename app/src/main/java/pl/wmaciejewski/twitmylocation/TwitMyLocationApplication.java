package pl.wmaciejewski.twitmylocation;



import java.util.Arrays;
import java.util.List;


import pl.wmaciejewski.twitmylocation.injections.AbstractApplication;
import pl.wmaciejewski.twitmylocation.injections.TwitMyLocationModule;


/**
 * Created by w.maciejewski on 2014-12-02.
 */
public class TwitMyLocationApplication extends AbstractApplication {


    @Override
    protected List<Object> getAppModules() {
            return  Arrays.<Object>asList(new TwitMyLocationModule());
    }
}
