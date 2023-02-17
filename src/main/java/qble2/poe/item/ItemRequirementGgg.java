package qble2.poe.item;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ItemRequirementGgg {

  @JsonProperty("name")
  private String name;

  @JsonProperty("value")
  private String value;

  @JsonProperty("values")
  public void unpackNestedRequirements(List<List<String>> values) {
    this.value = values.get(0).get(0);
  }

}
