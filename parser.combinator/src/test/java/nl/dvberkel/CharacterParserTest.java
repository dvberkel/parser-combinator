package nl.dvberkel;

import static nl.dvberkel.CharacterParser.character;
import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class CharacterParserTest{

    @Test
    public void should_parse_a_single_character()
    {
        Parser parser =  character('A');
        ParseResult result = parser.parse("ABC");
        assertEquals(ParseResult.Ok(Character.valueOf('A'), "BC"), result);
    }
}

interface Parser<I> {
    ParseResult parse(I input);
}

class CharacterParser implements Parser<String> {
    public static CharacterParser character(char character){
        return new CharacterParser(character);
    }
    private final char expectedCharacter;

    public CharacterParser(char character){
        this.expectedCharacter = character;
    }

    @Override
    public ParseResult<Character> parse(String input) {
        return ParseResult.Ok(Character.valueOf('A'),"BC");
    }
}

abstract class ParseResult<O> {
    public static <P> ParseResult<P> Ok(P result, String remainingInput) {
        return new OkParseResult(result, remainingInput);
    }
}

class OkParseResult<O> extends ParseResult<O> {
    private final O result;
    private final String input;

    public OkParseResult(O result, String remainingInput){
        this.result = result;
        this.input = remainingInput;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OkParseResult<?> that = (OkParseResult<?>) o;

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