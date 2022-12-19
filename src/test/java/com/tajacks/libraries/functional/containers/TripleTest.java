package com.tajacks.libraries.functional.containers;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TripleTest {

    @Test
    void nullTripleInit_throwsNullPointer() {
        assertThrows(NullPointerException.class, () -> new Triple<>("Test", "Test", null));
        assertThrows(NullPointerException.class, () -> new Triple<>("Test", null, "Test"));
        assertThrows(NullPointerException.class, () -> new Triple<>(null, "Test", "Test"));
    }
}
