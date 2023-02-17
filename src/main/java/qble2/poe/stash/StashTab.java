package qble2.poe.stash;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import qble2.poe.item.Item;

@Entity(name = "StashTab")
@Table(name = "StashTab")
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Accessors(chain = true, fluent = false)
public class StashTab {

  @Id
  @Column(name = "id", updatable = false, nullable = false)
  @EqualsAndHashCode.Include
  private String id;

  @Column(name = "index", nullable = false)
  private int index;

  @Column(name = "name")
  private String name;

  @Column(name = "type")
  private String type;

  @Column(name = "color")
  private int color;

  @Column(name = "leagueId", nullable = false)
  private String leagueId;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY,
      mappedBy = "stashTab")
  @OrderBy(value = "chaosValue DESC")
  private List<Item> items = new ArrayList<>();

  public void addItem(Item item) {
    this.getItems().add(item);
    item.setStashTab(this);
  }

  //
  @Column(name = "reloadedAt", nullable = true)
  private ZonedDateTime reloadedAt;

  @Column(name = "priceCheckedAt", nullable = true)
  private ZonedDateTime priceCheckedAt;

}
