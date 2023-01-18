package qble2.poe.item;

import java.io.Serializable;
import lombok.Data;
import lombok.experimental.Accessors;
import qble2.poe.enchant.Enchant;

@Data
@Accessors(chain = true, fluent = false)
public class ItemEnchantModId implements Serializable {

  private static final long serialVersionUID = 1L;

  private Item item;

  private Enchant enchant;

}
