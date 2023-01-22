package qble2.poe.trade.enums;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

// TODO BKE to be completed
@Getter
public enum ItemCategoryEnum {

  @JsonProperty("armour")
  ANY_ARMOUR("armour"),

  @JsonProperty("armour.chest")
  BODY_ARMOUR("armour.chest"),

  @JsonProperty("armour.helmet")
  HELMET("armour.helmet"),

  @JsonProperty("armour.gloves")
  GLOVES("armour.gloves"),

  @JsonProperty("armour.boots")
  BOOTS("armour.boots"),

  @JsonProperty("armour.shield")
  SHIELD("armour.shield"),

  @JsonProperty("jewel")
  ANY_JEWEL("jewel"),

  @JsonProperty("jewel.base")
  BASE_JEWEL("jewel.base"),

  @JsonProperty("jewel.abyss")
  ABYSS_JEWEL("jewel.abyss"),

  @JsonProperty("jewel.cluster")
  CLUSTER_JEWEL("jewel.cluster"),

  @JsonProperty("map")
  MAP("map"),

  @JsonProperty("gem")
  ANY_GEM("gem"),

  @JsonProperty("gem.activegem")
  ACTIVE_GEM("gem.activegem"),

  @JsonProperty("gem.supportgem")
  SUPPORT_GEM("gem.supportgem"),

  @JsonProperty("gem.supportgemplus")
  AWAKENED_GEM("gem.supportgemplus"),

  @JsonProperty("flask")
  FLASK("flask");

  private String value;

  private ItemCategoryEnum(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return this.value;
  }

}
