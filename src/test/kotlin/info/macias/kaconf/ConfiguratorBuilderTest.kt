package info.macias.kaconf

import info.macias.kaconf.sources.JavaUtilPropertySource
import junit.framework.TestCase

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
                    .addSource(JavaUtilPropertySource.from("someUnexistentFile.txt"))
                    .addSource(JavaUtilPropertySource.from(javaClass.getResourceAsStream("UnexistentResource.txt")))
                    .build()
            false
        } catch(e:ConfiguratorException) {
            true
        })
    }

    fun testInvalidBuildDeprecatedConstructors() {
        assertTrue(try {
            ConfiguratorBuilder().build()
            false
        } catch (e:ConfiguratorException) {
            true
        })

        assertTrue(try {
            ConfiguratorBuilder()
                    .addSource(JavaUtilPropertySource.from("someUnexistentFile.txt"))
                    .addSource(JavaUtilPropertySource.from(javaClass.getResourceAsStream("UnexistentResource.txt")))
                    .build()
            false
        } catch(e:ConfiguratorException) {
            true
        })
    }

}