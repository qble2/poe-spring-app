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
public class TradeFiltersGgg {

  @JsonProperty("filters")
  private TradeFiltersFiltersGgg filters = new TradeFiltersFiltersGgg();

  @JsonProperty("disabled")
  private Boolean disabled;

}
