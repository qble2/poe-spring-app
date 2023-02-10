package qble2.poe.web.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import qble2.poe.item.ItemDto;
import qble2.poe.item.ItemService;

@Controller
@RequestMapping("/items")
public class ThymeleafItemController {

  @Autowired
  private ItemService itemService;

  @GetMapping(path = "/characters/{characterName}", headers = "HX-Request")
  public String htmxGetCharacterItemsFragment(
      @PathVariable(name = "characterName", required = true) String characterName, Model model) {
    List<ItemDto> items = this.itemService.getCharacterItems(characterName);
    model.addAttribute("items", items);

    return "fragments/items-frags :: items-list";
  }

  @GetMapping(path = "/stashTabs/{stashTabId}", headers = "HX-Request")
  public String htmxGetStashTabItemsFragment(
      @PathVariable(name = "stashTabId", required = true) String stashTabId, Model model) {
    List<ItemDto> items = this.itemService.getStashTabItems(stashTabId);
    model.addAttribute("items", items);

    return "fragments/items-frags :: items-list";
  }

  @GetMapping(path = "/{itemId}", headers = "HX-Request")
  public String htmxGetItemDetailsFragment(
      @PathVariable(name = "itemId", required = true) String itemId, Model model) {
    ItemDto item = this.itemService.getItem(itemId);
    model.addAttribute("item", item);

    return "fragments/items-frags :: item-details";
  }

}
