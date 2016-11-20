package info.macias.kaconf;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by mmacias on 16/11/16.
 */
public class Configurator {
    private List<PropertySource> sources;

    Configurator(List<PropertySource> sources) {
        this.sources = sources;
    }

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
