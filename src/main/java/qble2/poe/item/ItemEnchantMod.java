package qble2.poe.item;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import qble2.poe.enchant.Enchant;

@Entity(name = "ItemEnchantMod")
@Table(name = "ItemEnchantMod")
@IdClass(ItemEnchantModId.class)
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Accessors(chain = true, fluent = false)
public class ItemEnchantMod {

  @Id
  @ManyToOne
  @JoinColumn(name = "itemId", nullable = false)
  @EqualsAndHashCode.Include
  private Item item;

  @Id
  @ManyToOne
  @JoinColumn(name = "enchantId", nullable = false)
  @EqualsAndHashCode.Include
  private Enchant enchant;

  // "value" is an sql reserved keyword, it will work with postresql but will fail with h2
  @Column(name = "enchantValue", nullable = true)
  private Integer enchantValue;

  public ItemEnchantMod(Item item, Enchant enchant, Integer enchantValue) {
    this.item = item;
    this.enchant = enchant;
    this.enchantValue = enchantValue;

  }

}
