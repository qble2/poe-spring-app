package qble2.poe.exception;

import java.io.Serial;

public class StashTabNotFoundException extends ResourceNotFoundException {

    @Serial
    private static final long serialVersionUID = 1L;

    public StashTabNotFoundException(String resourceId) {
        super("Stash tab", resourceId);
    }

}
