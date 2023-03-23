package qble2.poe.character;

import java.util.List;
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
import qble2.poe.item.ItemMapper;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class CharacterWebClientGgg {

  @Autowired
  private CharacterMapper characterMapper;

  @Autowired
  private ItemMapper itemMapper;

  private static final String BROWSER_USER_AGENT =
      "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36";
  private static final int MAX_IN_MEMORY_SIZE_IN_MB = 1;
  private static final String GGG_BASE_URL = "https://www.pathofexile.com";
  public static final String GET_CHARACTERS_URI = "/character-window/get-characters";
  public static final String GET_CHARACTER_ITEMS_URI = "/character-window/get-items";

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

  // https://www.pathofexile.com/character-window/get-characters
  public List<CharacterDto> retrieveAccountCharacters(String accountName) {
    log.info("Retrieving characters (accountName: {}) from GGG...", accountName);
    Mono<CharacterGgg[]> mono = webClient.get()
        .uri(uriBuilder -> uriBuilder.path(GET_CHARACTERS_URI)
            .queryParam("accountName", accountName).queryParam("realm", "pc").build())
        // .cookie("POESESSID", poeSessionId)
        .retrieve()
        .onStatus(status -> status.value() == HttpStatus.TOO_MANY_REQUESTS.value(), response -> {
          int retryAfter = NumberUtils.parseNumber(response.headers().header("Retry-After").get(0),
              Integer.class);
          return Mono.error(new TooManyRequestsException(
              "Rate limit exceeded, Please try again later.", retryAfter));
        }).bodyToMono(CharacterGgg[].class);

    List<CharacterGgg> listOfCharacterGgg = List.of(mono.block());
    log.info("{}x characters (accountName: {}) have been retrieved from GGG.",
        listOfCharacterGgg.size(), accountName);

    return this.characterMapper.toDtoListFromGggList(listOfCharacterGgg, accountName);
  }

  // https://www.pathofexile.com/character-window/get-items?accountName=${accountName}&character=QbleSanctum
  public List<ItemDto> retrieveCharacterItems(String accountName, String characterName) {
    log.info("Retrieving character items (accountName: {} , characterName: {}) from GGG...",
        accountName, characterName);
    Mono<GetCharacterItemsGgg> mono = webClient.get()
        .uri(uriBuilder -> uriBuilder.path(GET_CHARACTER_ITEMS_URI)
            .queryParam("accountName", accountName).queryParam("realm", "pc")
            .queryParam("character", characterName).build())
        // .cookie("POESESSID", poeSessionId)
        .retrieve()
        .onStatus(status -> status.value() == HttpStatus.TOO_MANY_REQUESTS.value(), response -> {
          int retryAfter = NumberUtils.parseNumber(response.headers().header("Retry-After").get(0),
              Integer.class);
          return Mono.error(new TooManyRequestsException(
              "Rate limit exceeded, Please try again later.", retryAfter));
        }).bodyToMono(GetCharacterItemsGgg.class);

    GetCharacterItemsGgg getCharacterItemsGgg = mono.block();
    log.info("Character items (accountName: {} , characterName: {}) have been retrieved from GGG.",
        accountName, characterName);

    return this.itemMapper.toDtoListFromGggList(getCharacterItemsGgg.getItems());
  }

}
