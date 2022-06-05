package Exceptions;

public class NoProjectsWithinLevelsException extends Exception {

    private static final String MESSAGE = "No projects within levels %d and %d.";

    public NoProjectsWithinLevelsException(int lower, int upper) {
        super(String.format(MESSAGE, lower, upper));
    }
}
