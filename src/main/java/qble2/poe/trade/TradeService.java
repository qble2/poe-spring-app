package qble2.poe.trade;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import qble2.poe.trade.ggg.TradeSearchResponseGgg;

@Service
@Transactional
// @AllArgsConstructor needed to be able to inject mocked dependencies for unit testing
@AllArgsConstructor
public class TradeService {

  @Autowired
  private TradeWebClientGgg tradeWebClientGgg;

  public TradeSearchResponseGgg search(TradeSearchPayloadDto tradeSearchPayload) {
    return this.tradeWebClientGgg.search(tradeSearchPayload);
  }

}
