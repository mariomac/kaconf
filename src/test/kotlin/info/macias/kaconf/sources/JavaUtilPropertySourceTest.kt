package info.macias.kaconf.sources

import info.macias.kaconf.ConfiguratorBuilder
import info.macias.kaconf.Property
import org.junit.Test
import java.io.File
import java.net.URI
import java.net.URL
import kotlin.test.assertEquals

class JavaUtilPropertySourceTest {

    class Project {

        @Property("project.name")
        var name: String? = null

        @Property("project.uri")
        val uri: URI? = null
    }

    @Test
    fun `test can create source from InputStream (deprecated constructor)`() {
        var source : JavaUtilPropertySource? = null
        javaClass.getResourceAsStream("/javautil.properties").use {
            source = JavaUtilPropertySource(it)
        }

        val project = Project()
        val configurator = ConfiguratorBuilder()
                .addSource(source!!)
                .build()
        configurator.configure(project)

        assertEquals("kaconf", project.name)
        assertEquals(URI.create("https://github.com/mariomac/kaconf"), project.uri)
    }

    @Test
    fun `test can create source from InputStream`() {
        var source : JavaUtilPropertySource? = null
        javaClass.getResourceAsStream("/javautil.properties").use {
            source = JavaUtilPropertySource.from(it).get()
        }

        val project = Project()
        val configurator = ConfiguratorBuilder()
                .addSource(source!!)
                .build()
        configurator.configure(project)

        assertEquals("kaconf", project.name)
        assertEquals(URI.create("https://github.com/mariomac/kaconf"), project.uri)
    }

    @Test
    fun `test can create source from File`() {
        val res: URL = javaClass.getResource("/javautil.properties")
        val f = File(res.toURI())
        val source = JavaUtilPropertySource.from(f).get()

        val project = Project()
        val configurator = ConfiguratorBuilder()
                .addSource(source)
                .build()
        configurator.configure(project)

        assertEquals("kaconf", project.name)
        assertEquals(URI.create("https://github.com/mariomac/kaconf"), project.uri)
    }
}