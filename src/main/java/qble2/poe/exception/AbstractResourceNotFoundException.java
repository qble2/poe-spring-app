package qble2.poe.exception;

public abstract class AbstractResourceNotFoundException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  private static final String MESSAGE_FORMAT = "%s '%s' not found";

  public AbstractResourceNotFoundException(String resourceName, String resourceId) {
    super(String.format(MESSAGE_FORMAT, resourceName, resourceId));
  }

}
