package qble2.poe.trade.enums;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public enum ItemRarityEnum {

  @JsonProperty("normal")
  NORMAL("normal"),

  @JsonProperty("magic")
  MAGIC("magic"),

  @JsonProperty("rare")
  RARE("rare"),

  @JsonProperty("unique")
  UNIQUE("unique"),

  @JsonProperty("uniquefoil")
  UNIQUE_FOIL("uniquefoil"),

  @JsonProperty("nonunique")
  NON_UNIQUE("nonunique");

  private String value;

  private ItemRarityEnum(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return this.value;
  }

}
