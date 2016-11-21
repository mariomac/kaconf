package info.macias.kaconf;

import info.macias.kaconf.sources.JavaUtilPropertySource;
import info.macias.kaconf.sources.MapPropertySource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Builder class to create {@link Configurator} instances
 */
public class ConfiguratorBuilder {
    private List<PropertySource> psList = new ArrayList<>();

    /**
     * <p>Adds a property source whose values are provided by a {@link Map} object</p>
     * <p>The priority of the resultant {@link PropertySource} will have less priority to the ones
     * added by the previous calls to the variants of <code>addSource</code> methods, but more
     * priority to the sources added by the further calls to <code>addSource</code>.</p>
     * <p>If the resulting value of invoking {@link PropertySource#isAvailable()} is
     * <code>false</code>, the properties source won't be added to the chain of {@link PropertySource}</p>
     * @param map The source values for the properties, as a {@link Map}
     * @return A reference same <code>ConfigurationBuilder</code> that has been invoked
     */
    public ConfiguratorBuilder addSource(Map<String,?> map) {
        return addSource(new MapPropertySource(map));
    }
    /**
     * <p>Adds a property source whose values are provided by a {@link java.util.Properties} object</p>
     * <p>The priority of the resultant {@link PropertySource} will have less priority to the ones
     * added by the previous calls to the variants of <code>addSource</code> methods, but more
     * priority to the sources added by the further calls to <code>addSource</code>.</p>
     * <p>If the resulting value of invoking {@link PropertySource#isAvailable()} is
     * <code>false</code>, the properties source won't be added to the chain of {@link PropertySource}</p>
     * @param properties The source values for the properties, as a {@link java.util.Properties}
     * @return A reference same <code>ConfigurationBuilder</code> that has been invoked
     */
    public ConfiguratorBuilder addSource(Properties properties) {
        return addSource(new JavaUtilPropertySource(properties));
    }

    /**
     * <p>Adds a property source whose values are provided by a {@link PropertySource} object</p>
     * <p>The priority of the added {@link PropertySource} will have less priority to the ones
     * added by the previous calls to the variants of <code>addSource</code> methods, but more
     * priority to the sources added by the further calls to <code>addSource</code>.</p>
     * <p>If the resulting value of invoking {@link PropertySource#isAvailable()} is
     * <code>false</code>, the properties source won't be added to the chain of {@link PropertySource}</p>
     * @param propertySource The source values for the properties
     * @return A reference same <code>ConfigurationBuilder</code> that has been invoked
     */
    public ConfiguratorBuilder addSource(PropertySource propertySource) {
        if(propertySource.isAvailable()) {
            psList.add(propertySource);
        }
        return this;
    }

    /**
     * Builds a {@link Configurator} instance that accesses the added {@link PropertySource} according
     * to their given priority.
     * @return A new instance of {@link Configurator}
     * @throws ConfiguratorException if no available property sources have been provided
     */
    public Configurator build() {
        if(psList.size() < 1 || psList.stream().filter(PropertySource::isAvailable).count() == 0) {
            throw new ConfiguratorException("No available property sources provided");
        }
        return new Configurator(psList);
    }

}
