package qble2.poe.exception;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// only works for synchronous exceptions
@ControllerAdvice
@Slf4j
public class ExceptionsControllerAdvice {

  public static final String INTERNAL_SERVER_ERROR_MESSAGE =
      "Internal Server Error (Please contact the administrator)";

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<ResponseErrorDto> handleHttpRequestMethodNotSupportedException(
      HttpServletRequest request, HttpRequestMethodNotSupportedException exception) {
    return createErrorResponseEntity(request, HttpStatus.METHOD_NOT_ALLOWED, exception);
  }

  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ResponseEntity<ResponseErrorDto> handleMissingParams(HttpServletRequest request,
      Exception exception) {
    return createErrorResponseEntity(request, HttpStatus.BAD_REQUEST, exception);
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ResponseErrorDto> handleResourceNotFoundException(
      HttpServletRequest request, Exception exception) {
    return createErrorResponseEntity(request, HttpStatus.NOT_FOUND, exception);
  }

  // at last, as a fail-safe, to catch any unhandled server exception
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ResponseErrorDto> unhandledExceptions(HttpServletRequest request,
      Exception exception) {
    log.error("(Unhandled exception) An internal server error has occurred", exception);
    return createErrorResponseEntity(request, HttpStatus.INTERNAL_SERVER_ERROR,
        INTERNAL_SERVER_ERROR_MESSAGE, null);
  }

  /////
  /////
  /////

  private ResponseEntity<ResponseErrorDto> createErrorResponseEntity(HttpServletRequest request,
      HttpStatus httpStatus, Exception exception) {
    return createErrorResponseEntity(request, httpStatus, exception.getMessage(), null);
  }

  private ResponseEntity<ResponseErrorDto> createErrorResponseEntity(HttpServletRequest request,
      HttpStatus httpStatus, String message, List<String> details) {
    String requestUrl = request.getRequestURI();
    String queryString = request.getQueryString();
    if (queryString != null) {
      requestUrl = requestUrl + "?" + queryString;
    }

    ResponseErrorDto responseError =
        ResponseErrorDto.builder().status(httpStatus.value()).error(httpStatus.getReasonPhrase())
            .message(message).details(details).path(requestUrl).build();
    return new ResponseEntity<>(responseError, httpStatus);
  }

}
