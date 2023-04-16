package qble2.poe.exception;

import java.io.Serial;

public class ItemNotFoundException extends ResourceNotFoundException {

    @Serial
    private static final long serialVersionUID = 1L;

    public ItemNotFoundException(String resourceId) {
        super("Item", resourceId);
    }

}
