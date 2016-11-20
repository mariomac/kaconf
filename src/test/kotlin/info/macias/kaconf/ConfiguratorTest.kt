package info.macias.kaconf

import info.macias.kaconf.test.ConfigurableClass
import info.macias.kaconf.test.ConfigurableSubclass
import junit.framework.TestCase
import org.junit.Test

/**
 * Created by mmacias on 19/11/16.
 */
class ConfiguratorTest : TestCase("Test Configurator") {
    internal class MockedProperty : AbstractPropertySource() {
        val properties = mapOf(
               "publicint" to "1234",
                "protectedstring" to "hello my colleague",
                "privatebyte" to "-123",
                "subpublicint" to "12345",
                "subprotectedstring" to "hello my other colleague",
                "subprivatebyte" to "-12"
        )

        override fun get(key: String): String? = properties[key]
    }

    @Test
    fun testConfiguration() {
        val obj = ConfigurableClass()
        ConfiguratorBuilder()
                .addPropertySource(MockedProperty())
                .build()
                .configure(obj)

        assertEquals(obj.privatebyte, -123)
        assertEquals(obj.publicint, 1234)
        assertEquals(obj.otherFieldSameProperty, 1234)
        assertEquals(obj.protectedstring, "hello my colleague")
        assertNull(obj.nullstring)
        assertNull(obj.nullinteger)
        assertEquals(obj.nullint,0)
    }

    @Test
    fun testInheritedProperties() {
        val obj = ConfigurableSubclass()
        ConfiguratorBuilder()
                .addPropertySource(MockedProperty())
                .build()
                .configure(obj)

        assertEquals(obj.subprivatebyte, -12)
        assertEquals(obj.subpublicint, 12345)
        assertEquals(obj.subprotectedstring, "hello my other colleague")
        assertNull(obj.subnullstring)
        assertNull(obj.subnullinteger)
        assertEquals(obj.subnullint,0)

        assertEquals(obj.privatebyte, -123)
        assertEquals(obj.publicint, 1234)
        assertEquals(obj.otherFieldSameProperty, 1234)
        assertEquals(obj.protectedstring, "hello my colleague")
        assertNull(obj.nullstring)
        assertNull(obj.nullinteger)
        assertEquals(obj.nullint,0)

    }
}