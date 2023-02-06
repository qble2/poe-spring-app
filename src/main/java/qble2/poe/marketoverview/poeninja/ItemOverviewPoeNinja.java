package qble2.poe.marketoverview.poeninja;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ItemOverviewPoeNinja {

  @JsonProperty("lines")
  private List<ItemOverviewLinePoeNinja> lines;

  // @JsonProperty("language")
  // private Object language;

}
