package com.tajacks.libraries.utilities;

import com.tajacks.libraries.common.Effect;
import com.tajacks.libraries.common.Function;
import java.util.*;

public class CollectionUtilities {

    /**
     * Returns an immutable empty list
     *
     * @param <T> The type of elements in the list
     * @return An immutable empty list
     */
    public static <T> List<T> list() {
        return Collections.emptyList();
    }

    /**
     * Returns an immutable list containing the given single element
     *
     * @param t   The single element to be added to this list
     * @param <T> The type of elements in the list
     * @return An immutable list containing a single element
     */
    public static <T> List<T> list(T t) {
        return Collections.singletonList(t);
    }

    /**
     * Returns an immutable list containing the elements of the given collection
     *
     * @param ts  The collection of elements which will make up the new list
     * @param <T> The type of elements in the list
     * @return An immutable list containing the elements of the given collection
     */
    public static <T> List<T> list(Collection<T> ts) {
        return Collections.unmodifiableList(copyMutable(ts));
    }

    /**
     * Returns an immutable list containing the elements of the given varargs or array
     *
     * @param ts  Elements to create the list from
     * @param <T> The type of elements in the list
     * @return An immutable list containing the given elements
     */
    @SafeVarargs
    public static <T> List<T> list(T... ts) {
        return List.of(Arrays.copyOf(ts, ts.length));
    }

    /**
     * Given a collection, apply a mapping function to each element in the list and return the resulting immutable list
     *
     * @param toMap   The collection containing elements which will have the mapping function applied
     * @param toApply The mapping function to apply to each element
     * @param <T>     The type of elements in the collection
     * @param <U>     The type of elements resulting from the mapping operation
     * @return An immutable list containing the results of applying the given mapping function to each item in the list
     */
    public static <T, U> List<U> map(Collection<T> toMap, Function<T, U> toApply) {
        List<U> mappedList = new ArrayList<>();
        for (T value : toMap) {
            mappedList.add(toApply.apply(value));
        }
        return mappedList;
    }

    /**
     * Retrieves the optional first element in a list. If the list is empty, returns an empty optional
     *
     * @param list The list to retrieve the first element from
     * @param <T>  The type of elements in the list
     * @return An Optional containing either the first element in the list, or, nothing if the list is empty
     */
    public static <T> Optional<T> head(List<T> list) {
        return list.isEmpty() ? Optional.empty() : Optional.ofNullable(list.get(0));
    }

    /**
     * Returns an immutable list containing all elements following the head of the given list
     *
     * @param list The list of items to retrieve the tail from
     * @param <T>  The type of elements in the list
     * @return A list of elements following the head. An empty list if the list is empty or contains only a single element
     */
    public static <T> List<T> tail(List<T> list) {
        if (list.isEmpty()) {
            return list();
        }
        List<T> workingList = copyMutable(list);
        workingList.remove(0);
        return Collections.unmodifiableList(workingList);
    }

    /**
     * Returns an immutable list containing all elements of the given list as well as the given element in the last position of the list
     *
     * @param toAdd The item to add to the list
     * @param list  The list to add the item to
     * @param <T>   The type of elements in the list
     * @return An immutable list containing all items in the original list as well as the given item in the last position
     */
    public static <T> List<T> append(T toAdd, List<T> list) {
        List<T> copiedList = copyMutable(list);
        copiedList.add(toAdd);
        return Collections.unmodifiableList(copiedList);
    }

    /**
     * Returns an immutable list containing all elements of the given list as well as the given element in the first position of the list
     *
     * @param toAdd The item to add to the list
     * @param list  The list to add the item to
     * @param <T>   The type of elements in the list
     * @return An immutable list containing all items in the original list as well as the given item in the first position
     */
    public static <T> List<T> prepend(T toAdd, List<T> list) {
        List<T> copiedList = copyMutable(list);
        copiedList.add(0, toAdd);
        return Collections.unmodifiableList(copiedList);
    }

    public static <T, U> U foldLeft(List<T> toFold, U identity, Function<U, Function<T, U>> f) {
        U result = identity;
        for (T t : toFold) {
            result = f.apply(result).apply(t);
        }
        return result;
    }

    public static <T, U> U foldRight(List<T> toFold, U identity, Function<T, Function<U, U>> f) {
        U result = identity;
        for (int i = toFold.size(); i > 0; i--) {
            result = f.apply(toFold.get(i - 1)).apply(result);
        }
        return result;
    }

    /**
     * Reveres the order of items in the given list and return an immutable copy
     *
     * @param toReverse The list to reverse
     * @param <T>       The type of elements in the list
     * @return An immutable list containing the elements in the given list in reverse order
     */
    public static <T> List<T> reverse(List<T> toReverse) {
        List<T> workingCopy = copyMutable(toReverse);
        Collections.reverse(workingCopy);
        return Collections.unmodifiableList(workingCopy);
    }

    public static <T> void forEach(Collection<T> ts, Effect<T> effect) {
        for (T t : ts) {
            effect.apply(t);
        }
    }

    public static <T> List<T> unfold(T seed, Function<T, T> unfolder, Function<T, Boolean> predicate) {
        List<T> results = new ArrayList<>();
        T temp = seed;
        while (predicate.apply(temp)) {
            results = append(temp, results);
            temp = unfolder.apply(temp);
        }
        return Collections.unmodifiableList(results);
    }

    /**
     * Returns an immutable list of integers from the start value to the end value - 1.
     * If the end value is smaller or equal to the start value, the list is empty
     *
     * @param start The start of the range
     * @param end   The end of the range
     * @return An immutable list of integers from the start value to the end value - 1
     */
    public static List<Integer> intRangeExclusive(int start, int end) {
        return unfold(start, i -> i + 1, i -> i < end);
    }

    /**
     * Returns an immutable list of integers from the start value to the end value.
     * If the end value is smaller than the start value, the list is empty
     *
     * @param start The start of the range
     * @param end   The end of the range
     * @return An immutable list of integers from the start value to the end value
     */
    public static List<Integer> intRangeInclusive(int start, int end) {
        return unfold(start, i -> i + 1, i -> i <= end);
    }

    /**
     * Returns an immutable list of longs from the start value to the end value - 1.
     * If the end value is smaller or equal to the start value, the list is empty
     *
     * @param start The start of the range
     * @param end   The end of the range
     * @return An immutable list of longs from the start value to the end value - 1
     */
    public static List<Long> longRangeExclusive(long start, long end) {
        return unfold(start, i -> i + 1, i -> i < end);
    }

    /**
     * Returns an immutable list of longs from the start value to the end value.
     * If the end value is smaller than the start value, the list is empty
     *
     * @param start The start of the range
     * @param end   The end of the range
     * @return An immutable list of longs from the start value to the end value
     */
    public static List<Long> longRangeInclusive(long start, long end) {
        return unfold(start, i -> i + 1, i -> i <= end);
    }

    // Private helper method to copy a list into a mutable list. Do not let this leak out of this class
    // Only immutable lists should be a product of public methods of this class
    private static <T> List<T> copyMutable(Collection<T> ts) {
        return new ArrayList<>(ts);
    }
}
