package info.macias.kaconf.test;

import info.macias.kaconf.Property;

public class WithStaticValuesSubClass extends WithStaticValues {
    @Property("subprivatebyte")
    private static byte SUB_STATIC_VALUE;

    public static byte getSubStaticValue() {
        return SUB_STATIC_VALUE;
    }
}
