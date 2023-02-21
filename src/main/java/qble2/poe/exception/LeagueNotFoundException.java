package qble2.poe.exception;

public class LeagueNotFoundException extends ResourceNotFoundException {

  private static final long serialVersionUID = 1L;

  public LeagueNotFoundException(String resourceId) {
    super("League", resourceId);
  }

}
