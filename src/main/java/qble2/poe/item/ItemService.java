package qble2.poe.item;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qble2.poe.exception.ItemNotFoundException;

@Service
@Transactional
public class ItemService {

  @Autowired
  private ItemRepository itemRepository;

  @Autowired
  private ItemMapper itemMapper;

  public ItemDto getItem(String itemId) {
    return this.itemMapper.toDtoFromEntity(findItemByIdOrThrow(itemId));
  }

  public List<ItemDto> getCharacterItems(String characterName) {
    return this.itemMapper
        .toDtoListFromEntityList(this.itemRepository.findAllByCharacter_name(characterName));
  }

  public List<ItemDto> getStashTabItems(String stashTabId) {
    return this.itemMapper.toDtoListFromEntityList(
        this.itemRepository.findAllByStashTab_idOrderByChaosValueDesc(stashTabId));
  }

  /////
  /////
  /////

  private Item findItemByIdOrThrow(String itemId) {
    return this.itemRepository.findById(itemId)
        .orElseThrow(() -> new ItemNotFoundException(itemId));
  }

}
