package Exceptions;

public class UnderClearanceLevelException extends Exception {
    private static final String MESSAGE = "Project manager %s has clearance level %d.";
    public UnderClearanceLevelException(String name, int level) {
        super(String.format(MESSAGE, name, level));
    }
}
