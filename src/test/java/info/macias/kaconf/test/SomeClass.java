package info.macias.kaconf.test;

import info.macias.kaconf.Property;

public class SomeClass {
    @Property({"publicint", "PUBLIC_INT"})
    public int publicint;
    @Property({"protectedstring", "PROTECTED_STRING"})
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
