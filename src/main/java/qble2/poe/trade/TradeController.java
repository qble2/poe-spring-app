package qble2.poe.trade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import qble2.poe.trade.ggg.TradeSearchResponseGgg;

@RestController
@RequestMapping(path = "api/trades", produces = MediaType.APPLICATION_JSON_VALUE)
public class TradeController {

  @Autowired
  private TradeService tradeService;

  @PostMapping(path = "/search")
  public TradeSearchResponseGgg search(
      @RequestBody(required = true) TradeSearchPayloadDto tradeSearchPayload) {
    return this.tradeService.search(tradeSearchPayload);
  }

}
