package qble2.poe.character;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import qble2.poe.item.ItemGgg;

@Data
public class GetCharacterItemsGgg {

  @JsonProperty("items")
  private List<ItemGgg> items = new ArrayList<>();

  @JsonProperty("character")
  private CharacterGgg character;

}
