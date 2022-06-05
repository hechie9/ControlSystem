package Exceptions;

public class UserAlreadyExistsException extends Exception {

    private static final String MESSAGE = "User %s already exists.";

    public UserAlreadyExistsException(String user) {
        super(String.format(MESSAGE, user));
    }

}
