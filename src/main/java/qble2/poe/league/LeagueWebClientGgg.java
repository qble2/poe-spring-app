package qble2.poe.league;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import lombok.extern.slf4j.Slf4j;
import qble2.poe.RequestLogUtils;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class LeagueWebClientGgg {

  @Autowired
  private LeagueMapper leagueMapper;

  private static final String BROWSER_USER_AGENT =
      "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36";

  private static final String GGG_API_BASE_URL = "https://api.pathofexile.com";
  private static final String GET_LEAGUES_URI = "/leagues";

  private static final int MAX_IN_MEMORY_SIZE_IN_MB = 1;

  // handling DataBufferLimitException: Exceeded limit on max bytes to buffer
  private static final ExchangeStrategies exchangeStrategies =
      ExchangeStrategies.builder().codecs(configurer -> configurer.defaultCodecs()
          .maxInMemorySize(MAX_IN_MEMORY_SIZE_IN_MB * 1024 * 1024)).build();

  private static final WebClient webClient =
      WebClient.builder().filters(exchangeFilterFunctions -> {
        exchangeFilterFunctions.add(RequestLogUtils.logRequest());
        exchangeFilterFunctions.add(RequestLogUtils.logResponse());
      }).defaultHeader("User-Agent", BROWSER_USER_AGENT).baseUrl(GGG_API_BASE_URL)
          .exchangeStrategies(exchangeStrategies).build();

  // https://api.pathofexile.com/leagues
  public List<LeagueDto> retrieveLeagues() {
    log.info("Retrieving leagues from GGG...");
    Mono<LeagueGgg[]> mono =
        webClient.get().uri(GET_LEAGUES_URI).retrieve().bodyToMono(LeagueGgg[].class);

    List<LeagueGgg> listOfLeagueGgg = List.of(mono.block());
    log.info("Leagues have been retrieved from GGG.");

    return this.leagueMapper.toDtoListFromGggList(listOfLeagueGgg);
  }

  // TODO handle invalid leagueId (404)
  // https://api.pathofexile.com/leagues/Sanctum
  public LeagueDto retrieveLeague(String leagueId) {
    log.info("Retrieving league (id: {}) from GGG...", leagueId);
    Mono<LeagueGgg> mono = webClient.get().uri(GET_LEAGUES_URI + "/" + leagueId).retrieve()
        .bodyToMono(LeagueGgg.class);

    LeagueGgg leagueGgg = mono.block();
    log.info("League ({}) has been retrieved from GGG.", leagueId);

    return this.leagueMapper.toDtoFromGgg(leagueGgg);
  }

}
