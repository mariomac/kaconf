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
 * Unchecked exception type to handle with Configurator-related issues and failures
 */
public class ConfiguratorException extends RuntimeException {
    /**
     * {@inheritDoc}
     */
    public ConfiguratorException() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    public ConfiguratorException(String message) {
        super(message);
    }

    /**
     * {@inheritDoc}
     */
    public ConfiguratorException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * {@inheritDoc}
     */
    public ConfiguratorException(Throwable cause) {
        super(cause);
    }

    /**
     * {@inheritDoc}
     */
    protected ConfiguratorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
