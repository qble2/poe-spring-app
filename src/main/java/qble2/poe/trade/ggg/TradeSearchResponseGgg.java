package qble2.poe.trade.ggg;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
public class TradeSearchResponseGgg {

  @JsonProperty("id")
  private String id;

  @JsonProperty("complexity")
  private int complexity;

  @JsonProperty("total")
  private int total;

  @JsonProperty("result")
  private List<String> result;

}
