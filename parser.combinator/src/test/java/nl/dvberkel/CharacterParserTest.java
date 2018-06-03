package nl.dvberkel;

import static nl.dvberkel.parser.leaf.CharacterParser.character;
import static org.junit.Assert.assertEquals;

import nl.dvberkel.input.StringInput;
import org.junit.Test;


public class CharacterParserTest {

    @Test
    public void should_parse_a_single_character() {
        Parser<Character, Character> parser = character('A');
        Input<Character> input = new StringInput("ABC");

        ParseResult<Character, Character> result = parser.parse(input);

        assertEquals(ParseResult.Ok(Character.valueOf('A'), input.advance()), result);
    }

    @Test
    public void should_parse_a_single_character_if_characters_match() {
        Parser<Character, Character> parser = character('A');
        Input<Character> input = new StringInput("BC");

        ParseResult<Character, Character> result = parser.parse(input);

        assertEquals(ParseResult.Error("Expected character 'A'", input), result);
    }
}

