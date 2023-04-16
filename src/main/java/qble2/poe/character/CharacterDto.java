package qble2.poe.character;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import qble2.poe.item.ItemDto;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CharacterDto {

  @JsonProperty("name")
  @EqualsAndHashCode.Include
  private String name;

  @JsonProperty("accountName")
  private String accountName;

  @JsonProperty("leagueId")
  private String leagueId;

  @JsonProperty("classId")
  private int classId;

  @JsonProperty("ascendancyClass")
  private int ascendancyClass;

  @JsonProperty("characterClass")
  private String characterClass;

  @JsonProperty("level")
  private int level;

  @JsonProperty("experience")
  private double experience;

  @JsonProperty("items")
  private List<ItemDto> items = new ArrayList<>();

}
