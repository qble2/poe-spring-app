package qble2.poe.trade.ggg;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(Include.NON_NULL)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryFiltersGgg {

  @JsonProperty("misc_filters")
  private MiscFiltersGgg miscFilters = new MiscFiltersGgg();

  @JsonProperty("trade_filters")
  private TradeFiltersGgg tradeFilters = new TradeFiltersGgg();

  @JsonProperty("type_filters")
  private TypeFiltersGgg typeFilters = new TypeFiltersGgg();

}
