package qble2.poe.trade.enums;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public enum SortPriceEnum {

  @JsonProperty("asc")
  ASC("asc"),

  @JsonProperty("desc")
  DESC("desc");

  private String value;

  private SortPriceEnum(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return this.value;
  }

}
