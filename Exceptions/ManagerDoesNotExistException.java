package Exceptions;

public class ManagerDoesNotExistException extends Exception {

    private static final String MESSAGE = "Project manager %s does not exist.";

    public ManagerDoesNotExistException(String user) {
        super(String.format(MESSAGE, user));
    }

}
