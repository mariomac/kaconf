package info.macias.kaconf.test;

import info.macias.kaconf.KA;
import info.macias.kaconf.Property;

public class WithFinalValues {
    @Property("finalValue")
    public final int finalValue = 0;

    @Property("finalStaticValue")
    private final static int SUPPOSEDLY_CONSTANT = KA.anInt();

    @Property("finalStaticString")
    public final static String SUPPOSEDLY_CONSTANT_STRING = null;

    public static int getSupposedlyConstant() {
        return SUPPOSEDLY_CONSTANT;
    }
}
