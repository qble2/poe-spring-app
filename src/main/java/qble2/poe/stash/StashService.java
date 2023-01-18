package qble2.poe.stash;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qble2.poe.enchant.EnchantRepository;
import qble2.poe.item.ItemDto;
import qble2.poe.item.ItemMapper;
import qble2.poe.item.ItemRepository;

@Service
@Transactional
public class StashService {

  // TODO BKE config
  private static final String POE_SESSION_ID = "06f836f7d8f5e2b852ca590ee68d3088";

  @Autowired
  private StashWebClientGgg stashWebClientGgg;

  @Autowired
  private ItemRepository itemRepository;

  @Autowired
  private ItemMapper itemMapper;

  @Autowired
  private EnchantRepository enchantRepository;

  public List<ItemDto> getStashTabItems(String accountName, String leagueId, int tabIndex,
      boolean isRetrieveTabHeaders) {
    return this.itemMapper.toDtoListFromEntityList(this.itemRepository.findAll());
  }

  public List<ItemDto> reloadStashTabItems(String accountName, String leagueId, int tabIndex,
      boolean isRetrieveTabHeaders) {
    List<ItemDto> listOfItemDto = this.stashWebClientGgg.retrieveStashItems(POE_SESSION_ID,
        accountName, leagueId, tabIndex, isRetrieveTabHeaders);
    this.itemRepository
        .saveAll(this.itemMapper.toEntityListFromDtoList(listOfItemDto, enchantRepository));

    return this.itemMapper.toDtoListFromEntityList(this.itemRepository.findAll());
  }

}
