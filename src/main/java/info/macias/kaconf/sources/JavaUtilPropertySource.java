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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
     * @param filePath The path to reach the Properties file.
     */
    public JavaUtilPropertySource(String filePath) {
        this(new File(filePath));
    }

    /**
     * <p>Instantiates the class by loading the File specified as argument.</p>
     * <p>If the {@link java.util.Properties} cannot be loaded (e.g. because the file does not exist or
     * the user does not have permissions), no exceptions will be thrown and the object will be
     * instantiated anyway. However, the {@link JavaUtilPropertySource#isAvailable()} method will
     * return <code>false</code>.</p>
     * @param filePath The path to reach the Properties file.
     */
    public JavaUtilPropertySource(File file) {
        if (file == null || !file.isFile())
            throw new IllegalArgumentException("'" + file + "' is not a valid file.");
        try(FileInputStream fis = new FileInputStream(file)) {
            Properties props = new Properties();
            props.load(fis);
            properties = props;
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
     * @param is The {@link InputStream} to access the Properties file
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
     * <p>Instantiates the class to handle the {@link java.util.Properties} object passed as an
     * argument</p>
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
     *          loaded. <code>false</code> otherwise
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
        return properties.getProperty(key);
    }
}
