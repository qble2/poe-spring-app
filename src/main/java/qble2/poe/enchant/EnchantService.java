package qble2.poe.enchant;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class EnchantService {

  @Autowired
  private EnchantRepository enchantRepository;

  @Autowired
  private EnchantMapper enchantMapper;

  public List<EnchantDto> getEnchants() {
    return this.enchantMapper.toDtoListFromEntityList(this.enchantRepository.findAll());
  }

}
