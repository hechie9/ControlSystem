package Exceptions;

public class MemberAlreadyInTeamException extends Exception {
    private static final String MESSAGE = "%s: already a member.";
    public MemberAlreadyInTeamException(String username) {
        super(String.format(MESSAGE, username));
    }
}
