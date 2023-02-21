package qble2.poe.exception;

public class ItemNotFoundException extends ResourceNotFoundException {

  private static final long serialVersionUID = 1L;

  public ItemNotFoundException(String resourceId) {
    super("Item", resourceId);
  }

}
