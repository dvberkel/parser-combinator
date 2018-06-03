package nl.dvberkel.input;

import nl.dvberkel.Input;
import nl.dvberkel.util.Tuple;

public class StringInput implements Input<Character> {
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

    public Input<Character> advance() {
        return new StringInput(index + 1, source);
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
