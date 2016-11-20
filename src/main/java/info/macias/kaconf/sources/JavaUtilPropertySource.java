package info.macias.kaconf.sources;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by mmacias on 20/11/16.
 */
public class JavaUtilPropertySource extends AbstractPropertySource {
    private Properties properties;

    public JavaUtilPropertySource(String filePath) {
        try(FileInputStream fis = new FileInputStream(filePath)) {
            properties.load(fis);
        } catch (IOException e) {
            properties = null;
        }
    }
    /**
     * If the properties cannot be loaded, no exception will be thrown
     * but the object will be marked as unavailable and the properties
     * won't be considered
     * @param is
     */
    public JavaUtilPropertySource(InputStream is) {
        try {
            properties.load(is);
        } catch (NullPointerException | IOException e) {
            properties = null;
        }
    }
    public JavaUtilPropertySource(Properties properties) {
        this.properties = properties;
    }

    @Override
    public boolean isAvailable() {
        return properties != null;
    }

    @Override
    protected String get(String key) {
        return properties.getProperty(key);
    }
}
