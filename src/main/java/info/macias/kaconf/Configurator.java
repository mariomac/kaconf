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

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Class that handles the configuration of objects
 */
public class Configurator {
    private List<PropertySource> sources;

    /**
     * Instantiates a configurator
     *
     * @param sources a list of {@link PropertySource} instances, in order of priority
     */
    Configurator(List<PropertySource> sources) {
        this.sources = sources;
    }

    /**
     * <p>Configures the object passed as argument. It looks for {@link Property} annotations in the
     * object passed as parameter and assigns the annotated property name present in the
     * {@link PropertySource} with the hightes priority.</p>
     *
     * <p>The invocation of this method will also configure the static fields of the class that
     * the destination object belongs to.</p>
     *
     * @param dst the object to configure
     * @throws ConfiguratorException if an invalid assignment has been intended (for example, assign
     *                               an alphanumeric value into an integer) or if the access to the destination field is
     *                               enforced by Java language access control and the underlying field is either inaccessible
     *                               or final.
     */
    public void configure(Object dst) {
        // Configure properties for this class and its superclasses
        for (Class c = dst.getClass(); c != null; c = c.getSuperclass()) {
            configure(dst, c);
        }
    }

    /**
     * Configures the static fields of a class passed as an argument, in a similar manner to
     * {@link #configure(Object)}
     *
     * @param clazz The class whose static fields have to be configured
     */
    public void configure(Class<?> clazz) {
        while (clazz != null) {
            configure(null, clazz);
            clazz = clazz.getSuperclass();
        }
    }

    private void configure(Object dst, Class clazz) {
        boolean jvmAllowsAccessingModifiers = true;
        boolean isModifiersFieldAccessible = true;
        Field modifiersField = null;
        try {
            modifiersField = Field.class.getDeclaredField("modifiers");
            isModifiersFieldAccessible = modifiersField.isAccessible();
            modifiersField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            // this can happen because the security manager disallows this operation
            // e.g. in Java >=12
            jvmAllowsAccessingModifiers = false;
        }

        for (Field f : clazz.getDeclaredFields()) {
            int fieldModifiers = f.getModifiers();
            if (dst != null || Modifier.isStatic(fieldModifiers)) {

                // Make accessible final static members, if possible
                boolean modifiersChanged = false;
                boolean modifiableField = true;
                try {
                    if (Modifier.isFinal(fieldModifiers) && Modifier.isStatic(fieldModifiers)) {
                        if (jvmAllowsAccessingModifiers) {
                            modifiersField.setInt(f, fieldModifiers & ~Modifier.FINAL);
                            modifiersChanged = true;
                        } else {
                            modifiableField = false;
                        }
                    }
                } catch (IllegalAccessException e) {
                    throw new ConfiguratorException(e);
                }
                final boolean finalModifiableField = modifiableField;
                Stream.of(f.getAnnotations())
                        .filter(a -> a.annotationType().isAssignableFrom(Property.class))
                        .map(a -> (Property) a)
                        .findFirst().flatMap(p -> findPriorValue(p.value(), f.getType()))
                        .ifPresent(value -> {
                            if (!finalModifiableField) {
                                throw new ConfiguratorException(
                                        "This JVM disallows writing final field: " + f.getName());
                            }
                            boolean isAcessible = f.isAccessible();
                            if (!isAcessible) {
                                f.setAccessible(true);
                            }


                            try {
                                f.set(dst, value);
                            } catch (NumberFormatException |
                                     IllegalAccessException e) {
                                throw new ConfiguratorException(e);
                            }
                            if (!isAcessible) {
                                f.setAccessible(false);
                            }
                        });

                // Restore final static members
                if (modifiersChanged) {
                    try {
                        modifiersField.setInt(f, fieldModifiers);
                    } catch (IllegalAccessException e) {
                        throw new ConfiguratorException(e);
                    }
                }

            }
        }
        if (jvmAllowsAccessingModifiers) {
            modifiersField.setAccessible(isModifiersFieldAccessible);
        }
    }

    // finds any of the keys into the properties sources, returning the values by priority
    private <T> Optional<T> findPriorValue(String[] keys, Class<T> pType) {
        return sources.stream()
                .map(ps -> {
                    for (String key : keys) {
                        T type = ps.get(key, pType);
                        if (type != null) {
                            return type;
                        }
                    }
                    return null;
                })
                .filter(value -> value != null)
                .findFirst();
    }
}
