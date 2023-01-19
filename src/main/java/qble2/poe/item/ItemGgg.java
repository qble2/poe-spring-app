package qble2.poe.item;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
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

  @JsonProperty("searing")
  private Boolean searing;

  @JsonProperty("tangled")
  private Boolean tangled;

  @JsonProperty("fractured")
  private Boolean fractured;

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

  // @JsonProperty("properties")
  // private Requirement[] properties;

  // @JsonProperty("requirements")
  // private Requirement[] requirements;

  // @JsonProperty("sockets")
  // private Socket[] sockets;

  // @JsonProperty("socketedItems")
  // private SocketedItem[] socketedItems;

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
