package com.olenickglobal.utils;

@FunctionalInterface
public interface FunctionWithException<A, R, E extends Throwable> {
    R apply(A a) throws E;
}
