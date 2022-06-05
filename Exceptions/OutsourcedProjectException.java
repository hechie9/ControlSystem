package Exceptions;

public class OutsourcedProjectException extends Exception {

    private static final String MESSAGE = "%s is an outsourced project.";

    public OutsourcedProjectException(String id) {
        super(String.format(MESSAGE, id));
    }
}
