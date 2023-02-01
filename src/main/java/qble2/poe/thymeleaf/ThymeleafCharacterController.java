package qble2.poe.thymeleaf;

import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import qble2.poe.character.CharacterDto;
import qble2.poe.character.CharacterService;
import qble2.poe.item.ItemDto;

@Controller
@RequestMapping(path = "characters")
public class ThymeleafCharacterController {

  @Autowired
  private CharacterService characterService;

  @GetMapping
  public String getCharacters(Model model, HttpSession session) {
    String accountName = (String) session.getAttribute("accountName");
    String leagueId = (String) session.getAttribute("leagueId");

    List<CharacterDto> characters = this.characterService.getCharacters(accountName, leagueId);
    model.addAttribute("characters", characters);

    return "character-list";
  }

  @GetMapping(headers = "HX-Request")
  public String getCharactersFragment(Model model, HttpSession session) {
    String accountName = (String) session.getAttribute("accountName");
    String leagueId = (String) session.getAttribute("leagueId");

    List<CharacterDto> characters = this.characterService.getCharacters(accountName, leagueId);
    model.addAttribute("characters", characters);

    return "fragments/character-list";
  }

  @PostMapping(headers = "HX-Request")
  public String reloadCharactersFragment(Model model, HttpSession session) {
    String accountName = (String) session.getAttribute("accountName");

    List<CharacterDto> characters = this.characterService.reloadCharacters(accountName);
    model.addAttribute("characters", characters);

    return "fragments/character-list";
  }

  @GetMapping(path = "/{characterName}")
  public String getCharacter(@ModelAttribute(value = "characterName") String characterName,
      Model model, HttpSession session) {
    CharacterDto character = this.characterService.getDetailedCharacter(characterName);
    model.addAttribute("character", character);

    return "character";
  }

  @PostMapping(path = "/{characterName}/items", headers = "HX-Request")
  public String reloadCharacterItemsFragment(
      @PathVariable(name = "characterName", required = true) String characterName, Model model,
      HttpSession session) {
    String accountName = (String) session.getAttribute("accountName");

    List<ItemDto> items = this.characterService.reloadCharacterItems(accountName, characterName);
    model.addAttribute("items", items);

    return "fragments/item-list";
  }

}
