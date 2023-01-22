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
public class TradeFiltersFiltersGgg {

  @JsonProperty("indexed")
  private StringFilterValueGgg indexed;

  @JsonProperty("collapse")
  private StringFilterValueGgg collapse;

  @JsonProperty("sale_type")
  private StringFilterValueGgg saleType;

}
