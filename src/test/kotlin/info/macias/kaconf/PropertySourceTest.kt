package info.macias.kaconf

import info.macias.kaconf.sources.AbstractPropertySource
import junit.framework.TestCase
import org.junit.Test

/**
 * Created by mmacias on 16/11/16.
 */
class PropertySourceTest : TestCase("Test Property Sources") {
    val b: Byte = -123
    val c: Char = '!'
    val s: Short = 30000
    val i: Int = -2000000000
    val l: Long = 30000000000
    val str = "ola ke ase"
    val properties = object : AbstractPropertySource() {
        val keyVals = mapOf(
                "b" to b.toString(),
                "c" to c.toString(),
                "s" to s.toString(),
                "i" to i.toString(),
                "l" to l.toString(),
                "str" to str
        )

        override fun get(key: String?): String? = keyVals.get(key)
        override fun isAvailable() = true
    }

    @Test
    fun testPropertyConversion() {
        assertEquals(properties.get("b", java.lang.Byte::class.java), b)
        assertEquals(properties.get("c", java.lang.Character::class.java), c)
        assertEquals(properties.get("s", java.lang.Short::class.java), s)
        assertEquals(properties.get("i", java.lang.Integer::class.java), i)
        assertEquals(properties.get("l", java.lang.Long::class.java), l)
        assertEquals(properties.get("str", java.lang.String::class.java), str)
    }

    @Test
    fun testNullResults() {
        assertNull(properties.get("abc", java.lang.Byte::class.java))
        assertNull(properties.get("def", java.lang.Character::class.java))
        assertNull(properties.get("ghi", java.lang.Short::class.java))
        assertNull(properties.get("jkl", java.lang.Integer::class.java))
        assertNull(properties.get("mno", java.lang.Long::class.java))
        assertNull(properties.get("pqr", java.lang.String::class.java))
    }

    @Test(expected = IllegalArgumentException::class)
    fun testUnexistingConverters() {
        var illegalArgumentExceptionThrown = false;

        try {
            properties.get("b", Object::class.java)
        } catch (e:ConfiguratorException) {
            illegalArgumentExceptionThrown = true;
        }
        assert(illegalArgumentExceptionThrown)
    }
}