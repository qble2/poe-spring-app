package qble2.poe.marketoverview.poeninja;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CurrencyOverviewCurrencyDetailPoeNinja {

  @JsonProperty("id")
  private int id;

  @JsonProperty("icon")
  private String icon; // url

  @JsonProperty("name")
  private String name; // Divine Orb

  @JsonProperty("tradeId")
  private String tradeId; // divine

}
