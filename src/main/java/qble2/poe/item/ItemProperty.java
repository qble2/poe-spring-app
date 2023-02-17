package qble2.poe.item;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Entity(name = "ItemProperty")
@Table(name = "ItemProperty")
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Accessors(chain = true, fluent = false)
public class ItemProperty {

  @Id
  @GeneratedValue
  @Column(name = "id", nullable = false, updatable = false)
  @EqualsAndHashCode.Include
  private Long id;

  @Column(name = "name", nullable = false, updatable = false)
  private String name;

  // XXX BKE "value" is an sql reserved keyword, it will work with postresql but will fail with h2
  @Column(name = "valuex", nullable = true, updatable = false)
  private String value;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "itemId", nullable = false)
  private Item item;

}
