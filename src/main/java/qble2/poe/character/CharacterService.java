package qble2.poe.character;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qble2.poe.enchant.EnchantRepository;
import qble2.poe.exception.CharacterNotFoundException;
import qble2.poe.item.ItemDto;
import qble2.poe.item.ItemMapper;

@Service
@Transactional
public class CharacterService {

  @Autowired
  private CharacterRepository characterRepository;

  @Autowired
  private CharacterWebClientGgg characterWebClientGgg;

  @Autowired
  private CharacterMapper characterMapper;

  @Autowired
  private ItemMapper itemMapper;

  @Autowired
  private EnchantRepository enchantRepository;

  // TODO BKE Spring Data specifications
  public List<CharacterDto> getCharacters(String leagueId, String accountName) {
    if (accountName != null) {
      return this.characterMapper.toDtoListFromEntityList(
          this.characterRepository.findAllByLeagueIdAndAccountName(leagueId, accountName));
    }

    return this.characterMapper
        .toDtoListFromEntityList(this.characterRepository.findAllByLeagueId(leagueId));
  }

  public CharacterDto getCharacter(String characterName) {
    Character character = findCharacterByIdOrThrow(characterName);

    return this.characterMapper.toDtoFromEntity(character);
  }

  public List<CharacterDto> reloadAccountCharacters(String accountName) {
    List<CharacterDto> listOfCharacterDto =
        this.characterWebClientGgg.retrieveAccountCharacters(accountName);
    this.characterRepository
        .saveAll(this.characterMapper.toEntityListFromDtoList(listOfCharacterDto));

    return this.characterMapper.toDtoListFromEntityList(this.characterRepository.findAll());
  }

  /////
  ///// character . items
  /////

  public List<ItemDto> getCharacterItems(String characterName) {
    Character character = findCharacterByIdOrThrow(characterName);

    return this.itemMapper.toDtoListFromEntityList(character.getItems());
  }

  public List<ItemDto> reloadCharacterItems(String accountName, String characterName) {
    updateCharacterItems(accountName, characterName);

    return this.itemMapper.toDtoListFromEntityList(this.characterRepository.findById(characterName)
        .orElseThrow(() -> new CharacterNotFoundException(characterName)).getItems());
  }

  public Character updateCharacterItems(String accountName, String characterName) {
    Character character = findCharacterByIdOrThrow(characterName);

    List<ItemDto> listOfItemDto =
        this.characterWebClientGgg.retrieveCharacterItems(accountName, characterName);

    character.getItems().clear();
    this.itemMapper.toEntityListFromDtoList(listOfItemDto, enchantRepository).stream()
        .forEach(character::addItem);
    this.characterRepository.save(character);

    return character;
  }

  /////
  /////
  /////

  private Character findCharacterByIdOrThrow(String characterName) {
    return this.characterRepository.findById(characterName)
        .orElseThrow(() -> new CharacterNotFoundException(characterName));
  }

}
