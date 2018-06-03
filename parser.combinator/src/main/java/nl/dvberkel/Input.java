package nl.dvberkel;

import nl.dvberkel.util.Tuple;

public interface Input<I> {
    I peek();
    Tuple<I, Input<I>> pop();
    Input<I> advance();
}
