package nl.dvberkel;

import static nl.dvberkel.CharacterParser.character;
import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class CharacterParserTest {

    @Test
    public void should_parse_a_single_character() {
        Parser<Character, Character> parser = character('A');
        Input<Character> input = new StringInput("ABC");

        ParseResult<Character, Character> result = parser.parse(input);

        assertEquals(ParseResult.Ok(Character.valueOf('A'),new StringInput(1,"ABC")), result);
    }

    @Test
    public void should_parse_a_single_character_if_characters_match() {
        Parser<Character, Character> parser = character('A');
        Input<Character> input = new StringInput("BC");

        ParseResult<Character, Character> result = parser.parse(input);

        assertEquals(ParseResult.Error("Expected character 'A'", input), result);
    }
}

interface Parser<I, O> {
    ParseResult<I, O> parse(Input<I> input);
}

interface Input<I> {
    I peek();
    Tuple<I, Input<I>> pop();
}

class StringInput implements Input<Character> {
    private final int index;
    private final String source;

    public StringInput(String source) {
        this(0, source);
    }

    public StringInput(int index, String source) {
        this.index = index;
        this.source = source;
    }

    public Character peek() {
        return Character.valueOf(source.charAt(index));
    }

    public Tuple<Character, Input<Character>> pop() {
        return Tuple.of(Character.valueOf(source.charAt(index)), new StringInput(index+1, source));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StringInput that = (StringInput) o;

        if (index != that.index) return false;
        return source.equals(that.source);
    }

    @Override
    public int hashCode() {
        int result = index;
        result = 31 * result + source.hashCode();
        return result;
    }
}

class Tuple<L, R> {
    public static <U, V> Tuple<U, V> of(U left, V right) {
        return new Tuple(left, right);
    }

    private final L left;
    private final R right;

    private Tuple(L left, R right) {
        this.left = left;
        this.right = right;
    }

    public L first() {
        return left;
    }

    public R second() {
        return right;
    }
}

class CharacterParser implements Parser<Character, Character> {
    public static CharacterParser character(char character) {
        return new CharacterParser(character);
    }

    private final char expectedCharacter;

    public CharacterParser(char character) {
        this.expectedCharacter = character;
    }

    @Override
    public ParseResult<Character, Character> parse(Input<Character> input) {
        if (input.peek().equals(Character.valueOf('A'))) {
            Tuple<Character, Input<Character>> pop = input.pop();
            return ParseResult.Ok(pop.first(), pop.second());
        } else {
            return ParseResult.Error("Expected character 'A'", input);
        }
    }
}

abstract class ParseResult<I, O> {
    public static <U, V> ParseResult<U, V> Ok(V result, Input<U> remainingInput) {
        return new Ok(result, remainingInput);
    }

    public static <U, V> ParseResult<U, V> Error(String message, Input<U> remainingInput) {
        return new Error(message, remainingInput);
    }

    private static class Ok<I, O> extends ParseResult<I, O> {
        private final O result;
        private final Input<I> input;

        public Ok(O result, Input<I> remainingInput) {
            this.result = result;
            this.input = remainingInput;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Ok<?,?> that = (Ok<?,?>) o;

            if (!result.equals(that.result)) return false;
            return input.equals(that.input);
        }

        @Override
        public int hashCode() {
            int result1 = result.hashCode();
            result1 = 31 * result1 + input.hashCode();
            return result1;
        }
    }

    private static class Error<I, O> extends ParseResult<I, O> {
        private final String message;
        private final Input<I> input;

        public Error(String message, Input<I> remainingInput) {
            this.message = message;
            this.input = remainingInput;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Error<?,?> that = (Error<?,?>) o;

            if (!message.equals(that.message)) return false;
            return input.equals(that.input);
        }

        @Override
        public int hashCode() {
            int result = message.hashCode();
            result = 31 * result + input.hashCode();
            return result;
        }
    }
}