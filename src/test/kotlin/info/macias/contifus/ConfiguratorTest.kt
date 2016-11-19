package info.macias.contifus

import info.macias.contifus.test.ConfigurableClass
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
                "privatebyte" to "-123"
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
        assertEquals(obj.protectedstring, "hello my colleague")
        assertNull(obj.nullstring)
        assertNull(obj.nullinteger)
        assertEquals(obj.nullint,0)
    }
}