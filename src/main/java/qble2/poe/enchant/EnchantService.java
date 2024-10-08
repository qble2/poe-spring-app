package qble2.poe.enchant;

import java.util.List;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qble2.poe.item.Item;
import qble2.poe.item.ItemDto;
import qble2.poe.item.ItemMapper;
import qble2.poe.item.ItemRepository;

@Service
@Transactional
// @AllArgsConstructor needed to be able to inject mocked dependencies for unit testing
@AllArgsConstructor
public class EnchantService {

  @Autowired
  private EnchantRepository enchantRepository;

  @Autowired
  private EnchantMapper enchantMapper;

  @Autowired
  private ItemRepository itemRepository;

  @Autowired
  private ItemMapper itemMapper;

  public List<EnchantDto> getEnchants() {
    return this.enchantMapper.toDtoListFromEntityList(this.enchantRepository.findAll());
  }

  public List<ItemDto> getItemsEnchantedwith(String enchantId, String leagueId) {
    List<Item> items = this.itemRepository.findByEnchantModsAndLeagueId(enchantId, leagueId);

    return this.itemMapper.toDtoListFromEntityList(items);
  }

  public List<EnchantedItemBaseWithOccurrenceProjectionDto> getEnchantItemBaseseWithOccurrenceByProjection(
      String enchantId, String leagueId) {
    return this.itemRepository.findEnchantItemBasesWithOccurrenceByProjection(enchantId, leagueId);
  }

}
