package qble2.poe.trade.ggg;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(Include.NON_NULL)
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class StatItemModFilterGgg {

  @JsonProperty("id")
  private String id;

  @JsonProperty("value")
  private NumericalFilterValueGgg value;

  @JsonProperty("disabled")
  private Boolean disabled;

}
