package info.macias.kaconf.test;

import info.macias.kaconf.Property;

/**
 * Created by mmacias on 19/11/16.
 */
public class SomeClass {
    @Property("publicint")
    public int publicint;
    @Property("protectedstring")
    protected String protectedstring;
    @Property("privatebyte")
    private byte privatebyte;
    @Property("nullstring")
    private String nullstring;
    @Property("nullinteger")
    protected Integer nullinteger;
    @Property("nullint")
    protected int nullint;

    @Property("publicint")
    private int otherFieldSameProperty;

    public String getNullstring() {
        return nullstring;
    }

    public Integer getNullinteger() {
        return nullinteger;
    }

    public int getNullint() {
        return nullint;
    }

    public int getPublicint() {
        return publicint;
    }

    public String getProtectedstring() {
        return protectedstring;
    }

    public byte getPrivatebyte() {
        return privatebyte;
    }

    public int getOtherFieldSameProperty() {
        return otherFieldSameProperty;
    }
}
