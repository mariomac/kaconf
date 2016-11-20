package info.macias.kaconf.sources;

import info.macias.kaconf.PropertySource;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static java.util.AbstractMap.SimpleEntry;

/**
 * Created by mmacias on 15/11/16.
 */
public abstract class AbstractPropertySource implements PropertySource {

    @FunctionalInterface
    private interface Converter<C> {
        C convert(String value);
    }

    private static <C> SimpleEntry<Class<C>,Converter<C>> entry(Class<C> cClass, Converter<C> converter) {
        return new SimpleEntry<>(cClass, converter);
    }

    protected Map<Class, Converter> converters = Collections.unmodifiableMap(Stream.of(
            entry(Byte.class, Byte::valueOf),
            entry(byte.class, Byte::valueOf),
            entry(Character.class, value -> value.charAt(0)),
            entry(char.class, value -> value.charAt(0)),
            entry(Short.class, Short::valueOf),
            entry(short.class, Short::valueOf),
            entry(Integer.class, Integer::valueOf),
            entry(int.class, Integer::valueOf),
            entry(Long.class, Long::valueOf),
            entry(long.class, Long::valueOf),
            entry(Float.class, Float::valueOf),
            entry(float.class, Float::valueOf),
            entry(Double.class, Double::valueOf),
            entry(double.class, Double::valueOf),
            entry(String.class, value->value)
    ) .collect(Collectors.toMap((e) -> e.getKey(), (e) -> e.getValue())));


    protected abstract String get(String key);

    @Override
    public <T> T get(String key, Class<T> type) {
        String strVal = get(key);
        if(strVal == null) {
            return null;
        }
        @SuppressWarnings("unchecked")
        Converter<T> converter = converters.get(type);
        if(converter == null) {
            throw new IllegalArgumentException("Cannot convert to type: " + type.getName());
        }
        return converter.convert(strVal);
    }
}
