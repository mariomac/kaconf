package info.macias.kaconf.test;

import info.macias.kaconf.Property;

/**
 * Created by mmacias on 20/11/16.
 */
public class ConfigurableSubclass extends ConfigurableClass {
    @Property("subpublicint")
    public int subpublicint;
    @Property("subprotectedstring")
    protected String subprotectedstring;
    @Property("subprivatebyte")
    private byte subprivatebyte;
    @Property("subnullstring")
    private String subnullstring;
    @Property("subnullinteger")
    protected Integer subnullinteger;
    @Property("subnullint")
    protected int subnullint;

    public int getSubpublicint() {
        return subpublicint;
    }

    public String getSubprotectedstring() {
        return subprotectedstring;
    }

    public byte getSubprivatebyte() {
        return subprivatebyte;
    }

    public String getSubnullstring() {
        return subnullstring;
    }

    public Integer getSubnullinteger() {
        return subnullinteger;
    }

    public int getSubnullint() {
        return subnullint;
    }
}
