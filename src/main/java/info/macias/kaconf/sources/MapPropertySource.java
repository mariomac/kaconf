package info.macias.kaconf.sources;

import java.util.HashMap;
import java.util.Map;

/**
 * Wrapper that allows handling {@link java.util.Map} instances though
 * {@link info.macias.kaconf.PropertySource} interface
 */
public class MapPropertySource extends AbstractPropertySource {
    private Map<String, ?> properties = new HashMap<>();

    /**
     * Instantiates the class by providing a map with the property values
     * @param properties The properties to access later
     */
    public MapPropertySource(Map<String, ?> properties) {
        this.properties = properties;
    }


    /**
     * {@inheritDoc}
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
        Object val = properties.get(key);
        return val == null ? null : val.toString();
    }
}
