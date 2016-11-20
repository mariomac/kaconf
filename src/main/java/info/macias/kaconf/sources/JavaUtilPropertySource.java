package info.macias.kaconf.sources;

import java.util.Properties;

/**
 * Created by mmacias on 20/11/16.
 */
public class JavaUtilPropertySource extends AbstractPropertySource {
    private Properties properties;

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
