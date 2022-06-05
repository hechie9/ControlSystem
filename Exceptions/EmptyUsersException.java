package Exceptions;

public class EmptyUsersException extends Exception {

    private static final String MESSAGE = "No users registered.";

    public EmptyUsersException() {
        super(MESSAGE);
    }
}
