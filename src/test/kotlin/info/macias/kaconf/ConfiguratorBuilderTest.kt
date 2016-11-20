package info.macias.kaconf

import info.macias.kaconf.sources.JavaUtilPropertySource
import junit.framework.TestCase
import org.junit.Test
import java.io.FileInputStream

/**
 * Created by mmacias on 16/11/16.
 */
class ConfiguratorBuilderTest : TestCase("Test Configurator Builder") {

    fun testInvalidBuild() {
        assertTrue(try {
            ConfiguratorBuilder().build()
            false
        } catch (e:ConfiguratorException) {
            true
        })

        assertTrue(try {
            ConfiguratorBuilder()
                    .addSource(JavaUtilPropertySource("someUnexistentFile.txt"))
                    .addSource(JavaUtilPropertySource(javaClass.getResourceAsStream("UnexistentResource.txt")))
                    .build()
            false
        } catch(e:ConfiguratorException) {
            true
        })
    }


}