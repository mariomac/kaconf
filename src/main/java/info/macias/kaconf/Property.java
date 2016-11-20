package info.macias.kaconf;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by mmacias on 15/11/16.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Property {
    String value();
}
