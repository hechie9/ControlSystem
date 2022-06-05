package Exceptions;

public class NoProjectsWithKeywordException extends Exception {

    private static final String MESSAGE = "No projects with keyword %s.";

    public NoProjectsWithKeywordException(String keyword) {
        super(String.format(MESSAGE, keyword));
    }

}
