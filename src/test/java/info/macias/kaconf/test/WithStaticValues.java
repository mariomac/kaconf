package info.macias.kaconf.test;

import info.macias.kaconf.Property;

public class WithStaticValues {
    @Property("publicint")
    public static int STATIC_VALUE;

    @Property("normalvalue")
    public String normalValue;
}
