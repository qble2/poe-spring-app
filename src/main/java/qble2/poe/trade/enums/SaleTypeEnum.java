package qble2.poe.trade.enums;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public enum SaleTypeEnum {

  @JsonProperty("priced_with_info")
  PRICE_WITH_NOTE("priced_with_info"),

  @JsonProperty("unpriced")
  NO_LISTED_PRICE("unpriced"),

  @JsonProperty("any")
  ANY("any");

  private String value;

  private SaleTypeEnum(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return this.value;
  }

}
