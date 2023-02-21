package qble2.poe.character;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import qble2.poe.item.ItemDto;

@RestController
@RequestMapping(path = "api/characters", produces = MediaType.APPLICATION_JSON_VALUE)
public class CharacterController {

  @Autowired
  private CharacterService characterService;

  @GetMapping
  public List<CharacterDto> getCharacters(
      @RequestParam(name = "leagueId", required = false) String leagueId,
      @RequestParam(name = "accountName", required = false) String accountName) {
    return this.characterService.getCharacters(accountName, leagueId);
  }

  @PostMapping
  public List<CharacterDto> reloadCharacters(
      @RequestParam(name = "accountName", required = true) String accountName,
      @RequestParam(name = "leagueId", required = false) String leagueId) {
    return this.characterService.reloadCharacters(accountName, leagueId);
  }

  @GetMapping(path = "/{characterName}")
  public CharacterDto getCharacter(
      @PathVariable(name = "characterName", required = true) String characterName) {
    return this.characterService.getCharacter(characterName);
  }

  @GetMapping(path = "/{characterName}/items")
  public List<ItemDto> getCharacterItems(
      @PathVariable(name = "characterName", required = true) String characterName) {
    return this.characterService.getCharacterItems(characterName);
  }

  @PostMapping(path = "/{characterName}/items")
  public List<ItemDto> reloadCharacterItems(
      @PathVariable(name = "characterName", required = true) String characterName,
      @RequestParam(name = "accountName", required = true) String accountName) {
    return this.characterService.reloadCharacterItems(accountName, characterName);
  }

}
