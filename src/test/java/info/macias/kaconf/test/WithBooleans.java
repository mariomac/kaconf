package info.macias.kaconf.test;

import info.macias.kaconf.Property;

public class WithBooleans {
    @Property("prop.true")
    public boolean trueProperty;

    @Property("prop.yes")
    public Boolean yesProperty;

    @Property("prop.1")
    public Boolean oneProperty;

    @Property("prop.false")
    public boolean falseProperty;

    @Property("prop.no")
    public Boolean noProperty;

    @Property("prop.0")
    public boolean zeroProperty;

    @Property("nonPresentProperty")
    public boolean hasToBeFalse;

    @Property("otherNonPresentProperty")
    public Boolean hasToBeNull;

    @Property("prop.anyOtherProperty")
    public final Boolean anyOtherProperty = null;
}
