package nl.dvberkel;

import static nl.dvberkel.CharacterParser.character;
import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class CharacterParserTest{

    @Test
    public void should_parse_a_single_character()
    {
        Parser parser =  character('A');
        ParseResult result = parser.parse("A");
        assertEquals(ParseResult.Ok(""), result);
    }
}

interface Parser {
    ParseResult parse(String input);
}

class CharacterParser implements Parser {
    public static CharacterParser character(char character){
        return new CharacterParser(character);
    }
    private final char expectedCharacter;

    public CharacterParser(char character){
        this.expectedCharacter = character;
    }

    @Override
    public ParseResult parse(String input) {
        return ParseResult.Ok("");
    }
}

abstract class ParseResult {
    public static ParseResult Ok(String remainingInput) {
        return new OkParseResult(remainingInput);
    }
}

class OkParseResult extends ParseResult {
    private String input;

    public OkParseResult(String remainingInput){
        this.input = remainingInput;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OkParseResult that = (OkParseResult) o;

        return input.equals(that.input);
    }

    @Override
    public int hashCode() {
        return input.hashCode();
    }
}