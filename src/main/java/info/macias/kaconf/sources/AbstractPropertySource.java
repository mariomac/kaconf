/*
    Copyright 2016 Mario Macías

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/
package info.macias.kaconf.sources;

import info.macias.kaconf.ConfiguratorException;
import info.macias.kaconf.PropertySource;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static java.util.AbstractMap.SimpleEntry;

/**
 * <p>Class that implements some basic functionalities of the {@link PropertySource} interface,
 * such as converting string values to java basic types.</p>
 * <p>Generally, new property sources should extend this class.</p>
 */
public abstract class AbstractPropertySource implements PropertySource {

    @FunctionalInterface
    private interface Converter<C> {
        C convert(String value);
    }

    private static <C> SimpleEntry<Class<C>,Converter<C>> entry(Class<C> cClass, Converter<C> converter) {
        return new SimpleEntry<>(cClass, converter);
    }

    private static boolean convertBool(String str) {
        if(str != null) {
            str = str.trim().toLowerCase();
            return str.equals("true") || str.equals("yes") || str.equals("1");
        }
        return false;
    }
    private Map<Class, Converter> converters = Collections.unmodifiableMap(Stream.of(
            entry(Boolean.class, AbstractPropertySource::convertBool),
            entry(boolean.class, AbstractPropertySource::convertBool),
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


    /**
     * <p>Gets the string representation of a property whose name is defined by the <code>name</code> argument</p>
     * <p>This method will be invoked by {@link #get(String, Class)} to transform the String value into
     * a numeric/char value</p>
     * @param name The name of the property to return
     * @return The property stored for the given name, as a String, <code>null</code> if a property
     * with such name does not exist
     */
    protected abstract String get(String name);

    /**
     * <p>Gets the value of the property whose name is defined by the <code>name</code> argument, transforming
     * it to the type of the class passed as second argument.</p>
     *
     * <p>If the requested type cannot be converted, it will throw a {@link ConfiguratorException}. Other
     * exceptions may raise, e.g. {@link NumberFormatException} when trying to convert an alphanumeric property
     * into an <code>int</code>.</p>
     *
     * @param name The name of the property to return
     * @param type The class object of the property to return
     * @param <T> The returned type
     * @return The property stored for the given name, as an instance of the given type, <code>null</code> if a property
     * with such name does not exist
     * @throws ConfiguratorException if an invalid type conversion has been intended (for example,
     *         trying to return a non-basic, non-String type)
     * @throws NumberFormatException or trying to convert an alphanumeric property value to a Number
     */
    @Override
    public <T> T get(String name, Class<T> type) {
        String strVal = get(name);
        if(strVal == null) {
            return null;
        }
        @SuppressWarnings("unchecked")
        Converter<T> converter = converters.get(type);
        if(converter == null) {
            throw new ConfiguratorException("Cannot convert to type: " + type.getName());
        }
        return converter.convert(strVal);
    }
}
