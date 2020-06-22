package com.gowyn.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

@Slf4j
public class LambdaExceptionUtils {

    @FunctionalInterface
    public interface ThrowingConsumer<T, E extends Exception> {
        void accept(T t) throws E;
    }


    public static <T, E extends Exception> Consumer<T> useWithCheckedException(ThrowingConsumer<T, E> throwingConsumer) throws E{
        return i -> {
            try {
                throwingConsumer.accept(i);
            } catch (Exception e) {
                throwAndLogCheckedException(e);
            }
        };
    }

    @SuppressWarnings("unchecked")
    private static <E extends Exception> void throwAndLogCheckedException (Exception e) throws E {
        log.error("An error occured " , e);
        throw (E) e;
    }

}
