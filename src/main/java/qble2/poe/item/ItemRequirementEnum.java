package qble2.poe.item;

import lombok.Getter;

public enum ItemRequirementEnum {

  // GGG mixes both versions at the same time depending on the item
  STR("Str"), STRENGTH("Strength"),

  DEXT("Dex"), DEXTERITY("Dexterity"),

  INT("Int"), INTELLIGENCE("Intelligence"),

  ;

  @Getter
  private String name;

  ItemRequirementEnum(String name) {
    this.name = name;
  }

}
