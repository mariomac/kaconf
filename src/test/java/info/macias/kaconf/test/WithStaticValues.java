package info.macias.kaconf.test;

import info.macias.kaconf.Property;

/**
 * Created by mmacias on 21/11/16.
 */
public class WithStaticValues {
    @Property("publicint")
    public static int STATIC_VALUE;

    @Property("normalvalue")
    public String normalValue;
}
