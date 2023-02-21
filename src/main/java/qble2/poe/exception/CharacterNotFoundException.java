package qble2.poe.exception;

public class CharacterNotFoundException extends ResourceNotFoundException {

  private static final long serialVersionUID = 1L;

  public CharacterNotFoundException(String resourceId) {
    super("Character", resourceId);
  }

}
