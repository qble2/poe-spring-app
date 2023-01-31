package qble2.poe.character;

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

@Controller
@RequestMapping(path = "characters")
public class ThymeleafCharacterController {

  @Autowired
  private CharacterService characterService;

  @GetMapping
  public String getCharacters(Model model, HttpSession session) {
    String accountName = (String) session.getAttribute("accountName");
    String leagueId = (String) session.getAttribute("leagueId");

    List<CharacterDto> characters = this.characterService.getCharacters(leagueId, accountName);
    model.addAttribute("characters", characters);

    return "character-list";
  }

  @PostMapping
  public String reloadCharacters(Model model, HttpSession session) {
    String accountName = (String) session.getAttribute("accountName");

    List<CharacterDto> characters = this.characterService.reloadCharacters(accountName);
    model.addAttribute("characters", characters);

    return "character-list";
  }

  @GetMapping(path = "/{characterName}")
  public String getCharacter(@ModelAttribute(value = "characterName") String characterName,
      Model model, HttpSession session) {
    CharacterDto character = this.characterService.getDetailedCharacter(characterName);
    model.addAttribute("character", character);

    return "character";
  }

  @PostMapping(path = "/{characterName}/items")
  public String reloadCharacterItems(
      @PathVariable(name = "characterName", required = true) String characterName, Model model,
      HttpSession session) {
    String accountName = (String) session.getAttribute("accountName");

    CharacterDto character =
        this.characterService.reloadDetailedCharacter(accountName, characterName);
    model.addAttribute("character", character);

    return "character";
  }

  /////
  /////
  /////

}
