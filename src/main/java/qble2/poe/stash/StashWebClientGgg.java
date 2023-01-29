package qble2.poe.stash;

import java.util.List;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.NumberUtils;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import lombok.extern.slf4j.Slf4j;
import qble2.poe.RequestLogUtils;
import qble2.poe.exception.TooManyRequestsException;
import qble2.poe.item.ItemDto;
import qble2.poe.item.ItemGgg;
import qble2.poe.item.ItemMapper;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class StashWebClientGgg {

  @Autowired
  private StashMapper stashMapper;

  @Autowired
  private ItemMapper itemMapper;

  private static final String BROWSER_USER_AGENT =
      "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36";
  private static final int MAX_IN_MEMORY_SIZE_IN_MB = 1;
  private static final String GGG_BASE_URL = "https://www.pathofexile.com";
  public static final String GET_STASH_ITEMS_URI = "/character-window/get-stash-items";

  // handling DataBufferLimitException: Exceeded limit on max bytes to buffer
  private static final ExchangeStrategies exchangeStrategies =
      ExchangeStrategies.builder().codecs(configurer -> configurer.defaultCodecs()
          .maxInMemorySize(MAX_IN_MEMORY_SIZE_IN_MB * 1024 * 1024)).build();

  private static final WebClient webClient =
      WebClient.builder().filters(exchangeFilterFunctions -> {
        exchangeFilterFunctions.add(RequestLogUtils.logRequest());
        exchangeFilterFunctions.add(RequestLogUtils.logResponse());
      }).baseUrl(GGG_BASE_URL).exchangeStrategies(exchangeStrategies).build();

  // GGG does not provide a dedicated enpoint to retrieve stash tab headers alone
  // https://www.pathofexile.com/character-window/get-stash-items?accountName=${accountName}&realm=pc&league=Sanctum?tabIndex=0&tabs=1&
  public List<StashTabDto> retrieveStashTabs(String accountName, String poeSessionId,
      String leagueId) {
    log.info("retrieving stash tabs headers (accountName: {} , leagueId: {}) from GGG...",
        accountName, leagueId);
    List<StashTabGgg> listOfStashTabGgg =
        retrieveStash(accountName, poeSessionId, leagueId, 0, true).getTabs();
    log.info("stash tabs headers (accountName: {} , leagueId: {}) have been retrieved from GGG.",
        accountName, leagueId);

    return this.stashMapper.toDtoListFromGggList(listOfStashTabGgg, leagueId);
  }

  // https://www.pathofexile.com/character-window/get-stash-items?accountName=${accountName}&realm=pc&league=Sanctum?tabIndex=0
  public List<ItemDto> retrieveStashTabItems(String accountName, String poeSessionId,
      String leagueId, int tabIndex) {
    log.info(
        "retrieving stash tab items (accountName: {} , leagueId: {} , tabIndex: {}) from GGG...",
        accountName, leagueId, tabIndex);
    List<ItemGgg> listOfItemGgg =
        retrieveStash(accountName, poeSessionId, leagueId, tabIndex, false).getItems();
    log.info(
        "stash tab items (accountName: {} , leagueId: {} , tabIndex: {}) have been retrieved from GGG.",
        accountName, leagueId, tabIndex);

    return this.itemMapper.toDtoListFromGggList(listOfItemGgg);
  }

  /////
  /////
  /////

  private GetStashItemsGgg retrieveStash(String accountName, String poeSessionId, String leagueId,
      int tabIndex, boolean isRetrieveTabHeaders) {
    Mono<GetStashItemsGgg> mono = webClient.get()
        .uri(uriBuilder -> uriBuilder.path(GET_STASH_ITEMS_URI)
            .queryParam("accountName", accountName).queryParam("realm", "pc")
            .queryParam("league", leagueId).queryParam("tabIndex", tabIndex)
            .queryParam("tabs", BooleanUtils.toInteger(isRetrieveTabHeaders)).build())
        .header("User-Agent", BROWSER_USER_AGENT).cookie("POESESSID", poeSessionId).retrieve()
        .onStatus(status -> status.value() == HttpStatus.TOO_MANY_REQUESTS.value(), response -> {
          int retryAfter = NumberUtils.parseNumber(response.headers().header("Retry-After").get(0),
              Integer.class);
          return Mono.error(new TooManyRequestsException(
              "Rate limit exceeded, Please try again later.", retryAfter));
        }).bodyToMono(GetStashItemsGgg.class);

    return mono.block();
  }

}
