package info.macias.kaconf;

import info.macias.kaconf.sources.JavaUtilPropertySource;
import info.macias.kaconf.sources.MapPropertySource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by mmacias on 18/11/16.
 */
public class ConfiguratorBuilder {
    public List<PropertySource> psList = new ArrayList<>();

    public ConfiguratorBuilder addPropertySource(Map<String,?> map) {
        return addPropertySource(new MapPropertySource(map));
    }
    public ConfiguratorBuilder addPropertySource(Properties properties) {
        return addPropertySource(new JavaUtilPropertySource(properties));
    }
    public ConfiguratorBuilder addPropertySource(PropertySource ps) {
        if(ps.isAvailable()) {
            psList.add(ps);
        }
        return this;
    }

    public Configurator build() {
        if(psList.size() < 1) {
            throw new ConfiguratorException("You must specify at least one Property Source");
        }
        return new Configurator(psList);
    }

}
