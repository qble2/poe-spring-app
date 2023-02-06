package qble2.poe.ladder.ggg;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LadderEntryDepthGgg {

  @JsonProperty("default")
  private int defaultDepth;

  @JsonProperty("solo")
  private int soloDepth;

}
