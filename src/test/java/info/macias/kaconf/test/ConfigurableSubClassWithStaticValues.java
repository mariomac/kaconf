package info.macias.kaconf.test;

import info.macias.kaconf.Property;

/**
 * Created by mmacias on 21/11/16.
 */
public class ConfigurableSubClassWithStaticValues extends ConfigurableClassWithStaticValues {
    @Property("subprivatebyte")
    private static byte SUB_STATIC_VALUE;

    public static byte getSubStaticValue() {
        return SUB_STATIC_VALUE;
    }
}
