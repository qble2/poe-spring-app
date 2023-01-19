package qble2.poe.enchant;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class EnchantedItemBaseWithOccurrenceProjectionDto {

  // SQL count(*) returns a long
  @JsonProperty("occurrence")
  private long occurrence;

  @JsonProperty("itemName")
  private String itemName;

  @JsonProperty("itemBaseType")
  private String itemBaseType;

  public EnchantedItemBaseWithOccurrenceProjectionDto(long occurrence, String itemName, String itemBaseType) {
    super();
    this.occurrence = occurrence;
    this.itemName = itemName;
    this.itemBaseType = itemBaseType;
  }

}
