package qble2.poe.item;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import qble2.poe.character.Character;
import qble2.poe.marketoverview.ItemCategoryEnum;
import qble2.poe.stash.StashTab;

@Entity(name = "Item")
@Table(name = "Item")
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Accessors(chain = true, fluent = false)
public class Item {

  @Id
  @Column(name = "id", updatable = false, nullable = false)
  @EqualsAndHashCode.Include
  private String id;

  @Column(name = "leagueId", nullable = false)
  private String leagueId;

  @Column(name = "name")
  private String name;

  // @Column(name = "typeLine")
  // private String typeLine;

  @Column(name = "baseType")
  private String baseType;

  @Column(name = "frameType")
  private int frameType;

  @Column(name = "ilvl")
  private int ilvl;

  @Column(name = "inventoryId")
  private String inventoryId;

  @Column(name = "verified")
  private boolean verified;

  @Column(name = "identified")
  private boolean identified;

  @Column(name = "corrupted")
  private Boolean corrupted;

  @Column(name = "searing")
  private Boolean searing;

  @Column(name = "tangled")
  private Boolean tangled;

  @Column(name = "fractured")
  private Boolean fractured;

  @Column(name = "isRelic")
  private Boolean isRelic;

  @Column(name = "foilVariation")
  private Integer foilVariation;

  @Column(name = "w")
  private int w;

  @Column(name = "h")
  private int h;

  @Column(name = "x", nullable = true)
  private Integer x;

  @Column(name = "y", nullable = true)
  private Integer y;

  @Column(name = "descrText", nullable = true)
  private String descrText;

  @Column(name = "stackSize", nullable = true)
  private Integer stackSize;

  @Column(name = "maxStackSize", nullable = true)
  private Integer maxStackSize;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "item",
      fetch = FetchType.LAZY)
  @Builder.Default
  @ToString.Exclude
  private List<ItemProperty> properties = new ArrayList<>();

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "item",
      fetch = FetchType.LAZY)
  @Builder.Default
  @ToString.Exclude
  private List<ItemRequirement> requirements = new ArrayList<>();

  // private Socket[] sockets;
  // private SocketedItem[] socketedItems;

  @ElementCollection(fetch = FetchType.LAZY)
  @Builder.Default
  @ToString.Exclude
  private List<String> enchantMods = new ArrayList<>();

  @ElementCollection(fetch = FetchType.LAZY)
  @Builder.Default
  @ToString.Exclude
  private List<String> implicitMods = new ArrayList<>();

  @ElementCollection(fetch = FetchType.LAZY)
  @Builder.Default
  @ToString.Exclude
  private List<String> explicitMods = new ArrayList<>();

  @ElementCollection(fetch = FetchType.LAZY)
  @Builder.Default
  @ToString.Exclude
  private List<String> craftedMods = new ArrayList<>();

  @ElementCollection(fetch = FetchType.LAZY)
  @Builder.Default
  @ToString.Exclude
  private List<String> fracturedMods = new ArrayList<>();

  @ElementCollection(fetch = FetchType.LAZY)
  @Builder.Default
  @ToString.Exclude
  private List<String> utilityMods = new ArrayList<>();

  @ElementCollection(fetch = FetchType.LAZY)
  @Builder.Default
  @ToString.Exclude
  private List<String> flavourText = new ArrayList<>();

  @Column(name = "icon")
  private String icon;

  @ManyToOne(fetch = FetchType.LAZY, optional = true)
  @ToString.Exclude
  private Character character;

  @ManyToOne(fetch = FetchType.LAZY, optional = true)
  @ToString.Exclude
  private StashTab stashTab;

  // calculated fields
  @Column(name = "category")
  private ItemCategoryEnum category;

  @Column(name = "poeNinjaDetailsId")
  private String poeNinjaDetailsId;

  @Column(name = "chaosValue", nullable = true)
  private Double chaosValue;

  // MapStruct collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED
  public void addProperty(ItemProperty itemProperty) {
    this.properties.add(itemProperty);
    itemProperty.setItem(this);
  }

  // MapStruct collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED
  public void addRequirement(ItemRequirement itemRequirement) {
    this.requirements.add(itemRequirement);
    itemRequirement.setItem(this);
  }

  //
  public String getPropertyValue(ItemPropertyEnum itemPropertyEnum) {
    String value = null;
    Optional<ItemProperty> itemPropertyOptional = this.getProperties().stream()
        .filter(itemProperty -> itemPropertyEnum.getName().equals(itemProperty.getName()))
        .findFirst();
    if (itemPropertyOptional.isPresent()) {
      value = itemPropertyOptional.get().getValue();
    }
    return value;
  }

  public String getRequirementValue(ItemRequirementEnum itemRequirementEnum) {
    String value = null;
    Optional<ItemRequirement> itemRequirementOptional = this.getRequirements().stream()
        .filter(itemRequirement -> itemRequirementEnum.getName().equals(itemRequirement.getName()))
        .findFirst();
    if (itemRequirementOptional.isPresent()) {
      value = itemRequirementOptional.get().getValue();
    }
    return value;
  }

}
