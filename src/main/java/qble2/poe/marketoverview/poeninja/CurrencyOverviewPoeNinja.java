package qble2.poe.marketoverview.poeninja;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CurrencyOverviewPoeNinja {

  @JsonProperty("lines")
  private List<CurrencyOverviewLinePoeNinja> lines;

  // icons and tradeId can be found here
  @JsonProperty("currencyDetails")
  private List<CurrencyOverviewCurrencyDetailPoeNinja> currencyDetails;

  // @JsonProperty("language")
  // private Object language;

}
