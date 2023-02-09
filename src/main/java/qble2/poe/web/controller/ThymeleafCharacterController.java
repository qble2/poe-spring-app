package qble2.poe.web.controller;

import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import qble2.poe.character.CharacterDto;
import qble2.poe.character.CharacterService;
import qble2.poe.item.ItemDto;

@Controller
@RequestMapping(path = "characters")
public class ThymeleafCharacterController {

  @Autowired
  private CharacterService characterService;

  @InitBinder
  public void initBinder(WebDataBinder binder) {
    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
  }

  @GetMapping
  public String getCharacters(Model model, HttpSession session) {
    String accountName = (String) session.getAttribute("accountName");

    List<CharacterDto> characters = this.characterService.getCharacters(accountName, null);
    model.addAttribute("characters", characters);

    return "profile";
  }

  @PostMapping
  public String reloadCharacters(@RequestParam(name = "leagueId", required = false) String leagueId,
      Model model, HttpSession session) {
    String accountName = (String) session.getAttribute("accountName");

    List<CharacterDto> characters = this.characterService.reloadCharacters(accountName, leagueId);
    model.addAttribute("characters", characters);

    return "profile";
  }

  @GetMapping(path = "/{characterName}")
  public String getCharacter(@ModelAttribute(value = "characterName") String characterName,
      Model model, HttpSession session) {
    CharacterDto character = this.characterService.getDetailedCharacter(characterName);
    model.addAttribute("character", character);

    return "character";
  }

  @GetMapping(path = "get-characters-list", headers = "HX-Request")
  public String htmxGetCharactersListFragment(
      @RequestParam(name = "leagueId", required = false) String leagueId, Model model,
      HttpSession session) {
    String accountName = (String) session.getAttribute("accountName");

    List<CharacterDto> characters = this.characterService.getCharacters(accountName, leagueId);
    model.addAttribute("characters", characters);

    return "fragments/characters-frags :: characters-list";
  }

  @PostMapping(path = "reload-characters-list", headers = "HX-Request")
  public String htmxReloadCharactersListFragment(
      @RequestParam(name = "leagueId", required = false) String leagueId, Model model,
      HttpSession session) {
    String accountName = (String) session.getAttribute("accountName");

    List<CharacterDto> characters = this.characterService.reloadCharacters(accountName, leagueId);
    model.addAttribute("characters", characters);

    return "fragments/characters-frags :: characters-list";
  }

  @GetMapping(path = "/{characterName}/get-inventory", headers = "HX-Request")
  public String htmxGetCharacterInventoryFragment(
      @PathVariable(name = "characterName", required = true) String characterName, Model model,
      HttpSession session) {
    List<ItemDto> items = this.characterService.getCharacterItems(characterName);
    model.addAttribute("items", items);
    model.addAttribute("characterName", characterName);

    return "fragments/characters-frags :: character-inventory";
  }

  @PostMapping(path = "/{characterName}/reload-inventory", headers = "HX-Request")
  public String htmxReloadCharacterInventoryFragment(
      @PathVariable(name = "characterName", required = true) String characterName, Model model,
      HttpSession session) {
    String accountName = (String) session.getAttribute("accountName");

    List<ItemDto> items = this.characterService.reloadCharacterItems(accountName, characterName);
    model.addAttribute("items", items);
    model.addAttribute("characterName", characterName);

    return "fragments/characters-frags :: character-inventory";
  }

}
