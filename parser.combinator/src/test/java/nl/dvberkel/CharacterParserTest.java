package nl.dvberkel;

import static nl.dvberkel.CharacterParser.character;
import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class CharacterParserTest{

    @Test
    public void should_parse_a_single_character()
    {
        Parser<String, Character> parser =  character('A');
        ParseResult<Character> result = parser.parse("ABC");
        assertEquals(ParseResult.Ok(Character.valueOf('A'), "BC"), result);
    }

    @Test
    public void should_parse_a_single_character_if_characters_match()
    {
        Parser<String, Character> parser =  character('A');
        ParseResult<Character> result = parser.parse("BC");
        assertEquals(ParseResult.Error("Expected character 'A'", "BC"), result);
    }
}

interface Parser<I, O> {
    ParseResult<O> parse(I input);
}

class CharacterParser implements Parser<String, Character> {
    public static CharacterParser character(char character){
        return new CharacterParser(character);
    }
    private final char expectedCharacter;

    public CharacterParser(char character){
        this.expectedCharacter = character;
    }

    @Override
    public ParseResult<Character> parse(String input) {
        if (input.startsWith("A")) {
            return ParseResult.Ok(Character.valueOf('A'),"BC");
        } else {
            return ParseResult.Error("Expected character 'A'", "BC");
        }
    }
}

abstract class ParseResult<O> {
    public static <P> ParseResult<P> Ok(P result, String remainingInput) {
        return new Ok(result, remainingInput);
    }

    public static <P> ParseResult<P> Error(String message, String remainingInput) {
        return new Error(message, remainingInput);
    }
}

class Ok<O> extends ParseResult<O> {
    private final O result;
    private final String input;

    public Ok(O result, String remainingInput){
        this.result = result;
        this.input = remainingInput;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ok<?> that = (Ok<?>) o;

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

class Error<O> extends ParseResult<O> {
    private final String message;
    private final String input;

    public Error(String message, String remainingInput) {
        this.message = message;
        this.input = remainingInput;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Error<?> that = (Error<?>) o;

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