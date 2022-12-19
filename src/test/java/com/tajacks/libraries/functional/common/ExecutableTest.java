package com.tajacks.libraries.functional.common;

import org.junit.jupiter.api.Test;

class ExecutableTest {

    @Test
    void canExecuteThings() {
        Executable ex = () -> System.out.println("Test");
        ex.exec();
    }
}
