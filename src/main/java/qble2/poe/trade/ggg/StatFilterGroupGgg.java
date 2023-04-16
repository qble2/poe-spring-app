package qble2.poe.trade.ggg;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(Include.NON_NULL)
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class StatFilterGroupGgg {

  @JsonProperty("type")
  private String type;

  @JsonProperty("filters")
  @Builder.Default
  private List<StatItemModFilterGgg> filters = new ArrayList<>();

  @JsonProperty("disabled")
  private Boolean disabled;

  @JsonProperty("value")
  private NumericalFilterValueGgg value;

}
