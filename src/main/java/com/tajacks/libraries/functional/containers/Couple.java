package com.tajacks.libraries.functional.containers;

import java.util.Objects;

/**
 * Holds two non-null objects
 * @param _1 The first object, non-null
 * @param _2 The second object, non-null
 * @param <T> The type of the first object
 * @param <U> The type of the second object
 */
public record Couple<T, U>(T _1, U _2) {

    public Couple {
        Objects.requireNonNull(_1);
        Objects.requireNonNull(_2);
    }
}
