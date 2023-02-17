package qble2.poe.marketoverview;

import lombok.Getter;

@Getter
public enum ItemCategoryEnum {

  UNKNOWN("Unknown", null), //
  UNIDENTIFIED("Unidentified", null), //
  QUEST_ITEM("Quest item", null), //
  PROPHECY("Prophecy", null), //
  UNIQUE_ITEM("Unique", null), //
  UNIQUE_FOIL_ITEM("Unique Foil", null), //

  CURRENCY("Currency", MarketOverviewTypePoeNinjaEnum.CURRENCY), //
  FRAGMENT("Fragment", MarketOverviewTypePoeNinjaEnum.FRAGMENT), //

  BASE_TYPE("BaseType", MarketOverviewTypePoeNinjaEnum.BASE_TYPE), //
  BEAST("Beast", MarketOverviewTypePoeNinjaEnum.BEAST), //
  CLUSTER_JEWEL("Cluster Jewel", MarketOverviewTypePoeNinjaEnum.CLUSTER_JEWEL), //
  DELIRIUM_ORB("Delirium Orb", MarketOverviewTypePoeNinjaEnum.DELIRIUM_ORB), //
  DIVINATION_CARD("Divination Card", MarketOverviewTypePoeNinjaEnum.DIVINATION_CARD), //
  ESSENCE("Essence", MarketOverviewTypePoeNinjaEnum.ESSENCE), //
  FOSSIL("Fossil", MarketOverviewTypePoeNinjaEnum.FOSSIL), //

  // disabled because inaccurate
  // @JsonProperty("HelmetEnchant")
  // HELMET_ENCHANT("HelmetEnchant", MarketOverviewTypePoeNinjaEnum.HELMET_ENCHANT), //

  INCUBATOR("Incubator", MarketOverviewTypePoeNinjaEnum.INCUBATOR), //
  INVITATION("Invitation", MarketOverviewTypePoeNinjaEnum.INVITATION), //

  MAP("Map", MarketOverviewTypePoeNinjaEnum.MAP), //
  UNIQUE_MAP("UniqueMap", MarketOverviewTypePoeNinjaEnum.UNIQUE_MAP), //
  BLIGHTED_MAP("Blighted Map", MarketOverviewTypePoeNinjaEnum.BLIGHTED_MAP), //
  BLIGHT_RAVAGED_MAP("Blight-Ravaged Map", MarketOverviewTypePoeNinjaEnum.BLIGHT_RAVAGED_MAP), //
  SCOURGED_MAP("Scourged Map", MarketOverviewTypePoeNinjaEnum.SCOURGED_MAP), //

  OIL("Oil", MarketOverviewTypePoeNinjaEnum.OIL), //
  RESONATOR("Resonator", MarketOverviewTypePoeNinjaEnum.RESONATOR), //
  SCARAB("Scarab", MarketOverviewTypePoeNinjaEnum.SCARAB), //

  SKILL_GEM("Skill Gem", MarketOverviewTypePoeNinjaEnum.SKILL_GEM), //

  UNIQUE_ACCESSORY("Unique Accessory", MarketOverviewTypePoeNinjaEnum.UNIQUE_ACCESSORY), //
  UNIQUE_ARMOUR("Unique Armour", MarketOverviewTypePoeNinjaEnum.UNIQUE_ARMOUR), //
  UNIQUE_FLASK("Unique Flask", MarketOverviewTypePoeNinjaEnum.UNIQUE_FLASK), //
  UNIQUE_JEWEL("Unique Jewel", MarketOverviewTypePoeNinjaEnum.UNIQUE_JEWEL), //
  UNIQUE_WEAPON("Unique Weapon", MarketOverviewTypePoeNinjaEnum.UNIQUE_WEAPON), //

  VIAL("Vial", MarketOverviewTypePoeNinjaEnum.VIAL), //

  // Expedition artifact
  ARTIFACT("Artifact", MarketOverviewTypePoeNinjaEnum.ARTIFACT), //

  ;

  private String value;
  private MarketOverviewTypePoeNinjaEnum poeNinjaTypeEnum;

  ItemCategoryEnum(String value, MarketOverviewTypePoeNinjaEnum poeNinjaTypeEnum) {
    this.value = value;
    this.poeNinjaTypeEnum = poeNinjaTypeEnum;
  }

}
