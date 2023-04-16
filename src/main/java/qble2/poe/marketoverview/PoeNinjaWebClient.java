package qble2.poe.marketoverview;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import qble2.poe.RequestLogUtils;
import qble2.poe.marketoverview.poeninja.CurrencyOverviewPoeNinja;
import qble2.poe.marketoverview.poeninja.ItemOverviewPoeNinja;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class PoeNinjaWebClient {

  @Autowired
  private MarketOverviewMapper marketOverviewMapper;

  private static final String BROWSER_USER_AGENT =
      "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36";

  // BaseType: Size: 12.7 MB
  private static final int MAX_IN_MEMORY_SIZE_IN_MB = 15;

  private static final String POE_NINJA_BASE_URL = "https://poe.ninja/api/data";
  public static final String CURRENCY_OVERVIEW_URI = "/currencyoverview";
  public static final String ITEM_OVERVIEW_URI = "/itemoverview";

  // handling DataBufferLimitException: Exceeded limit on max bytes to buffer
  private final ExchangeStrategies exchangeStrategies =
      ExchangeStrategies.builder().codecs(configurer -> configurer.defaultCodecs()
          .maxInMemorySize(MAX_IN_MEMORY_SIZE_IN_MB * 1024 * 1024)).build();

  private final WebClient webClient = WebClient.builder().filters(exchangeFilterFunctions -> {
        exchangeFilterFunctions.add(RequestLogUtils.logRequest());
        exchangeFilterFunctions.add(RequestLogUtils.logResponse());
      }).defaultHeader("User-Agent", BROWSER_USER_AGENT).exchangeStrategies(exchangeStrategies)
      .baseUrl(POE_NINJA_BASE_URL).exchangeStrategies(exchangeStrategies).build();

  // https://poe.ninja/api/data/currencyoverview?league=Standard&type=Currency
  // https://poe.ninja/api/data/currencyoverview?league=Standard&type=Fragment
  public List<MarketOverviewDto> getCurrencyOverview(String leagueId,
      MarketOverviewTypePoeNinjaEnum typeEnum) {
    String type = typeEnum.getValue();
    log.info("Retrieving currency overview (league: {} , type: {}) from poe.ninja...", leagueId,
        type);
    Mono<CurrencyOverviewPoeNinja> mono = webClient.get()
        .uri(uriBuilder -> uriBuilder.path(CURRENCY_OVERVIEW_URI).queryParam("league", leagueId)
            .queryParam("type", type).build())
        .retrieve().bodyToMono(CurrencyOverviewPoeNinja.class);

    Optional<CurrencyOverviewPoeNinja> currencyOverviewPoeNinja = mono.blockOptional();
    if (currencyOverviewPoeNinja.isPresent()) {
      log.info("Currency overview (league: {} , type: {}) have been retrieved from poe.ninja.",
          leagueId, type);
      return this.marketOverviewMapper.toDtoListFromCurrencyOverviewPoeNinjaList(
          currencyOverviewPoeNinja.get().getLines(), leagueId, typeEnum);
    }

    return Collections.emptyList();
  }

  // https://poe.ninja/api/data/itemoverview?league=Standard&type=Oil
  public List<MarketOverviewDto> getItemOverview(String leagueId,
      MarketOverviewTypePoeNinjaEnum typeEnum) {
    String type = typeEnum.getValue();
    log.info("Retrieving item overview (league: {} , type: {}) from poe.ninja...", leagueId, type);

    Mono<ItemOverviewPoeNinja> mono =
        webClient.get()
            .uri(uriBuilder -> uriBuilder.path(ITEM_OVERVIEW_URI).queryParam("league", leagueId)
                .queryParam("type", type).build())
            .retrieve().bodyToMono(ItemOverviewPoeNinja.class);

    Optional<ItemOverviewPoeNinja> itemOverviewPoeNinja = mono.blockOptional();
    if (itemOverviewPoeNinja.isPresent()) {
      log.info("Item overview (league: {} , type: {}) have been retrieved from poe.ninja.",
          leagueId,
          type);
      return this.marketOverviewMapper
          .toDtoListFromItemOverviewPoeNinjaList(itemOverviewPoeNinja.get().getLines(), leagueId,
              typeEnum);
    }

    return Collections.emptyList();
  }

}
