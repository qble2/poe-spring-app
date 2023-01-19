package qble2.poe.enchant;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import qble2.poe.item.ItemDto;

@RestController
@RequestMapping(path = "api/enchants")
public class EnchantController {

  @Autowired
  private EnchantService enchantService;

  @GetMapping
  public List<EnchantDto> getEnchants() {
    return this.enchantService.getEnchants();
  }

  @GetMapping(path = "/{enchantId}/items")
  public List<ItemDto> getItemsEnchantedwith(
      @PathVariable(name = "enchantId", required = true) String enchantId,
      @RequestParam(name = "leagueId", required = true) String leagueId) {
    return this.enchantService.getItemsEnchantedwith(enchantId, leagueId);
  }

  @GetMapping(path = "/{enchantId}/itemBases")
  public List<EnchantedItemBaseWithOccurrenceProjectionDto> getEnchantItemBasesWithOccurrence(
      @PathVariable(name = "enchantId", required = true) String enchantId,
      @RequestParam(name = "leagueId", required = true) String leagueId) {
    return this.enchantService.getEnchantItemBaseseWithOccurrenceByProjection(enchantId, leagueId);
  }

}
