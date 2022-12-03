package com.tajacks.libraries.functional.common;

import java.util.Objects;

/**
 * An appropriately named replacement for {@link java.util.function.Consumer}
 *
 * @param <T> The type of input to the effect
 */
@FunctionalInterface
public interface Effect<T> {

    /**
     * Applies this effect on the given argument
     *
     * @param input The input argument
     */
    void apply(T input);

    /**
     * Returns a composed Effect that applies this effect followed by the after effect
     *
     * @param after The effect to apply after this one
     * @return A composed Effect that applies this effect followed by the after effect.
     * @throws NullPointerException if after is null
     */
    default Effect<T> andThen(Effect<? super T> after) {
        Objects.requireNonNull(after);
        return t -> {
            apply(t);
            after.apply(t);
        };
    }

    /**
     * Composes two Effects together, creating an effect which applies the first effect followed by the second effect
     *
     * @param first  The first Effect to apply
     * @param second The second Effect to apply
     * @param <T>    The type of the effect
     * @return A composed Effect that applies the first effect followed by the second effect
     */
    static <T> Effect<T> andThen(Effect<T> first, Effect<? super T> second) {
        return first.andThen(second);
    }
}
