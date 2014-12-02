package pl.wmaciejewski.twitmylocation.injections;

import java.lang.annotation.Retention;

import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by w.maciejewski on 2014-12-02.
 */

@Qualifier
@Retention(RUNTIME)
public @interface ForActivity {
}
