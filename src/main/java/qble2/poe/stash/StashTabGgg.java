package qble2.poe.stash;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class StashTabGgg {

  @JsonProperty("id")
  private String id;

  @JsonProperty("i")
  private int i;

  @JsonProperty("n")
  private String n;

  @JsonProperty("type")
  private String type;

  @JsonProperty("colour")
  private ColourGgg colour;

}
