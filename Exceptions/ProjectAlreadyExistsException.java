package Exceptions;

public class ProjectAlreadyExistsException extends Exception {
    private static final String MESSAGE = "%s project already exists.";
    public ProjectAlreadyExistsException(String id) {
        super(String.format(MESSAGE, id));
    }
}
