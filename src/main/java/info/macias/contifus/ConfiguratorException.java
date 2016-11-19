package info.macias.contifus;

/**
 * Created by mmacias on 18/11/16.
 */
public class ConfiguratorException extends RuntimeException {
    public ConfiguratorException() {
        super();
    }

    public ConfiguratorException(String message) {
        super(message);
    }

    public ConfiguratorException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfiguratorException(Throwable cause) {
        super(cause);
    }

    protected ConfiguratorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
