package nl.dvberkel;

public abstract class ParseResult<I, O> {
    public static <U, V> ParseResult<U, V> Ok(V result, Input<U> remainingInput) {
        return new Ok(result, remainingInput);
    }

    public static <U, V> ParseResult<U, V> Error(String message, Input<U> remainingInput) {
        return new Error(message, remainingInput);
    }

    public abstract boolean isOk();

    public static class Ok<I, O> extends ParseResult<I, O> {
        public final O result;
        public final Input<I> input;

        public Ok(O result, Input<I> remainingInput) {
            this.result = result;
            this.input = remainingInput;
        }

        @Override
        public boolean isOk() {
            return true;
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

        @Override
        public String toString() {
            return String.format("Ok(%s, %s)", result, input);
        }
    }

    public static class Error<I, O> extends ParseResult<I, O> {
        public final String message;
        public final Input<I> input;

        public Error(String message, Input<I> remainingInput) {
            this.message = message;
            this.input = remainingInput;
        }

        @Override
        public boolean isOk() {
            return false;
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

        @Override
        public String toString() {
            return String.format("Error(%s, %s)", message, input);
        }
    }
}
