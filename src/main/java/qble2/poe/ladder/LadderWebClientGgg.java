package qble2.poe.ladder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.NumberUtils;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import lombok.extern.slf4j.Slf4j;
import qble2.poe.RequestLogUtils;
import qble2.poe.exception.TooManyRequestsException;
import qble2.poe.ladder.ggg.LadderGgg;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class LadderWebClientGgg {

  @Autowired
  private LadderMapper ladderMapper;

  public static final int GGG_MAX_LADDER_ENTRIES = 15000;
  // max limit = 200 (even tho GGG documentation says 500)
  public static final int GGG_FETCH_LIMIT = 200;

  private static final String BROWSER_USER_AGENT =
      "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36";
  private static final int MAX_IN_MEMORY_SIZE_IN_MB = 1;
  private static final String GGG_BASE_URL = "https://www.pathofexile.com";
  private static final String GET_LADDER_URI = "/api/ladders";

  // handling DataBufferLimitException: Exceeded limit on max bytes to buffer
  private static final ExchangeStrategies exchangeStrategies =
      ExchangeStrategies.builder().codecs(configurer -> configurer.defaultCodecs()
          .maxInMemorySize(MAX_IN_MEMORY_SIZE_IN_MB * 1024 * 1024)).build();

  private static final WebClient webClient =
      WebClient.builder().filters(exchangeFilterFunctions -> {
        exchangeFilterFunctions.add(RequestLogUtils.logRequest());
        exchangeFilterFunctions.add(RequestLogUtils.logResponse());
      }).defaultHeader("User-Agent", BROWSER_USER_AGENT).baseUrl(GGG_BASE_URL)
          .exchangeStrategies(exchangeStrategies).build();

  // https://www.pathofexile.com/api/ladders?offset=0&limit=20&id=Sanctum&type=league&realm=pc
  // offset 0 = rank 1
  // offset 0 + limit 200 => rank 1 to rank 200 both included
  // offset 200 + limit 200 => rank 201 to rank 400 both included
  public LadderDto retrieveLadder(String leagueId, int start, int end) {
    int limit = Math.min(GGG_FETCH_LIMIT, end);
    log.info("Retrieving ladder (leagueId: {} , offset: {} , limit: {}) from GGG...", leagueId,
        start, limit);
    Mono<LadderGgg> mono = webClient.get()
        .uri(uriBuilder -> uriBuilder.path(GET_LADDER_URI).queryParam("type", "league")
            .queryParam("realm", "pc").queryParam("id", leagueId).queryParam("offset", start)
            .queryParam("limit", limit).build())
        .retrieve()
        .onStatus(status -> status.value() == HttpStatus.TOO_MANY_REQUESTS.value(), response -> {
          int retryAfter = NumberUtils.parseNumber(response.headers().header("Retry-After").get(0),
              Integer.class);
          return Mono.error(new TooManyRequestsException(
              "Rate limit exceeded, Please try again later.", retryAfter));
        }).bodyToMono(LadderGgg.class);

    LadderGgg ladderGgg = mono.block();
    log.info("Ladder (leagueId: {} , offset: {} , limit: {}) have been retrieved from GGG.",
        leagueId, start, GGG_FETCH_LIMIT);

    return this.ladderMapper.toDtoFromGgg(ladderGgg, leagueId);
  }

}
