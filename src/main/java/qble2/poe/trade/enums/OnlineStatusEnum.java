package qble2.poe.trade.enums;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public enum OnlineStatusEnum {

  @JsonProperty("online")
  ONLINE("online"),

  @JsonProperty("onlineleague")
  ONLINE_IN_LEAGUE("onlineleague"),

  @JsonProperty("any")
  ANY("any");

  private String value;

  private OnlineStatusEnum(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return this.value;
  }

}
