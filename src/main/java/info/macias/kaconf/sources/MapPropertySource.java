package info.macias.kaconf.sources;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mmacias on 20/11/16.
 */
public class MapPropertySource extends AbstractPropertySource {
    private Map<String, ?> properties = new HashMap<>();

    public MapPropertySource(Map<String, ?> properties) {
        this.properties = properties;
    }

    @Override
    public boolean isAvailable() {
        return properties != null;
    }

    @Override
    protected String get(String key) {
        Object val = properties.get(key);
        return val == null ? null : val.toString();
    }
}
