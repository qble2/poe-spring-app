package qble2.poe.character;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import qble2.poe.item.ItemDto;

@Data
public class CharacterDto {

  @JsonProperty("name")
  private String name;

  @JsonProperty("accountName")
  private String accountName;

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

  @JsonProperty("items")
  private List<ItemDto> items = new ArrayList<>();

}
