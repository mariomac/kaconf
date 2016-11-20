package info.macias.kaconf;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mmacias on 18/11/16.
 */
public class ConfiguratorBuilder {
    public List<PropertySource> psList = new ArrayList<>();
    public ConfiguratorBuilder addPropertySource(PropertySource ps) {
        psList.add(ps);
        return this;
    }
    public Configurator build() {
        if(psList.size() < 0) {
            throw new ConfiguratorException("You must specify at least one Property Source");
        }
        return new Configurator(psList);
    }
}
