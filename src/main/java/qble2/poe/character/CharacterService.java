package qble2.poe.character;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

  public List<CharacterDto> getCharacters(String accountName, String leagueId) {
    if (accountName != null && leagueId != null) {
      return this.characterMapper.toDtoListFromEntityList(this.characterRepository
          .findAllByAccountNameAndLeagueIdOrderByLeagueIdAscLevelDescNameAsc(accountName,
              leagueId));
    }

    if (accountName != null) {
      return this.characterMapper.toDtoListFromEntityList(this.characterRepository
          .findAllByAccountNameOrderByLeagueIdAscLevelDescNameAsc(accountName));
    }

    if (leagueId != null) {
      return this.characterMapper.toDtoListFromEntityList(
          this.characterRepository.findAllByLeagueIdOrderByLeagueIdAscLevelDescNameAsc(leagueId));
    }

    return this.characterMapper.toDtoListFromEntityList(
        this.characterRepository.findAllByOrderByLeagueIdAscLevelDescNameAsc());
  }

  public CharacterDto getCharacter(String characterName) {
    Character character = findCharacterByIdOrThrow(characterName);

    return this.characterMapper.toDtoFromEntity(character);
  }

  public CharacterDto getDetailedCharacter(String characterName) {
    Character character = findCharacterByIdOrThrow(characterName);

    return this.characterMapper.toDetailedDtoFromEntity(character);
  }

  public List<CharacterDto> reloadCharacters(String accountName, String leagueId) {
    List<CharacterDto> listOfCharacterDto =
        this.characterWebClientGgg.retrieveAccountCharacters(accountName);
    this.characterRepository
        .saveAll(this.characterMapper.toEntityListFromDtoList(listOfCharacterDto));

    if (leagueId != null) {
      return this.characterMapper.toDtoListFromEntityList(this.characterRepository
          .findAllByAccountNameAndLeagueIdOrderByLeagueIdAscLevelDescNameAsc(accountName,
              leagueId));
    }

    return this.characterMapper.toDtoListFromEntityList(this.characterRepository
        .findAllByAccountNameOrderByLeagueIdAscLevelDescNameAsc(accountName));
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

    return this.itemMapper
        .toDtoListFromEntityList(findCharacterByIdOrThrow(characterName).getItems());
  }

  public CharacterDto reloadDetailedCharacter(String accountName, String characterName) {
    Character character = updateCharacterItems(accountName, characterName);

    return this.characterMapper.toDetailedDtoFromEntity(character);
  }

  public Character updateCharacterItems(String accountName, String characterName) {
    Character character = findCharacterByIdOrThrow(characterName);

    List<ItemDto> listOfItemDto =
        this.characterWebClientGgg.retrieveCharacterItems(accountName, characterName);

    character.getItems().clear();
    this.itemMapper.toEntityListFromDtoList(listOfItemDto).stream().forEach(character::addItem);
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
