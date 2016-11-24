package info.macias.kaconf.test

import info.macias.kaconf.Value
import info.macias.kaconf.Property

/**
 * Created by mmacias on 24/11/16.
 */
class KonfigurableClass {
    @Property("publicint")
    var publicint = Value.def(321)

    @Property("privatebyte")
    private var privatebyte = Value.aByte()

    @Property("protectedstring")
    protected var protectedstring = Value.aString()

    fun getPrivateByte() = privatebyte
    fun getProtectedString() = protectedstring

    @Property("finalchar")
    val finalchar = Value.def('a')

    @Property("PropertyNotFound")
    val propertynotfound = Value.def("default")

    companion object {
        @Property("finalstaticint")
        val finalstaticint: Int = 0

        @Property("finalstaticstring")
        val finalstaticstring = ""

    }
}


object KonfigurableObject {
    @Property("aboolean")
    val aboolean = Value.aBoolean()

    @Property("anint")
    var anint: Int? = null

    @Property("Apropertythatisnotfound")
    var nonfoundproperty: String? = null
}