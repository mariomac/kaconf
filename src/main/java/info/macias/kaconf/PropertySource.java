/*
    Copyright 2016-2020 Mario Macías

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
package info.macias.kaconf;

/**
 * <p>Interface that defines the basic functionalities of a source of properties
 * (e.g. a {@link java.util.Map}, a {@link java.util.Properties} file, ...)</p>
 * <p>Implementors of this interface should silently fail if the source of properties
 * is not available during its instantiation (for example, a java properties file is not
 * available. The class will be instantiated without throwing any exception and then the
 * {@link #isAvailable()} method would return {@code false}</p>
 * <p>For a simpler use, implementing classes should better extend
 * {@link info.macias.kaconf.sources.AbstractPropertySource}</p> */
public interface PropertySource {

    /**
     * <p>Gets the value of the property whose name is defined by the <code>name</code> argument, transforming
     * it to the type of the class passed as second argument.</p>
     *
     * <p>If the requested type cannot be converted, it should throw a {@link ConfiguratorException}. Other
     * exceptions may raise, e.g. {@link NumberFormatException} when trying to convert an alphanumeric property
     * into an <code>int</code>.</p>
     *
     * @param name The name of the property to return
     * @param type The class object of the property to return
     * @param <T> The returned type
     * @return The property stored for the given name, as an instance of the given type, <code>null</code> if a property
     * with such name does not exist
     */
    <T> T get(String name, Class<T> type);

    /**
     * Returns whether the property source is available. For example, a property source is <em>unavailable</em>
     * if the file that contains the properties has not been correctly read, or the object that stores the
     * property sources (e.g. a {@link java.util.Map}) is <code>null</code>.
     * @return <code>true</code> if the property source is available. <code>false</code> otherwise
     */
    boolean isAvailable();
}
