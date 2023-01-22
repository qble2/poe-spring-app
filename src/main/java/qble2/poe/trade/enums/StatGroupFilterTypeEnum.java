package qble2.poe.trade.enums;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public enum StatGroupFilterTypeEnum {

  @JsonProperty("and")
  AND("and"),

  @JsonProperty("not")
  NOT("not"),

  @JsonProperty("count")
  COUNT("count");

  private String value;

  private StatGroupFilterTypeEnum(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return this.value;
  }

}
