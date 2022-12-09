package com.tajacks.libraries.functional.common;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;

public class FunctionTest {

    Function<String, Integer> stringCounter = String::length;
    Function<Integer, String> intToString = String::valueOf;
    Function<Integer, Function<String, String>> repeater = i -> s -> s.repeat(i);

    @Test
    public void canApply() {
        assertThat(stringCounter.apply("Java")).isEqualTo(4);
        assertThat(intToString.apply(4)).isEqualTo("4");
        assertThat(repeater.apply(3).apply("Java")).isEqualTo("JavaJavaJava");
    }

    @Test
    public void canCompose() {
        var result = stringCounter.compose(intToString).apply(3);
        assertThat(result).isEqualTo(1);
        var otherDirection = intToString.compose(stringCounter).apply("333");
        assertThat(otherDirection).isEqualTo("3");
    }

    @Test
    public void canAndThen() {
        var result = stringCounter.andThen(intToString).apply("Java");
        assertThat(result).isEqualTo("4");
        var otherDirection = intToString.andThen(stringCounter).apply(5);
        assertThat(otherDirection).isEqualTo(1);
    }

    @Test
    public void canTestIdentity() {
        assertThat(Function.identity().apply("Test")).isEqualTo("Test");
    }

    @Test
    public void canAndThen_usingStaticMethod_takingParams() {
        int result = Function.andThen(intToString, stringCounter)
                .apply(100);
        assertThat(result).isEqualTo(3);
    }

    @Test
    public void canCompose_usingStaticMethod_takingParams() {
        int result = Function.compose(stringCounter, intToString)
                .apply(100);
        assertThat(result).isEqualTo(3);
    }

    @Test
    public void canHigherCompose_usingStaticMethod() {
        String result = Function.<String, Integer, String>compose()
                .apply(intToString)
                .apply(stringCounter)
                .apply("Test");
        assertThat(result).isEqualTo("4");
    }

    @Test
    public void canHigherAndThen_usingStaticMethod() {
        String result = Function.<String, Integer, String>andThen()
                .apply(stringCounter)
                .apply(intToString)
                .apply("7");
        assertThat(result).isEqualTo("1");
    }
}
