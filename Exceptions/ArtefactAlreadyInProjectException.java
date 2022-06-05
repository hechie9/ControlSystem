package Exceptions;

public class ArtefactAlreadyInProjectException extends Exception {
    private static final String MESSAGE = "%s: already in the project.";
    public ArtefactAlreadyInProjectException(String name) {
        super(String.format(MESSAGE, name));
    }
}
