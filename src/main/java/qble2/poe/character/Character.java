package qble2.poe.character;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import qble2.poe.item.Item;

@Entity(name = "Character")
@Table(name = "Character")
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Accessors(chain = true, fluent = false)
public class Character {

  @Id
  @Column(name = "name", updatable = false, nullable = false)
  @EqualsAndHashCode.Include
  private String name;

  @Column(name = "accountName", nullable = false)
  private String accountName;

  @Column(name = "leagueId", nullable = false)
  private String leagueId;

  @Column(name = "classId")
  private int classId;

  @Column(name = "ascendancyClass")
  private int ascendancyClass;

  @Column(name = "characterClass")
  private String characterClass;

  @Column(name = "level")
  private int level;

  @Column(name = "experience")
  private double experience;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY,
      mappedBy = "character")
  @Builder.Default
  private List<Item> items = new ArrayList<>();

  public void addItem(Item item) {
    this.items.add(item);
    item.setCharacter(this);
  }

}
