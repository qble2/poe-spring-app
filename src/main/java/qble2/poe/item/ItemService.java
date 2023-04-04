package qble2.poe.item;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import qble2.poe.character.Character;
import qble2.poe.character.CharacterWebClientGgg;
import qble2.poe.exception.ItemNotFoundException;

@Service
@Transactional
// @AllArgsConstructor needed to be able to inject mocked dependencies for unit testing
@AllArgsConstructor
public class ItemService {

  @Autowired
  private ItemRepository itemRepository;

  @Autowired
  private ItemMapper itemMapper;

  @Autowired
  private CharacterWebClientGgg characterWebClientGgg;

  public ItemDto getItem(String itemId) {
    return this.itemMapper.toDtoFromEntity(findItemByIdOrThrow(itemId));
  }

  public List<ItemDto> getCharacterItemsDto(Character character) {
    return this.itemMapper.toDtoListFromEntityList(getCharacterItems(character));
  }

  public List<Item> getCharacterItems(Character character) {
    return this.itemRepository.findAllByCharacter_nameOrderByChaosValueDesc(character.getName());
  }

  public List<Item> reloadCharacterItems(Character character) {
    return this.itemMapper.toEntityListFromDtoList(reloadCharacterItemsDto(character));
  }

  public List<ItemDto> reloadCharacterItemsDto(Character character) {
    return this.characterWebClientGgg.retrieveCharacterItems(character.getAccountName(),
        character.getName());
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
