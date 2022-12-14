package com.tajacks.libraries.functional.containers;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CoupleTest {

    @Test
    void nullCoupleInit_throwsNullPointer() {
        assertThrows(NullPointerException.class, () -> new Couple<>("Test", null));
        assertThrows(NullPointerException.class, () -> new Couple<>(null, "Test"));
    }
}
