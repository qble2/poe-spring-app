package qble2.poe;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import reactor.core.publisher.Mono;

// @UtilityClass
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class RequestLogUtils {

  private static final String TABULATION = "\t";

  // This method returns filter function which will log request data
  public static ExchangeFilterFunction logRequest() {
    return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
      log.info(TABULATION + "Request: {} {}", clientRequest.method(), clientRequest.url());
      clientRequest.headers().forEach((name, values) -> values
          .forEach(value -> log.info(TABULATION + TABULATION + "{}: {}", name, value)));
      return Mono.just(clientRequest);
    });
  }

  public static ExchangeFilterFunction logResponse() {
    return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
      logStatus(clientResponse);
      logHeaders(clientResponse);
      // return logBody(clientResponse); // can be verbose
      return Mono.just(clientResponse);
    });
  }

  private static void logStatus(ClientResponse response) {
    HttpStatus status = response.statusCode();
    log.info(TABULATION + "Returned status code {} ({})", status.value(), status.getReasonPhrase());
  }

  private static void logHeaders(ClientResponse response) {
    response.headers().asHttpHeaders().forEach((name, values) -> {
      // values.forEach(value -> {
      // logNameAndValuePair(name, value);
      // });
    });
  }

  @SuppressWarnings("unused")
  private static Mono<ClientResponse> logBody(ClientResponse response) {
    if (response.statusCode().is4xxClientError() || response.statusCode().is5xxServerError()) {
      return response.bodyToMono(String.class).flatMap(body -> {
        log.info(TABULATION + TABULATION + "Body is {}", body);
        return Mono.just(response);
      });
    } else {
      return Mono.just(response);
    }
  }

}
