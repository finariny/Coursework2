package exceptions;

public class IncorrectArgumentException extends RuntimeException {
    private final String argument;
    public IncorrectArgumentException(String argument) {
        this.argument = argument;
    }

    public String getArgument() {
        return this.argument;
    }

    @Override
    public String toString() {
        return "Параметр " + this.argument + " задан некорректно";
    }
}
