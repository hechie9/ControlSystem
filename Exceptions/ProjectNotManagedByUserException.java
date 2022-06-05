package Exceptions;

public class ProjectNotManagedByUserException extends Exception {
    private static final String MESSAGE = "%s is managed by %s.";
    public ProjectNotManagedByUserException(String id, String username) {
        super(String.format(MESSAGE, id, username));
    }
}
