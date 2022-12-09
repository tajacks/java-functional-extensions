package com.tajacks.libraries.functional.common;

/**
 * A function that takes an element and produces a result
 *
 * @param <T> The type of element to apply the function against
 * @param <R> The type of element resulting from applying the function to the element T
 */
@FunctionalInterface
public interface Function<T, R> {

    /**
     * Applies this function against the element T, resulting in an R
     *
     * @param arg The argument to apply this function against
     * @return The result of applying this function
     */
    R apply(T arg);

    /**
     * Returns a composed function which applies this function against the product
     * of applying the given function against an input element
     *
     * @param f The function to first apply to an input element
     * @return The composed function which applies this function against the product
     * of applying the given function against an input element
     */
    default <V> Function<V, R> compose(Function<? super V, ? extends T> f) {
        return x -> apply(f.apply(x));
    }

    /**
     * Returns a composed function which applies the given function
     * against the product of applying this function against an input element
     *
     * @param f
     * @return A composed function which applies the given function
     * against the product of applying this function against an input element
     */
    default <V> Function<T, V> andThen(Function<? super R, ? extends V> f) {
        return x -> f.apply(apply(x));
    }

    /**
     * Returns a function which returns its input argument
     *
     * @return a function which returns its input argument
     */
    static <T> Function<T, T> identity() {
        return t -> t;
    }

    /**
     * Returns a composed function which first applies the second argument passed to this method to an input,
     * then, applies the first argument supplied to this method against the product of the previous function
     * application
     *
     * @param functionOne The function to apply to the result of applying functionTwo
     * @param functionTwo The first function to apply to an element
     * @return A composed function which applies functionOne to the product of applying functionTwo to an input element
     */
    static <T, R, V> Function<V, R> compose(Function<T, R> functionOne, Function<V, T> functionTwo) {
        return x -> functionOne.apply(functionTwo.apply(x));
    }

    /**
     * Returns a composed function that first applies the first parameter to this method
     * to an input element, followed by applying the second parameter to the product of the
     * aforementioned operation
     *
     * @param first  The first function to apply to an input element
     * @param second The function to apply to the product of applying the first function to an input element
     * @return A composed function which applies the first function to an input element followed by applying
     * the second function to the product of the aforementioned operation
     */
    static <T, R, V> Function<T, V> andThen(Function<T, R> first, Function<R, V> second) {
        return x -> second.apply(first.apply(x));
    }

    /**
     * Returns a Function which composes functions in the 'andThen' style. Example usage:
     *
     * <pre>
     * String x = Function.<String, Integer, String>andThen().apply(String::length).apply(String::valueOf);
     * "1" = x.apply("7");
     * </pre>
     * <p>
     * In the above example, the {@code String::length} operation is applied first against the input String.
     * The {@code String::valueOf} function is then applied to the result of the aforementioned.
     * <p>
     * This is a constant and exists to compose functions.
     *
     * @param <T> The type of element provided to the function which will be applied first
     * @param <R> The type of element created by the function applied first
     * @param <V> The type of element created by the function applied second
     * @return A Function which composes functions in the 'andThen' style
     */
    static <T, R, V> Function<Function<T, R>, Function<Function<R, V>, Function<T, V>>> andThen() {
        return (Function<T, R> x) -> (Function<R, V> y) -> (T z) -> y.apply(x.apply(z));
    }

    /**
     * Returns a Function which composes functions. Example usage:
     *
     * <pre>
     * String x = Function.<String, Integer, String>compose().apply(String::valueOf).apply(String::length);
     * "4" = x.apply("Test");
     * </pre>
     * <p>
     * In the above example, the {@code String::length} operation is applied first against the input String.
     * The {@code String::valueOf} function is then applied to the result of the aforementioned.
     * <p>
     * This is a constant and exists to compose functions.
     *
     * @param <T> The type of element provided to the function which will be applied first
     * @param <R> The type of element created by the function applied first
     * @param <V> The type of element created by the function applied second
     * @return A Function which composes functions
     */
    static <T, R, V> Function<Function<R, V>, Function<Function<T, R>, Function<T, V>>> compose() {
        return (Function<R, V> x) -> (Function<T, R> y) -> (T z) -> x.apply(y.apply(z));
    }
}
