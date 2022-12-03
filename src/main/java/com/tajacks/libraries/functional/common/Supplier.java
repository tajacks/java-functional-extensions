package com.tajacks.libraries.functional.common;

/**
 * Carbon copy of {@link java.util.function.Supplier}
 * A copy of the built-in Java supplier is used for flexibility if we wish to
 * extend functionality in the future. By building other classes off of this supplier,
 * that flexibility is granted
 *
 * @param <T> The type of element to supply from this supplier
 */
@FunctionalInterface
public interface Supplier<T> {

    /**
     * Gets a result
     * @return An element of type T
     */
    T get();
}
