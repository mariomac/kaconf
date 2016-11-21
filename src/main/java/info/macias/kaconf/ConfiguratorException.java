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
