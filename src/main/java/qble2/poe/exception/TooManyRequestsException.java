package qble2.poe.exception;

import lombok.Getter;

@Getter
public class TooManyRequestsException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  private int retryAfter;

  public TooManyRequestsException(String message, int retryAfter) {
    super(message);
    this.retryAfter = retryAfter;
  }

}
