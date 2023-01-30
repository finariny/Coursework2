package exceptions;

public class IncorrectArgumentException extends RuntimeException {
    private final String argument;
    public IncorrectArgumentException(String argument) {
        this.argument = argument;
    }

    public final String getArgument() {
        return "Параметр " + this.argument + " задан некорректно";
    }

    @Override
    public String toString() {
        return this.argument;
    }
}
