package info.macias.contifus

import junit.framework.TestCase
import org.junit.Test

/**
 * Created by mmacias on 16/11/16.
 */
class ConfiguratorBuilderTest : TestCase("Test Configurator Builder") {

    @Test(expected = ConfiguratorException::class)
    fun testInvalidBuild() {
        println("Hola nen")
        //ConfiguratorBuilder().build()
    }

    @Test
    fun testNullResults() {

    }

}