package qble2.poe.marketoverview;

import java.util.stream.Stream;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum MarketOverviewTypePoeNinjaEnum {

  CURRENCY("Currency"), //
  FRAGMENT("Fragment"), //

  BASETYPE("BaseType"), //
  BEAST("Beast"), //
  BLIGHTED_MAP("BlightedMap"), //
  CLUSTER_JEWEL("ClusterJewel"), //
  DELIRIUM_ORB("DeliriumOrb"), //
  DIVINATION_CARD("DivinationCard"), //
  ESSENCE("Essence"), //
  FOSSIL("Fossil"), //

  // disabled because inaccurate
  // @JsonProperty("HelmetEnchant")
  // HELMET_ENCHANT("HelmetEnchant"), //

  INCUBATOR("Incubator"), //
  INVITATION("Invitation"), //
  MAP("Map"), //
  OIL("Oil"), //
  RESONATOR("Resonator"), //
  SCARAB("Scarab"), //
  SKILL_GEM("SkillGem"), //
  UNIQUE_ACCESSORY("UniqueAccessory"), //
  UNIQUE_ARMOUR("UniqueArmour"), //
  UNIQUE_FLASK("UniqueFlask"), //
  UNIQUE_JEWEL("UniqueJewel"), //
  UNIQUE_MAP("UniqueMap"), //
  UNIQUE_WEAPON("UniqueWeapon"), //
  VIAL("Vial"), //

  // Expedition artifact
  ARTIFACT("Artifact");

  @JsonValue
  private String value;

  MarketOverviewTypePoeNinjaEnum(String value) {
    this.value = value;
  }

  @JsonCreator
  public static MarketOverviewTypePoeNinjaEnum decode(final String value) {
    return Stream.of(MarketOverviewTypePoeNinjaEnum.values())
        .filter(targetEnum -> targetEnum.value.equals(value)).findFirst().orElse(null);
  }

}
