package qble2.poe.item;

import lombok.Getter;

public enum ItemPropertyEnum {

  LEVEL("Level"), QUALITY("Quality"), MAP_TIER("Map Tier"), ATLAS_REGION("Atlas Region"),

  ;

  @Getter
  private String name;

  ItemPropertyEnum(String name) {
    this.name = name;
  }

}
