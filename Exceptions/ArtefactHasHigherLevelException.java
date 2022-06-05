package Exceptions;

public class ArtefactHasHigherLevelException extends Exception {
    private static final String MESSAGE = "%s: exceeds project confidentiality level.";
    public ArtefactHasHigherLevelException(String name) {
        super(String.format(MESSAGE, name));
    }
}
