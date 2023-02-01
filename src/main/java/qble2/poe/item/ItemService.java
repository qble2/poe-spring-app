package qble2.poe.item;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ItemService {

  @Autowired
  private ItemRepository itemRepository;

  @Autowired
  private ItemMapper itemMapper;

  public List<ItemDto> getCharacterItems(String characterName) {
    return this.itemMapper
        .toDtoListFromEntityList(this.itemRepository.findAllByCharacter_name(characterName));
  }

  public List<ItemDto> getStashTabItems(String stashTabId) {
    return this.itemMapper
        .toDtoListFromEntityList(this.itemRepository.findAllByStashTab_id(stashTabId));
  }

}
