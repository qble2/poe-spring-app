package qble2.poe.trade;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import qble2.poe.RequestLogUtils;
import qble2.poe.trade.ggg.TradeSearchPayloadGgg;
import qble2.poe.trade.ggg.TradeSearchResponseGgg;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class TradeWebClientGgg {

  private static final String BROWSER_USER_AGENT =
      "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36";
  private static final int MAX_IN_MEMORY_SIZE_IN_MB = 1;
  private static final String GGG_BASE_URL = "https://www.pathofexile.com";
  public static final String TRADE_SEARCH_URI = "/api/trade/search";
  public static final String TRADE_FETCH_URI = "/api/trade/fetch";

  private ObjectMapper mapper = new ObjectMapper();

  @Autowired
  private TradeMapper tradeMapper;

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

  // TODO handle 400 Bad Request (Invalid query/sort/filter/...)
  // https://www.pathofexile.com/api/trade/search/Sanctum
  public TradeSearchResponseGgg search(TradeSearchPayloadDto tradeSearchPayloadDto) {
    TradeSearchPayloadGgg tradeSearchPayloadGgg =
        this.tradeMapper.toGggFromDto(tradeSearchPayloadDto);
    JsonNode payloadJsonNode = mapper.valueToTree(tradeSearchPayloadGgg);

    log.info("Sending trade search request with payload: \n {}", payloadJsonNode.toPrettyString());
    Mono<TradeSearchResponseGgg> mono =
        webClient.post().uri(TRADE_SEARCH_URI + "/" + tradeSearchPayloadDto.getLeagueId())
            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(payloadJsonNode)).retrieve()
            .bodyToMono(TradeSearchResponseGgg.class);

    return mono.block();
  }

}
