package nl.dvberkel.parser.combinator;

import nl.dvberkel.Input;
import nl.dvberkel.ParseResult;
import nl.dvberkel.Parser;

import java.util.ArrayList;
import java.util.List;

public class SequenceParser<I, O> implements Parser<I, List<O>> {
    public static <U, V> SequenceParser<U, V> sequence(Parser<U, V> parser1, Parser<U, V> parser2) {
        return new SequenceParser(parser1, parser2);
    }

    private final Parser<I, O> parser1;
    private final Parser<I, O> parser2;

    public SequenceParser(Parser<I, O> parser1, Parser<I, O> parser2) {
        this.parser1 = parser1;
        this.parser2 = parser2;
    }

    @Override
    public ParseResult<I, List<O>> parse(Input<I> input) {
        List<O> result = new ArrayList<O>();
        ParseResult<I, O> firstResult = parser1.parse(input);
        if (firstResult.isOk()) {
            ParseResult.Ok<I, O> firstOk = (ParseResult.Ok) firstResult;
            result.add(firstOk.result);
            ParseResult<I, O> secondResult = parser2.parse(firstOk.input);
            if (secondResult.isOk()) {
                ParseResult.Ok<I, O> secondOk = (ParseResult.Ok) secondResult;
                result.add(secondOk.result);
                return new ParseResult.Ok(result, secondOk.input);
            } else {
                ParseResult.Error<I, O> secondError = (ParseResult.Error) secondResult;
                return ParseResult.Error(secondError.message, secondError.input);
            }
        } else {
            ParseResult.Error<I, O> firstError = (ParseResult.Error) firstResult;
            return ParseResult.Error(firstError.message, firstError.input);
        }
    }
}
