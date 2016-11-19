package info.macias.contifus.test;

import info.macias.contifus.Property;

/**
 * Created by mmacias on 19/11/16.
 */
public class ConfigurableClass {
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
}
