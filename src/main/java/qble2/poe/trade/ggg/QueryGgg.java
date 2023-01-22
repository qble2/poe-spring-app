package qble2.poe.trade.ggg;

import java.util.ArrayList;
import java.util.List;
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
public class QueryGgg {

  @JsonProperty("name")
  private String name;

  @JsonProperty("type")
  private String type;

  @JsonProperty("status")
  private StringFilterValueGgg status;

  @JsonProperty("stats")
  private List<StatFilterGroupGgg> statFilterGroups = new ArrayList<>();

  @JsonProperty("filters")
  private QueryFiltersGgg filters = new QueryFiltersGgg();
}
