package qble2.poe.ladder.ggg;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LadderEntryGgg {

  @JsonProperty("rank")
  private int rank;

  @JsonProperty("dead")
  private boolean isDead;

  @JsonProperty("public")
  private boolean isPublic;

  @JsonProperty("account")
  private LadderEntryAccountGgg account;

  @JsonProperty("character")
  private LadderEntryCharacterGgg character;

}
