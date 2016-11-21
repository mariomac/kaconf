package info.macias.kaconf.sources;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Wrapper that allows handling {@link java.util.Properties} instances though
 * {@link info.macias.kaconf.PropertySource} interface
 */
public class JavaUtilPropertySource extends AbstractPropertySource {
    private Properties properties;

    /**
     * <p>Instantiates the class by loading a {@link java.util.Properties} specified as argument.</p>
     * <p>If the {@link java.util.Properties} cannot be loaded (e.g. because the file does not exist or
     * the user does not have permissions), no exceptions will be thrown and the object will be
     * instantiated anyway. However, the {@link JavaUtilPropertySource#isAvailable()} method will
     * return <code>false</code>.</p>
     * @param filePath The path to reach the Properties file.
     */
    public JavaUtilPropertySource(String filePath) {
        try(FileInputStream fis = new FileInputStream(filePath)) {
            properties.load(fis);
        } catch (IOException e) {
            properties = null;
        }
    }
    /**
     * <p>Instantiates the class by loading the {@link java.util.Properties} object that is
     * reachable by the {@link InputStream} specified as argument</p>
     * <p>If the {@link java.util.Properties} cannot be loaded (e.g. because the file format is wrong or
     * the user does not have permissions), no exceptions will be thrown and the object will be
     * instantiated anyway. However, the {@link JavaUtilPropertySource#isAvailable()} method will
     * return <code>false</code>.</p>
     * @param is The {@link InputStream} to access the Properties file
     */
    public JavaUtilPropertySource(InputStream is) {
        try {
            properties.load(is);
        } catch (NullPointerException | IOException e) {
            properties = null;
        }
    }

    /**
     * <p>Instantiates the class to handle the {@link java.util.Properties} object passed as an
     * argument</p>
     * @param properties The properties that will be handled by the instantiated object
     */
    public JavaUtilPropertySource(Properties properties) {
        this.properties = properties;
    }

    /**
     * Returns <code>true</code> if the {@link java.util.Properties} object has been correctly
     * loaded. Otherwise, returns <code>false</code>.
     *
     * @return <code>true</code> if the {@link java.util.Properties} object has been correctly
     *          loaded. <code>false</code> otherwise
     */
    @Override
    public boolean isAvailable() {
        return properties != null;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    protected String get(String key) {
        return properties.getProperty(key);
    }
}
