package qble2.poe.stash;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ColourGgg {

  @JsonProperty("r")
  private int r;

  @JsonProperty("g")
  private int g;

  @JsonProperty("b")
  private int b;

}
