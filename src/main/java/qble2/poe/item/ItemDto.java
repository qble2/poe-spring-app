package qble2.poe.item;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import qble2.poe.marketoverview.ItemCategoryEnum;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ItemDto {

  @JsonProperty("id")
  @EqualsAndHashCode.Include
  private String id;

  @JsonProperty("leagueId")
  private String leagueId;

  @JsonProperty("name")
  private String name;

  // @JsonProperty("typeLine")
  // private String typeLine;

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
  private List<ItemPropertyDto> properties = new ArrayList<>();

  @JsonProperty("requirements")
  private List<ItemRequirementDto> requirements = new ArrayList<>();

  // @JsonProperty("sockets")
  // private Socket[] sockets;

  // @JsonProperty("socketedItems")
  // private SocketedItem[] socketedItems;

  @JsonProperty("enchantMods")
  private List<String> enchantMods = new ArrayList<>();

  @JsonProperty("implicitMods")
  private List<String> implicitMods = new ArrayList<>();

  @JsonProperty("explicitMods")
  private List<String> explicitMods = new ArrayList<>();

  @JsonProperty("craftedMods")
  private List<String> craftedMods = new ArrayList<>();

  @JsonProperty("fracturedMods")
  private List<String> fracturedMods = new ArrayList<>();

  @JsonProperty("utilityMods")
  private List<String> utilityMods = new ArrayList<>();

  @JsonProperty("flavourText")
  private List<String> flavourText = new ArrayList<>();

  @JsonProperty("icon")
  private String icon;

  // calculated fields
  @JsonProperty("category")
  private ItemCategoryEnum category;

  @JsonProperty("poeNinjaDetailsId")
  private String poeNinjaDetailsId;

  @JsonProperty("chaosValue")
  private Double chaosValue;

  // MapStruct collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED
  public void addProperty(ItemPropertyDto itemPropertyDto) {
    this.properties.add(itemPropertyDto);
    itemPropertyDto.setItem(this);
  }

  // MapStruct collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED
  public void addRequirement(ItemRequirementDto itemRequirementDto) {
    this.requirements.add(itemRequirementDto);
    itemRequirementDto.setItem(this);
  }

}
