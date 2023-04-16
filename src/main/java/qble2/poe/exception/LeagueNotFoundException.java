package qble2.poe.exception;

import java.io.Serial;

public class LeagueNotFoundException extends ResourceNotFoundException {

    @Serial
    private static final long serialVersionUID = 1L;

    public LeagueNotFoundException(String resourceId) {
        super("League", resourceId);
    }

}
