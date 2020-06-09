package com.graphqllike.gruphql.utils;

import java.util.function.Consumer;

public class LambdaExceptionUtils {

    @FunctionalInterface
    public interface ThrowingConsumer<T, E extends Exception> {
        void accept(T t) throws E;
    }


    public static <T> Consumer<T> useWithCheckedException(ThrowingConsumer<T, Exception> throwingConsumer) {
        return i -> {
            try {
                throwingConsumer.accept(i);
            } catch (Exception e) {
                    throw new RuntimeException(e);
            }
        };
    }

}
