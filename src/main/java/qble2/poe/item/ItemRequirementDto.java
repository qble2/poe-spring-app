package qble2.poe.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ItemRequirementDto {

  @JsonProperty("name")
  private String name;

  @JsonProperty("value")
  private String value;

  //
  @JsonIgnore
  private ItemDto item;

}
