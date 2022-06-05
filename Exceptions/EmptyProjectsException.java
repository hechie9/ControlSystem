package Exceptions;

public class EmptyProjectsException extends Exception {
    private static final String MESSAGE = "No projects added.";
    public EmptyProjectsException() {
        super(MESSAGE);
    }
}
