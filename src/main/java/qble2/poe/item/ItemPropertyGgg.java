package qble2.poe.item;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
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
        case "Level" -> firstValue.replaceAll("\\s\\(Max\\)", "");
        case "Quality" -> firstValue.replace("\\+", "").replace("%", "");

        default -> firstValue;
      };
    }
  }

}
