package info.macias.kaconf;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Stream;

/**
 * Class that handles the configuration of objects
 */
public class Configurator {
    private List<PropertySource> sources;

    /**
     * Instantiates a configuration
     * @param sources a list of {@link PropertySource} instances, in order of priority
     */
    Configurator(List<PropertySource> sources) {
        this.sources = sources;
    }

    /**
     * Configures the object passed as argument. It looks for {@link Property} annotations in the
     * object passed as parameter.
     *
     * @param dst the object to configure
     */
    public void configure(Object dst) {
        // Configure properties for this class and its superclasses
        for (Class c = dst.getClass(); c != null; c = c.getSuperclass()) {
            configure(dst, c);
        }
    }

    private void configure(Object dst, Class clazz) {
        for (Field f : clazz.getDeclaredFields()) {
            Stream.of(f.getAnnotations())
                    .filter(a -> a.annotationType().isAssignableFrom(Property.class))
                    .map(a -> (Property) a)
                    .findFirst()
                    .ifPresent(p ->
                            sources.stream()
                                    .map(ps -> ps.get(p.value(), f.getType()))
                                    .filter(value -> value != null)
                                    .findFirst()
                                    .ifPresent(value -> {
                                        boolean isAcessible = f.isAccessible();
                                        f.setAccessible(true);
                                        try {
                                            f.set(dst, value);
                                        } catch (IllegalAccessException e) {
                                            throw new ConfiguratorException(e);
                                        }
                                        f.setAccessible(isAcessible);
                                    })
                    );
        }
    }
}
