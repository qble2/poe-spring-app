package qble2.poe.exception;

import java.io.Serial;
import lombok.Getter;

@Getter
public class TooManyRequestsException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 1L;

  private final int retryAfter;

  public TooManyRequestsException(String message, int retryAfter) {
    super(message);
    this.retryAfter = retryAfter;
  }

}
