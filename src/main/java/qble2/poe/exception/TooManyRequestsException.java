package qble2.poe.exception;

import lombok.Getter;

import java.io.Serial;

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
