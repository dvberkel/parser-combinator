package nl.dvberkel.util;

public class Tuple<L, R> {
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
