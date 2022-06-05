package Exceptions;

public class ProjectDoesNotExistException extends Exception {
    private static final String MESSAGE = "%s project does not exist.";
    public ProjectDoesNotExistException(String id) {
        super(String.format(MESSAGE, id));
    }
}
