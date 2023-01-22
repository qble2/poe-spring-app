package qble2.poe.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class StatItemModFilterDto {

  @JsonProperty("id")
  private String id;

  @JsonProperty("min")
  private Integer min;

  @JsonProperty("max")
  private Integer max;

}
