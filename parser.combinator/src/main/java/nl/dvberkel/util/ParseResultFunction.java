package nl.dvberkel.util;

import nl.dvberkel.Input;

public abstract class ParseResultFunction {
    @FunctionalInterface
    public interface Ok<I, O, R> {
        R apply(O result, Input<I> remainingInput);
    }

    @FunctionalInterface
    public interface Error<I, O, R> {
        R apply(String message, Input<I> remainingInput);
    }
}
