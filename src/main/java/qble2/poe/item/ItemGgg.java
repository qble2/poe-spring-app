package qble2.poe.item;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
public class ItemGgg {

  @JsonProperty("id")
  private String id;

  @JsonProperty("league")
  private String league;

  @JsonProperty("name")
  private String name;

  @JsonProperty("typeLine")
  private String typeLine;

  @JsonProperty("baseType")
  private String baseType;

  @JsonProperty("frameType")
  private int frameType;

  @JsonProperty("ilvl")
  private int ilvl;

  @JsonProperty("inventoryId")
  private String inventoryId;

  @JsonProperty("verified")
  private boolean verified;

  @JsonProperty("identified")
  private boolean identified;

  @JsonProperty("corrupted")
  private Boolean corrupted;

  @JsonProperty("searing")
  private Boolean searing;

  @JsonProperty("tangled")
  private Boolean tangled;

  @JsonProperty("fractured")
  private Boolean fractured;

  @JsonProperty("isRelic")
  private Boolean isRelic;

  @JsonProperty("foilVariation")
  private Integer foilVariation;

  @JsonProperty("w")
  private int w;

  @JsonProperty("h")
  private int h;

  @JsonProperty("x")
  private int x;

  @JsonProperty("y")
  private int y;

  @JsonProperty("descrText")
  private String descrText;

  @JsonProperty("stackSize")
  private Integer stackSize;

  @JsonProperty("maxStackSize")
  private Integer maxStackSize;

  @JsonProperty("properties")
  private List<ItemPropertyGgg> properties;

  @JsonProperty("requirements")
  private List<ItemRequirementGgg> requirements;

  @JsonProperty("enchantMods")
  private List<String> enchantMods;

  @JsonProperty("implicitMods")
  private List<String> implicitMods;

  @JsonProperty("explicitMods")
  private List<String> explicitMods;

  @JsonProperty("craftedMods")
  private List<String> craftedMods;

  @JsonProperty("fracturedMods")
  private List<String> fracturedMods;

  @JsonProperty("utilityMods")
  private List<String> utilityMods;

  @JsonProperty("flavourText")
  private List<String> flavourText;

  @JsonProperty("icon")
  private String icon;

}
