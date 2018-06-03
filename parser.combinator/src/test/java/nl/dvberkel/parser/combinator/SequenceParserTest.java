package nl.dvberkel.parser.combinator;

import nl.dvberkel.Input;
import nl.dvberkel.ParseResult;
import nl.dvberkel.Parser;
import nl.dvberkel.input.StringInput;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static nl.dvberkel.parser.combinator.SequenceParser.sequence;
import static nl.dvberkel.parser.leaf.CharacterParser.character;
import static org.junit.Assert.assertEquals;

public class SequenceParserTest {
    @Test
    public void sequence_should_parse_one_parser_after_the_other() {
        Parser<Character, List<Character>> parser = sequence(character('A'), character('B'));
        Input<Character> input = new StringInput("ABCD");

        ParseResult<Character, List<Character>> result = parser.parse(input);

        assertEquals(ParseResult.Ok(Arrays.asList(Character.valueOf('A'), Character.valueOf('B')), input.advance().advance()), result);
    }
}
