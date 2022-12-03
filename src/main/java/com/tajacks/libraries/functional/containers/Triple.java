package com.tajacks.libraries.functional.containers;

import java.util.Objects;

/**
 * Holds three non-null objects
 * @param _1 The first object, non-null
 * @param _2 The second object, non-null
 * @param _3 The third object, non-null
 * @param <T> The type of the first object
 * @param <U> The type of the second object
 * @param <V> The type of the third object
 */
public record Triple<T, U, V>(T _1, U _2, V _3) {

    public Triple {
        Objects.requireNonNull(_1);
        Objects.requireNonNull(_2);
        Objects.requireNonNull(_3);
    }
}
