package qble2.poe.exception;

import java.io.Serial;

public class CharacterNotFoundException extends ResourceNotFoundException {
    @Serial
    private static final long serialVersionUID = 1L;

    public CharacterNotFoundException(String resourceId) {
        super("Character", resourceId);
    }

}
