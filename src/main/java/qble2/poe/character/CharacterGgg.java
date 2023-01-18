package qble2.poe.character;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CharacterGgg {

  @JsonProperty("name")
  private String name;

  @JsonProperty("league")
  private String leagueId;

  @JsonProperty("classId")
  private int classId;

  @JsonProperty("ascendancyClass")
  private int ascendancyClass;

  @JsonProperty("class")
  private String characterClass;

  @JsonProperty("level")
  private int level;

  @JsonProperty("experience")
  private double experience;

}
