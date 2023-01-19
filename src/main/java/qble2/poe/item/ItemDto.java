package qble2.poe.item;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

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

  @JsonProperty("typeLine")
  private String typeLine;

  @JsonProperty("baseType")
  private String baseType;

  @JsonProperty("frameType")
  private long frameType;

  @JsonProperty("ilvl")
  private long ilvl;

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
  private long w;

  @JsonProperty("h")
  private long h;

  @JsonProperty("x")
  private long x;

  @JsonProperty("y")
  private long y;

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
  private List<String> enchantMods = new ArrayList<>();;

  @JsonProperty("implicitMods")
  private List<String> implicitMods = new ArrayList<>();;

  @JsonProperty("explicitMods")
  private List<String> explicitMods = new ArrayList<>();;

  @JsonProperty("craftedMods")
  private List<String> craftedMods = new ArrayList<>();;

  @JsonProperty("fracturedMods")
  private List<String> fracturedMods = new ArrayList<>();;

  @JsonProperty("utilityMods")
  private List<String> utilityMods = new ArrayList<>();;

  @JsonProperty("flavourText")
  private List<String> flavourText = new ArrayList<>();;

  @JsonProperty("icon")
  private String icon;

}
