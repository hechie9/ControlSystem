package Exceptions;

public class MemberNotInTeamException extends Exception {
    private static final String MESSAGE = "User %s does not belong to the team of %s.";
    public MemberNotInTeamException(String username, String id) {
        super(String.format(MESSAGE, username, id));
    }
}
