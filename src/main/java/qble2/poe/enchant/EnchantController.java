package qble2.poe.enchant;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/enchants")
public class EnchantController {

  @Autowired
  private EnchantService enchantService;

  @GetMapping
  public List<EnchantDto> getEnchants() {
    return this.enchantService.getEnchants();
  }

}
