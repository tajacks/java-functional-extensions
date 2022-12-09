package com.tajacks.libraries.functional.recursion;

import com.tajacks.libraries.functional.common.Supplier;

/**
 * Used to place tail recursive operations on the heap instead of the stack.
 * Example usage of static methods:
 *
 * <pre>
 * public static int add(int x, int y) {
 *     return add_(x, y).evaluate();
 * }
 *
 * private static TailCall&lt;Integer&gt; add_(int x, int y) {
 *     return y == 0 ? returning(x) : suspending(() -> add_(x + 1, y - 1));
 * }
 * </pre>
 *
 * @param <T> The type of element returned by the tail recursive operation
 */
public abstract sealed class TailCall<T> permits TailCall.Return, TailCall.Suspend {

    /**
     * Calls the next TailCall in the recursive operation
     * Invalid on TailCall.Return instances
     * @return The next TailCall in the recursive operation
     */
    public abstract TailCall<T> resume();

    /**
     * Returns the result of the tail recursive operation
     * @return The result of the tail recursive operation
     */
    public abstract T evaluate();

    /**
     * Indicates if a TailCall is a suspending, i.e. non-terminating, operation
     * @return True if non-terminating, false otherwise
     */
    public abstract boolean isSuspend();

    private TailCall() {
        // Subclasses should only be instantiated via static factories
    }

    /**
     * Creates a new returning TailCall. Should be used when the recursive operation
     * should be terminated, returning a final result.
     *
     * @param result The final result of the tail recursive operation
     * @param <T>    The type of element to be returned by the tail recursive operation
     * @return A TailCall holding the final result of a recursive operation
     */
    public static <T> Return<T> returning(T result) {
        return new Return<>(result);
    }

    /**
     * Creates a new suspending TailCall. To be used when the recursive operation
     * should not yet be terminated.
     *
     * @param s   A supplier to supply the next TailCall in the tail recursive operation
     * @param <T> The type of element to be returned by the tail recursive operation
     * @return A TailCall which is not the final result of a tail recursive operation
     */
    public static <T> Suspend<T> suspending(Supplier<TailCall<T>> s) {
        return new Suspend<>(s);
    }

    /**
     * Represents the final result of a recursive operation, storing that value
     * in this objects result variable
     *
     * @param <T> The type of element returned by the tail recursive operation
     */
    protected static final class Return<T> extends TailCall<T> {
        private final T result;

        private Return(T result) {
            this.result = result;
        }

        /**
         * It is illegal for the resume method to be called on a {@code TailCall.Return}
         *
         * @return Nothing is returned from this implementation
         */
        @Override
        public TailCall<T> resume() {
            throw new IllegalStateException("Return has no resume");
        }

        /**
         * Return the value stored in this classes result variable
         *
         * @return The value stored in this classes result variable
         */
        @Override
        public T evaluate() {
            return result;
        }

        /**
         * Indicates if this object is a suspending operation
         *
         * @return False, this object is not a suspending operation
         */
        @Override
        public boolean isSuspend() {
            return false;
        }
    }

    /**
     * Represents a TailRecursive operation step which is not the final operation
     *
     * @param <T> The type of element returned by the tail recursive operation
     */
    protected static final class Suspend<T> extends TailCall<T> {
        private final Supplier<TailCall<T>> resume;

        private Suspend(Supplier<TailCall<T>> resume) {
            this.resume = resume;
        }

        /**
         * Returns the TailCall that is stored in this objects {@code resume} variable
         *
         * @return The TailCall that is stored in this objects {@code resume} variable
         */
        @Override
        public TailCall<T> resume() {
            return resume.get();
        }

        /**
         * Iterates through tail recursive suspending operations until
         * reaching an operation that is not suspending, returning the value
         * stored in the TailCall.Return
         *
         * @return The value of the returning TailCall operation
         */
        @Override
        public T evaluate() {
            TailCall<T> tailRec = this;
            while (tailRec.isSuspend()) {
                tailRec = tailRec.resume();
            }
            return tailRec.evaluate();
        }

        /**
         * Indicates if this object is a suspending operation
         *
         * @return True, object is a suspending operation
         */
        @Override
        public boolean isSuspend() {
            return true;
        }
    }
}
