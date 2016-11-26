package info.macias.kaconf

import info.macias.kaconf.sources.AbstractPropertySource
import info.macias.kaconf.test.*
import junit.framework.TestCase
import org.junit.Test
import java.util.stream.Collectors

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

        override fun isAvailable() = true

        override fun get(key: String): String? = properties[key]
    }

    @Test
    fun testConfiguration() {
        val obj = SomeClass()
        ConfiguratorBuilder()
                .addSource(MockedProperty())
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
        val obj = SomeSubclass()
        ConfiguratorBuilder()
                .addSource(MockedProperty())
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

    @Test
    fun testPropertyPreferences() {
        val obj = SomeSubclass()
        ConfiguratorBuilder()
                .addSource(mapOf(
                        "subprivatebyte" to "127",
                        "nullstring" to "hello boy",
                        "publicint" to "-4321"
                ))
                .addSource(MockedProperty())
                .build()
                .configure(obj)


        assertEquals(obj.subprivatebyte, 127)
        assertEquals(obj.subpublicint, 12345)
        assertEquals(obj.subprotectedstring, "hello my other colleague")
        assertNull(obj.subnullstring)
        assertNull(obj.subnullinteger)
        assertEquals(obj.subnullint,0)

        assertEquals(obj.privatebyte, -123)
        assertEquals(obj.publicint, -4321)
        assertEquals(obj.otherFieldSameProperty, -4321)
        assertEquals(obj.protectedstring, "hello my colleague")
        assertEquals(obj.nullstring, "hello boy")
        assertNull(obj.nullinteger)
        assertEquals(obj.nullint,0)
    }

    @Test
    fun testEnvSystemProperties() {
        val env = mapOf(
                "privatebyte" to "23",
                "subprotectedstring" to "Hello you"
        )
        val properties = mapOf(
                "privatebyte" to 1,
                "publicint" to 333444
        )
        val obj = SomeSubclass()
        ConfiguratorBuilder()
                .addSource(env)
                .addSource(properties)
                .addSource(MockedProperty())
                .build()
                .configure(obj)

        assertEquals(obj.subprivatebyte, -12)
        assertEquals(obj.subpublicint, 12345)
        assertEquals(obj.subprotectedstring, "Hello you")
        assertNull(obj.subnullstring)
        assertNull(obj.subnullinteger)
        assertEquals(obj.subnullint,0)

        assertEquals(obj.privatebyte, 23)
        assertEquals(obj.publicint, 333444)
        assertEquals(obj.otherFieldSameProperty, 333444)
        assertEquals(obj.protectedstring, "hello my colleague")
        assertNull(obj.nullstring)
        assertNull(obj.nullinteger)
        assertEquals(obj.nullint,0)
    }

    @Test
    fun loadPropertiesFromPath() {
        fail("TO DO")
    }

    @Test
    fun loadPropertiesFromInputStream() {
        fail("TO DO")
    }

    @Test
    fun testStaticValues() {
        WithStaticValues.STATIC_VALUE = 0

        val obj = WithStaticValues()
        val conf = ConfiguratorBuilder()
                .addSource(MockedProperty())
                .addSource(mapOf("normalvalue" to "normalvalue"))
                .build()
        conf.configure(obj)
        assertEquals(obj.normalValue, "normalvalue")
        assertEquals(WithStaticValues.STATIC_VALUE, 1234)

        WithStaticValues.STATIC_VALUE = 0
        conf.configure(WithStaticValuesSubClass::class.java)
        assertEquals(WithStaticValues.STATIC_VALUE, 1234)
        assertEquals(WithStaticValuesSubClass.getSubStaticValue(), -12)

    }
    @Test
    fun testFinalValues() {
        val obj = WithFinalValues()

        val conf = ConfiguratorBuilder()
                .addSource(mapOf(
                        "finalValue" to 321,
                        "finalStaticValue" to 123,
                        "finalStaticString" to "hello you"
                ))
                .build()

        conf.configure(WithFinalValues::class.java)
        conf.configure(obj)

        assertEquals(obj.finalValue, 321)
        assertEquals(WithFinalValues.getSupposedlyConstant(), 123)
        assertEquals(WithFinalValues.SUPPOSEDLY_CONSTANT_STRING, "hello you")

    }

    @Test
    fun testBooleans() {
        val conf = ConfiguratorBuilder()
                .addSource(mapOf(
                        "prop.true" to true,
                        "prop.yes" to "yes",
                        "prop.1" to 1,
                        "prop.false" to false,
                        "prop.0" to 0,
                        "prop.no" to "no",
                        "prop.anyOtherProperty" to "tralara"))
                .build()

        val obj1 = WithBooleans()
        conf.configure(obj1)

        assertTrue(obj1.trueProperty)
        assertTrue(obj1.yesProperty)
        assertTrue(obj1.oneProperty)
        assertFalse(obj1.falseProperty)
        assertFalse(obj1.zeroProperty)
        assertFalse(obj1.noProperty)
        assertFalse(obj1.anyOtherProperty)
        assertFalse(obj1.hasToBeFalse)
        assertNull(obj1.hasToBeNull)
    }

    @Test
    fun testChars() {
        class CharClass {
            @Property("char1")
            val char1 : Char = KA.def(',')
            @Property("char2")
            val char2 : Char = KA.def(',')
            @Property("char3")
            val char3 : Char = KA.def(',')
            @Property("char4")
            val char4 : Char = KA.def(',')
        }
        val conf = ConfiguratorBuilder()
                .addSource(mapOf(
                        "char1" to "X",
                        "char2" to 'y',
                        "char3" to "long string"))
                .build()

        val obj1 = CharClass()
        conf.configure(obj1)

        assertEquals(obj1.char1,'X')
        assertEquals(obj1.char2,'y')
        assertEquals(obj1.char3,'l')
        assertEquals(obj1.char4,',')
    }

    @Test
    fun testKotlin() {
        val obj = KonfigurableClass()
        val conf = ConfiguratorBuilder()
                .addSource(mapOf("publicint" to -44321,
                        "privatebyte" to 3,
                        "protectedstring" to "hi",
                        "finalchar" to 'X',
                        "finalstaticint" to 333444,
                        "finalstaticstring" to "hello perraco",
                        "aboolean" to true,
                        "anint" to 6789))
                .build()
        conf.configure(obj)

        assertEquals(obj.publicint, -44321)
        assertEquals(obj.getPrivateByte(), 3)
        assertEquals(obj.getProtectedString(), "hi")
        assertEquals(obj.finalchar, 'X')
        assertEquals(obj.propertynotfound, "default")
        assertEquals(KonfigurableClass.finalstaticint,333444)
        assertEquals(KonfigurableClass.finalstaticstring, "hello perraco")

        conf.configure(KonfigurableObject)
        assertTrue(KonfigurableObject.aboolean)
        assertEquals(KonfigurableObject.anint,6789)
        assertNull(KonfigurableObject.nonfoundproperty)
    }
}