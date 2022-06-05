package Exceptions;

public class EmployeeDoesNotExistException extends Exception {
    private static final String MESSAGE = "%s: does not exist.";
    public EmployeeDoesNotExistException(String username) {
        super(String.format(MESSAGE, username));
    }
}
