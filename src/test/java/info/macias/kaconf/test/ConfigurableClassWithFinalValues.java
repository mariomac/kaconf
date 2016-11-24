package info.macias.kaconf.test;

import info.macias.kaconf.Property;

/**
 * Created by mmacias on 21/11/16.
 */
public class ConfigurableClassWithFinalValues {
    @Property("finalValue")
    public final int finalValue = 0;

    @Property("finalStaticValue")
    private final static int SUPPOSEDLY_CONSTANT = m();

    @Property("finalStaticString")
    public final static String SUPPOSEDLY_CONSTANT_STRING = null;

    private final static int m() {
        return 0;
    }
    public static int getSupposedlyConstant() {
        return SUPPOSEDLY_CONSTANT;
    }
}
