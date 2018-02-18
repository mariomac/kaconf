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

import java.io.*;
import java.util.Optional;
import java.util.Properties;

/**
 * Wrapper that allows handling {@link java.util.Properties} instances though
 * {@link info.macias.kaconf.PropertySource} interface
 */
public class JavaUtilPropertySource extends AbstractPropertySource {
    private Properties properties;

    /**
     * <p>Instantiates the class by loading the File specified as argument.</p>
     * <p>If the {@link java.util.Properties} cannot be loaded (e.g. because the file does not exist or
     * the user does not have permissions), no exceptions will be thrown and the object will be
     * instantiated anyway. However, the {@link JavaUtilPropertySource#isAvailable()} method will
     * return <code>false</code>.</p>
     *
     * @param filePath The path to reach the Properties file.
     * @deprecated Use {@link #from(String)} instead. This constructor will be removed in version 0.9.0.
     */
    public JavaUtilPropertySource(String filePath) {
        try(FileInputStream fis = new FileInputStream(filePath)) {
            properties.load(fis);
        } catch (IOException e) {
            properties = null;
        }
    }
    /**
     * <p>Instantiates the class by loading the {@link java.util.Properties} object that is
     * reachable by the {@link InputStream} specified as argument</p>
     * <p>If the {@link java.util.Properties} cannot be loaded (e.g. because the file format is wrong or
     * the user does not have permissions), no exceptions will be thrown and the object will be
     * instantiated anyway. However, the {@link JavaUtilPropertySource#isAvailable()} method will
     * return <code>false</code>.</p>
     *
     * @param is The {@link InputStream} to access the Properties file
     * @deprecated Use {@link #from(InputStream)} instead. This constructor will be removed in version 0.9.0.
     */
    public JavaUtilPropertySource(InputStream is) {
        try {
            Properties props = new Properties();
            props.load(is);
            properties = props;
        } catch (NullPointerException | IOException e) {
            properties = null;
        }
    }

    /**
     * <p>Creates a properties source by loading the properties file with the path that is specified as argument.</p>
     *
     * @param filePath The path to reach the Properties file.
     * @return An {@link Optional} containing the property source or empty if
     *         the property source is not accessible (e.g. because it does not
     *         exist or there are not permissions).
     */
    public static Optional<JavaUtilPropertySource> from(String filePath) {
        if (filePath == null) {
            throw new IllegalArgumentException("File Path can't be null");
        }
        File file = new File(filePath);
        return from(file);
    }

    /**
     * <p>Creates a properties source by loading the File specified as argument.</p>
     *
     * @param file The {@link File} object to reach the Properties file.
     * @return An {@link Optional} containing the property source or empty if
     *         the property source is null or not accessible (e.g. because it does not
     *         exist or there are not permissions).
     */
    public static Optional<JavaUtilPropertySource> from(File file) {
        if (file == null || !file.isFile())
            return Optional.empty();
        try (FileInputStream fis = new FileInputStream(file)) {
            Properties properties;
            properties = new Properties();
            properties.load(fis);
            return Optional.of(new JavaUtilPropertySource(properties));
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    /**
     * <p>Creates a properties source by loading the {@link java.util.Properties} object that is
     * reachable by the {@link InputStream} specified as argument</p>
     *
     * @param is The {@link InputStream} to access the Properties file
     * @return An {@link Optional} containing the property source or empty if
     *         the property source is null or not accessible (e.g. because it does not
     *         exist or there are not permissions).
     */
    public static Optional<JavaUtilPropertySource> from(InputStream is) {
        if (is == null) {
            return Optional.empty();
        }
        try {
            Properties properties;
            properties = new Properties();
            properties.load(is);
            return Optional.of(new JavaUtilPropertySource(properties));
        } catch (NullPointerException | IOException e) {
            return Optional.empty();
        }
    }


    /**
     * <p>Instantiates the class to handle the {@link java.util.Properties} object passed as an
     * argument</p>
     * <p>If the properties argument is null, the {@link #isAvailable()} method will
     * always return {@code false}</p>
     * @param properties The properties that will be handled by the instantiated object
     */
    public JavaUtilPropertySource(Properties properties) {
        this.properties = properties;
    }

    /**
     * Returns <code>true</code> if the {@link java.util.Properties} object has been correctly
     * loaded. Otherwise, returns <code>false</code>.
     *
     * @return <code>true</code> if the {@link java.util.Properties} object has been correctly
     * loaded. <code>false</code> otherwise
     */
    @Override
    public boolean isAvailable() {
        return properties != null;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    protected String get(String key) {
        if (!isAvailable()) {
            return null;
        }
        return properties.getProperty(key);
    }
}
