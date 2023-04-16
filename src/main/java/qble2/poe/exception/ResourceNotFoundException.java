package qble2.poe.exception;

import lombok.Getter;

import java.io.Serial;

@Getter
public abstract class ResourceNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    private static final String MESSAGE_FORMAT = "%s '%s' not found";

    private final String resourceName;
    private final String resourceId;

    protected ResourceNotFoundException(String resourceName, String resourceId) {
        super(getFormattedMessage(resourceName, resourceId));

        this.resourceName = resourceName;
        this.resourceId = resourceId;

    }

    public static String getFormattedMessage(String resourceName, String resourceId) {
        return String.format(MESSAGE_FORMAT, resourceName, resourceId);
    }

}
