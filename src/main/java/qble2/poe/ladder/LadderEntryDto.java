package qble2.poe.ladder;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import qble2.poe.character.CharacterDto;

@Data
public class LadderEntryDto {

  @JsonProperty("rank")
  private int rank;

  @JsonProperty("leagueId")
  private String leagueId;

  @JsonProperty("isPublic")
  private boolean isPublic;

  @JsonProperty("character")
  private CharacterDto character;

  @JsonProperty("officialProfileUrl")
  private String officialProfileUrl;

  @JsonProperty("alternativeProfileUrl")
  private String alternativeProfileUrl;

  @JsonProperty("lastUpdateAt")
  private LocalDateTime lastUpdateAt;

}
