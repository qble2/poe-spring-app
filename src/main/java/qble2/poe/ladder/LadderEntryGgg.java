package qble2.poe.ladder;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LadderEntryGgg {

  @JsonProperty("rank")
  private long rank;

  @JsonProperty("dead")
  private boolean isDead;

  @JsonProperty("public")
  private boolean isPublic;

  @JsonProperty("account")
  private LadderEntryAccountGgg account;

  @JsonProperty("character")
  private LadderEntryCharacterGgg character;

}
