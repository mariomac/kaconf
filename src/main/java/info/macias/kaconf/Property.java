package info.macias.kaconf;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation interface that indicates that a field will get its value from
 * a {@link Configurator} instance.
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Property {
    /**
     * The name of the property that provides the value to the field this annotation target
     * @return The name of the property that provides the value to the field this annotation target
     */
    String value();
}
