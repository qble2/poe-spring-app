package qble2.poe.trade.enums;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public enum ListedSinceEnum {

  @JsonProperty("1day")
  ONE_DAY("1day"),

  @JsonProperty("3days")
  THREE_DAYS("3days"),

  @JsonProperty("1week")
  ONE_WEEK("1week"),

  @JsonProperty("2weeks")
  TWO_WEEK("2weeks"),

  @JsonProperty("1months")
  ONE_MONTH("1months"),

  @JsonProperty("2months")
  TWO_MONTHS("2months");

  private String value;

  private ListedSinceEnum(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return this.value;
  }

}
