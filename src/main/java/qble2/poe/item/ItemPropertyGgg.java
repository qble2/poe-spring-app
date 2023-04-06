package qble2.poe.item;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ItemPropertyGgg {

  @JsonProperty("name")
  private String name;

  @JsonProperty("value")
  private String value;

  @JsonProperty("values")
  public void unpackNestedProperties(List<List<String>> values) {
    String firstValue = null;

    // GGG can return an empty array ("values": [])
    if (!values.isEmpty()) {
      firstValue = values.get(0).get(0);

      this.value = switch (this.name) {
        case "Level" -> this.value = firstValue.replaceAll("\\s\\(Max\\)", "");
        case "Quality" -> this.value = firstValue.replaceAll("\\+", "").replaceAll("%", "");

        default -> firstValue;
      };
    }
  }

}
