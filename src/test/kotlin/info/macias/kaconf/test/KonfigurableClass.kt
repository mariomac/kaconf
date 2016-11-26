package info.macias.kaconf.test

import info.macias.kaconf.KA
import info.macias.kaconf.Property

/**
 * Created by mmacias on 24/11/16.
 */
class KonfigurableClass {
    @Property("publicint")
    var publicint = KA.def(321)

    @Property("privatebyte")
    private var privatebyte = KA.aByte()

    @Property("protectedstring")
    protected var protectedstring = KA.aString()

    fun getPrivateByte() = privatebyte
    fun getProtectedString() = protectedstring

    @Property("finalchar")
    val finalchar = KA.def('a')

    @Property("PropertyNotFound")
    val propertynotfound = KA.def("default")

    companion object {
        @Property("finalstaticint")
        val finalstaticint: Int = 0

        @Property("finalstaticstring")
        val finalstaticstring = ""

    }
}


object KonfigurableObject {
    @Property("aboolean")
    val aboolean = KA.aBoolean()

    @Property("anint")
    var anint: Int? = null

    @Property("Apropertythatisnotfound")
    var nonfoundproperty: String? = null
}