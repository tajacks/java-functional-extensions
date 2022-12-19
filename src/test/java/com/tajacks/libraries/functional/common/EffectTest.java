package com.tajacks.libraries.functional.common;

import static com.google.common.truth.Truth.assertThat;
import static com.tajacks.libraries.functional.common.Effect.andThen;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class EffectTest {

    // Mutable lists are used to demonstrate that the void effect is taking action

    @Test
    void canApplyEffect() {
        List<String> mutableExampleList = new ArrayList<>(List.of("One"));
        Effect<String> addToList = mutableExampleList::add;
        addToList.apply("Two");
        assertThat(mutableExampleList).hasSize(2);
    }

    @Test
    void canApplyEffectAndThenAnother() {
        List<String> mutableExampleList = new ArrayList<>(List.of("One"));
        Effect<String> addToList = mutableExampleList::add;
        Effect<String> addToListAgain = addToList.andThen(addToList);
        addToListAgain.apply("Two");
        assertThat(mutableExampleList).hasSize(3);
    }

    @Test
    void canApplyEffectAndThenAnotherStatic() {
        List<String> mutableExampleList = new ArrayList<>(List.of("One"));
        Effect<String> doubleListAdder = andThen(mutableExampleList::add, mutableExampleList::add);
        doubleListAdder.apply("Two");
        assertThat(mutableExampleList).hasSize(3);
    }
}
