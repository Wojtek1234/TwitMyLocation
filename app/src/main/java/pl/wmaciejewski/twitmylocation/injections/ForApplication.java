package pl.wmaciejewski.twitmylocation.injections;

/**
 * Created by w.maciejewski on 2014-12-02.
 */

import java.lang.annotation.Retention;

import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Qualifier
@Retention(RUNTIME)
public @interface ForApplication {
}
