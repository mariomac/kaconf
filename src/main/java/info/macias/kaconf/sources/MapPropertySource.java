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

import java.util.HashMap;
import java.util.Map;

/**
 * Wrapper that allows handling {@link java.util.Map} instances though
 * {@link info.macias.kaconf.PropertySource} interface
 */
public class MapPropertySource extends AbstractPropertySource {
    private Map<String, ?> properties = new HashMap<>();

    /**
     * Instantiates the class by providing a map with the property values
     * @param properties The properties to access later
     */
    public MapPropertySource(Map<String, ?> properties) {
        this.properties = properties;
    }


    /**
     * {@inheritDoc}
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
        Object val = properties.get(key);
        return val == null ? null : val.toString();
    }
}
