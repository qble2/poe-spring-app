package qble2.poe.item;

import java.util.ArrayList;
import java.util.List;
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
import lombok.experimental.Accessors;
import qble2.poe.character.Character;

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

  @Column(name = "typeLine")
  private String typeLine;

  @Column(name = "baseType")
  private String baseType;

  @Column(name = "frameType")
  private long frameType;

  @Column(name = "ilvl")
  private long ilvl;

  @Column(name = "inventoryId")
  private String inventoryId;

  @Column(name = "verified")
  private boolean verified;

  @Column(name = "identified")
  private boolean identified;

  @Column(name = "searing")
  private Boolean searing;

  @Column(name = "tangled")
  private Boolean tangled;

  @Column(name = "fractured")
  private Boolean fractured;

  @Column(name = "w")
  private long w;

  @Column(name = "h")
  private long h;

  @Column(name = "x")
  private long x;

  @Column(name = "y")
  private long y;

  @Column(name = "descrText")
  private String descrText;

  // private Requirement[] properties;
  // private Requirement[] requirements;
  // private Socket[] sockets;
  // private SocketedItem[] socketedItems;

  // @ElementCollection(fetch = FetchType.LAZY)
  // @Builder.Default
  // private List<String> enchantMods = new ArrayList<>();
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "item")
  @Builder.Default
  private List<ItemEnchantMod> enchantMods = new ArrayList<>();

  @ElementCollection(fetch = FetchType.LAZY)
  @Builder.Default
  private List<String> implicitMods = new ArrayList<>();

  @ElementCollection(fetch = FetchType.LAZY)
  @Builder.Default
  private List<String> explicitMods = new ArrayList<>();

  @ElementCollection(fetch = FetchType.LAZY)
  @Builder.Default
  private List<String> craftedMods = new ArrayList<>();

  @ElementCollection(fetch = FetchType.LAZY)
  @Builder.Default
  private List<String> fracturedMods = new ArrayList<>();

  @ElementCollection(fetch = FetchType.LAZY)
  @Builder.Default
  private List<String> utilityMods = new ArrayList<>();

  @ElementCollection(fetch = FetchType.LAZY)
  @Builder.Default
  private List<String> flavourText = new ArrayList<>();

  @Column(name = "icon")
  private String icon;

  @ManyToOne(fetch = FetchType.LAZY, optional = true)
  private Character character;

  public Item addEnchantMod(ItemEnchantMod itemEnchantMod) {
    this.enchantMods.add(itemEnchantMod);
    itemEnchantMod.setItem(this);

    return this;
  }

}
