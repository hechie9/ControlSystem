package Exceptions;

public class ArtefactDoesNotExistException extends Exception {

    private static final String MESSAGE = "%s does not exist in the project.";

    public ArtefactDoesNotExistException(String name) {
        super(String.format(MESSAGE, name));
    }
}
