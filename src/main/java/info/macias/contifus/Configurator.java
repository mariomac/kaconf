package info.macias.contifus;

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
        configure(dst,dst.getClass());
    }

    private void configure(Object dst, Class clazz) {
        for (Field f :clazz.getDeclaredFields()) {
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
                                            throw new RuntimeException(e);
                                        }
                                        f.setAccessible(isAcessible);
                                    })
                    );
        }
    }
}
