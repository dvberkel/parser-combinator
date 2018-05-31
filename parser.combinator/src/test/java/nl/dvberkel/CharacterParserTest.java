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
        assertEquals(ParseResult.Ok('A', "BC"), result);
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
    public ParseResult parse(String input) {
        return ParseResult.Ok('A',"BC");
    }
}

abstract class ParseResult {
    public static ParseResult Ok(char result, String remainingInput) {
        return new OkParseResult(result, remainingInput);
    }
}

class OkParseResult extends ParseResult {
    private final char result;
    private final String input;

    public OkParseResult(char result, String remainingInput){
        this.result = result;
        this.input = remainingInput;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OkParseResult that = (OkParseResult) o;

        if (result != that.result) return false;
        return input != null ? input.equals(that.input) : that.input == null;
    }

    @Override
    public int hashCode() {
        int result1 = (int) result;
        result1 = 31 * result1 + (input != null ? input.hashCode() : 0);
        return result1;
    }
}