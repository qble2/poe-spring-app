package qble2.poe.stash;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qble2.poe.exception.StashTabNotFoundException;
import qble2.poe.item.ItemDto;
import qble2.poe.item.ItemMapper;

@Service
@Transactional
public class StashTabService {

  @Autowired
  private StashWebClientGgg stashWebClientGgg;

  @Autowired
  private StashTabRepository stashTabRepository;

  @Autowired
  private StashMapper stashMapper;

  @Autowired
  private ItemMapper itemMapper;

  public List<StashTabDto> getStashTabs(String leagueId) {
    if (leagueId != null) {
      return this.stashMapper.toDtoListFromEntityList(
          this.stashTabRepository.findAllByLeagueIdOrderByLeagueIdAscIndexAsc(leagueId));
    }

    return this.stashMapper
        .toDtoListFromEntityList(this.stashTabRepository.findAllByOrderByLeagueIdAscIndexAsc());
  }

  public List<StashTabDto> reloadStashTabs(String accountName, String poeSessionId,
      String leagueId) {
    List<StashTabDto> listOfStashTabDto =
        this.stashWebClientGgg.retrieveStashTabs(accountName, poeSessionId, leagueId);
    this.stashTabRepository.saveAll(this.stashMapper.toEntityListFromDtoList(listOfStashTabDto));

    return this.stashMapper
        .toDtoListFromEntityList(this.stashTabRepository.findAllByOrderByLeagueIdAscIndexAsc());
  }

  public StashTabDto getDetailedStashTab(String stashTabId) {
    StashTab stashTab = findStashTabByIdOrThrow(stashTabId);

    return this.stashMapper.toDetailedDtoFromEntity(stashTab);
  }

  public StashTabDto reloadDetailedStashTab(String accountName, String poeSessionId,
      String stashTabId) {
    reloadStashTabItems(accountName, poeSessionId, stashTabId);

    return this.stashMapper.toDetailedDtoFromEntity(findStashTabByIdOrThrow(stashTabId));
  }

  public List<ItemDto> getStashTabItems(String stashTabId) {
    StashTab stashTab = findStashTabByIdOrThrow(stashTabId);

    return this.itemMapper.toDtoListFromEntityList(stashTab.getItems());
  }

  public List<ItemDto> reloadStashTabItems(String accountName, String poeSessionId,
      String stashTabId) {
    StashTab stashTab = findStashTabByIdOrThrow(stashTabId);

    List<ItemDto> listOfItemDto = this.stashWebClientGgg.retrieveStashTabItems(accountName,
        poeSessionId, stashTab.getLeagueId(), stashTab.getIndex());

    stashTab.getItems().clear();
    this.itemMapper.toEntityListFromDtoList(listOfItemDto).stream().forEach(stashTab::addItem);
    stashTab = this.stashTabRepository.save(stashTab);

    return this.itemMapper.toDtoListFromEntityList(stashTab.getItems());
  }

  /////
  /////
  /////

  private StashTab findStashTabByIdOrThrow(String stashTabId) {
    return this.stashTabRepository.findById(stashTabId)
        .orElseThrow(() -> new StashTabNotFoundException(stashTabId));
  }

}
