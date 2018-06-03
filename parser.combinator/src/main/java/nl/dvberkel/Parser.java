package nl.dvberkel;

public interface Parser<I, O> {
    ParseResult<I, O> parse(Input<I> input);
}
