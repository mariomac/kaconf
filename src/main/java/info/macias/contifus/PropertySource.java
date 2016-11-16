package info.macias.contifus;

/**
 * Created by mmacias on 16/11/16.
 */
public interface PropertySource {
    <T> T get(String key, Class<T> type);
}
