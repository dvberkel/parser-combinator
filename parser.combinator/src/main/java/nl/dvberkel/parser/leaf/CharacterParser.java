package nl.dvberkel.parser.leaf;

import nl.dvberkel.Input;
import nl.dvberkel.ParseResult;
import nl.dvberkel.Parser;
import nl.dvberkel.util.Tuple;

public class CharacterParser implements Parser<Character, Character> {
    public static CharacterParser character(char character) {
        return new CharacterParser(character);
    }

    private final char expectedCharacter;

    public CharacterParser(char character) {
        this.expectedCharacter = character;
    }

    @Override
    public ParseResult<Character, Character> parse(Input<Character> input) {
        if (input.peek().equals(Character.valueOf(expectedCharacter))) {
            Tuple<Character, Input<Character>> pop = input.pop();
            return ParseResult.Ok(pop.first(), pop.second());
        } else {
            return ParseResult.Error(String.format("Expected character '%s'", expectedCharacter), input);
        }
    }
}
