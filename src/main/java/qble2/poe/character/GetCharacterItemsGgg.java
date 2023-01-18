package qble2.poe.character;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import qble2.poe.item.ItemGgg;

@Data
public class GetCharacterItemsGgg {

  @JsonProperty("items")
  private List<ItemGgg> items;

  @JsonProperty("character")
  private CharacterGgg character;

}
