package qble2.poe.marketoverview.poeninja;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
public class ItemOverviewPoeNinja {

  @JsonProperty("lines")
  private List<ItemOverviewLinePoeNinja> lines;

}
