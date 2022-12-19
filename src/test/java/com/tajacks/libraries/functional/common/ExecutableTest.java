package com.tajacks.libraries.functional.common;

import org.junit.Test;

public class ExecutableTest {

    @Test
    public void canExecuteThings() {
        Executable ex = () -> System.out.println("Test");
    }
}
