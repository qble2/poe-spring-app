package qble2.poe.exception;

public class StashTabNotFoundException extends AbstractResourceNotFoundException {

  private static final long serialVersionUID = 1L;

  public StashTabNotFoundException(String resourceId) {
    super("Stash tab", resourceId);
  }

}
