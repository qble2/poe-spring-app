package qble2.poe.ladder.ggg;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LadderEntryAccountGgg {

  @JsonProperty("name")
  private String name;

  @JsonProperty("realm")
  private String realm;

  @JsonProperty("challenges")
  private LadderEntryAccountChallengesGgg challenges;

  @JsonProperty("twitch")
  private LadderEntryAccountTwitchGgg twitch;

}
