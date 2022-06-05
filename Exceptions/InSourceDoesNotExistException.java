package Exceptions;

public class InSourceDoesNotExistException extends Exception {
    private static final String MESSAGE = "%s project does not exist.";
    public InSourceDoesNotExistException(String id) {
        super(String.format(MESSAGE, id));
    }
}
