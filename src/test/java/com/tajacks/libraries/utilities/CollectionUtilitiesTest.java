package com.tajacks.libraries.utilities;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth8.assertThat;
import static com.tajacks.libraries.utilities.CollectionUtilities.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class CollectionUtilitiesTest {

    @Test
    public void canCreateEmptyList() {
        assertThat(list()).isEmpty();
    }

    @Test
    public void canCreateSingletonList() {
        assertThat(list("test")).containsExactly("test");
    }

    @Test
    public void canCopyList_andNotReferentiallyEqual() {
        List<String> stringList = List.of("my", "test");
        List<String> copyOfStringList = list(stringList);

        assertThat(stringList).isEqualTo(copyOfStringList);
        assertThat(stringList == copyOfStringList).isFalse();
    }

    @Test
    public void canCreateList_fromVarArg() {
        List<Integer> integerList = list(1, 2, 3);
        assertThat(integerList).isEqualTo(List.of(1, 2, 3));
    }

    @Test
    public void canMapList_toNewList() {
        var list = list(1, 2, 3);
        assertThat(map(list, x -> x * 2)).isEqualTo(List.of(2, 4, 6));
    }

    @Test
    public void canFoldLeft() {
        String result = foldLeft(list(1, 2, 3, 4, 5), "0", x -> y -> addStringToInteger(x, y));
        assertThat(result).isEqualTo("(((((0 + 1) + 2) + 3) + 4) + 5)");
    }

    @Test
    public void canFoldRight() {
        String result = foldRight(list(1, 2, 3, 4, 5), "0", x -> y -> addIntegerToString(y, x));
        assertThat(result).isEqualTo("(1 + (2 + (3 + (4 + (5 + 0)))))");
    }

    @Test
    public void canPerformHeadOperations() {
        assertThat(head(list(1, 2, 3))).hasValue(1);
        assertThat(head(list())).isEmpty();
    }

    @Test
    public void canPerformTailOperations() {
        assertThat(tail(list(1, 2, 3))).containsExactly(2, 3);
        assertThat(tail(list())).isEmpty();
    }

    @Test
    public void canPrepend_andIsNotReferentiallyEqual() {
        var listOne = list(1, 2, 3);
        var listTwo = prepend(0, listOne);
        assertThat(listTwo).containsExactly(0, 1, 2, 3);
        assertThat(listOne == listTwo).isFalse();
    }

    @Test
    public void canAppend_andIsNotReferentiallyEqual() {
        var listOne = list(1, 2, 3);
        var listTwo = append(4, listOne);
        assertThat(listTwo).containsExactly(1, 2, 3, 4);
        assertThat(listOne == listTwo).isFalse();
    }

    @Test
    public void canReverse() {
        assertThat(reverse(list(1, 2, 3))).isEqualTo(list(3, 2, 1));
    }

    @Test
    public void canApplyForEach() {
        final List<String> stringList = list("One", "Two");
        final List<String> mutableStringList = new ArrayList<>();
        forEach(stringList, mutableStringList::add);
        assertThat(mutableStringList).containsExactly("One", "Two");
    }

    @Test
    public void canCreateIntRangeExclusive() {
        assertThat(intRangeExclusive(1, 5)).isEqualTo(list(1, 2, 3, 4));
        assertThat(intRangeExclusive(5, 1)).isEmpty();
        assertThat(intRangeExclusive(1, 1)).isEmpty();
    }

    @Test
    public void canCreateIntRangeInclusive() {
        assertThat(intRangeInclusive(1, 5)).isEqualTo(list(1, 2, 3, 4, 5));
        assertThat(intRangeInclusive(5, 1)).isEmpty();
        assertThat(intRangeInclusive(1, 1)).isEqualTo(list(1));
    }

    @Test
    public void canCreateLongRangeExclusive() {
        assertThat(longRangeExclusive(1L, 5L)).isEqualTo(list(1L, 2L, 3L, 4L));
        assertThat(longRangeExclusive(5L, 1L)).isEmpty();
        assertThat(longRangeExclusive(1L, 1L)).isEmpty();
    }

    @Test
    public void canCreateLongRangeInclusive() {
        assertThat(longRangeInclusive(1L, 5L)).isEqualTo(list(1L, 2L, 3L, 4L, 5L));
        assertThat(longRangeInclusive(5L, 1L)).isEmpty();
        assertThat(longRangeInclusive(1L, 1L)).isEqualTo(list(1L));
    }

    private String addStringToInteger(String s, int i) {
        return "(" + s + " + " + i + ")";
    }

    private String addIntegerToString(String s, int i) {
        return "(" + i + " + " + s + ")";
    }
}
