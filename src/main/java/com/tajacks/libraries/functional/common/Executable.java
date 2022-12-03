package com.tajacks.libraries.functional.common;

/**
 * Similar to {@link Runnable}, without any requirements surrounding
 * the manner in which this is implemented. Differs from {@link Runnable}
 * regarding the requirement, or lack thereof, of execution by a thread
 */
@FunctionalInterface
public interface Executable {

    /**
     * Do something, anything!
     */
    void exec();
}
