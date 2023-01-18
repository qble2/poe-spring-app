package qble2.poe.ladder;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LadderEntryCharacterGgg {

  @JsonProperty("id")
  private String id;

  @JsonProperty("name")
  private String name;

  @JsonProperty("level")
  private int level;

  @JsonProperty("class")
  private String characterClass;

  @JsonProperty("experience")
  private long experience;

  @JsonProperty("depth")
  private LadderEntryDepthGgg delveDepth;

}
