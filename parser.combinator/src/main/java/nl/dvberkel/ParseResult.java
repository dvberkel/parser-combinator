package nl.dvberkel;

public abstract class ParseResult<I, O> {
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
