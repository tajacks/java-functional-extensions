package com.tajacks.libraries.functional.recursion;

import static com.google.common.truth.Truth.assertThat;
import static com.tajacks.libraries.functional.recursion.TailCall.returning;
import static com.tajacks.libraries.functional.recursion.TailCall.suspending;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigInteger;
import org.junit.Test;

public class TailCallTest {

    // This would otherwise overflow the stack
    @Test
    public void canPerformStackBasedRecursion() {
        int additionResult = add(3, 100000000);
        assertThat(additionResult).isEqualTo(100000003);
    }

    // This would otherwise overflow the stack
    @Test
    public void canPerformLargeFib() {
        BigInteger res = fib(10000);
        assertThat(res).isGreaterThan(BigInteger.ONE);
    }

    @Test
    public void callingResume_onReturning_throws() {
        assertThrows(IllegalStateException.class, () -> {
            TailCall<String> x = TailCall.returning("");
            x.resume();
        });
    }

    public static int add(int x, int y) {
        return add_(x, y).evaluate();
    }

    private static TailCall<Integer> add_(int x, int y) {
        return y == 0 ? returning(x) : suspending(() -> add_(x + 1, y - 1));
    }

    private static BigInteger fib(int of) {
        return fib_(BigInteger.ZERO, BigInteger.ONE, BigInteger.valueOf(of)).evaluate();
    }

    private static TailCall<BigInteger> fib_(BigInteger acc1, BigInteger acc2, BigInteger x) {
        if (x.equals(BigInteger.ZERO)) {
            return returning(BigInteger.ZERO);
        } else if (x.equals(BigInteger.ONE)) {
            return returning(acc1.add(acc2));
        } else {
            return suspending(() -> fib_(acc2, acc1.add(acc2), x.subtract(BigInteger.ONE)));
        }
    }
}
