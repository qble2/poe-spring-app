package qble2.poe.stash;

import java.time.ZonedDateTime;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qble2.poe.exception.StashTabNotFoundException;
import qble2.poe.item.ItemDto;
import qble2.poe.item.ItemMapper;
import qble2.poe.marketoverview.MarketOverviewService;

@Service
@Transactional
public class StashTabService {

  @Autowired
  private StashWebClientGgg stashWebClientGgg;

  @Autowired
  private StashTabRepository stashTabRepository;

  @Autowired
  private StashMapper stashMapper;

  // @Autowired
  // private ItemService itemService;

  @Autowired
  private ItemMapper itemMapper;

  @Autowired
  private MarketOverviewService marketOverviewService;

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

    if (leagueId != null) {
      return this.stashMapper.toDtoListFromEntityList(
          this.stashTabRepository.findAllByLeagueIdOrderByLeagueIdAscIndexAsc(leagueId));
    }

    return this.stashMapper
        .toDtoListFromEntityList(this.stashTabRepository.findAllByOrderByLeagueIdAscIndexAsc());
  }

  public StashTabDto getStashTab(String stashTabId) {
    return this.stashMapper.toDtoFromEntity(findStashTabByIdOrThrow(stashTabId));
  }

  public StashTabDto getDetailedStashTab(String stashTabId) {
    return this.stashMapper.toDetailedDtoFromEntity(findStashTabByIdOrThrow(stashTabId));
  }

  public List<ItemDto> getStashTabItems(String stashTabId) {
    // return this.itemService.getStashTabItems(findStashTabByIdOrThrow(stashTabId).getId());
    return this.stashMapper.toDetailedDtoFromEntity(findStashTabByIdOrThrow(stashTabId)).getItems();
  }

  public StashTabDto reloadStashTab(String accountName, String poeSessionId, String stashTabId) {
    StashTab stashTab = updateStashTabItems(accountName, poeSessionId, stashTabId);

    return this.stashMapper.toDetailedDtoFromEntity(stashTab);
  }

  public List<ItemDto> reloadStashTabItems(String accountName, String poeSessionId,
      String stashTabId) {
    StashTab stashTab = updateStashTabItems(accountName, poeSessionId, stashTabId);

    // return this.itemService.getStashTabItems(stashTabId);
    return this.stashMapper.toDetailedDtoFromEntity(stashTab).getItems();
  }

  public StashTabDto priceCheckStashTab(String accountName, String poeSessionId,
      String stashTabId) {
    StashTab stashTab = updateStashTabItemsMarketValue(findStashTabByIdOrThrow(stashTabId));

    return this.stashMapper.toDetailedDtoFromEntity(stashTab);
  }

  /////
  /////
  /////

  private StashTab findStashTabByIdOrThrow(String stashTabId) {
    return this.stashTabRepository.findById(stashTabId)
        .orElseThrow(() -> new StashTabNotFoundException(stashTabId));
  }

  private StashTab updateStashTabItems(String accountName, String poeSessionId, String stashTabId) {
    StashTab stashTab = findStashTabByIdOrThrow(stashTabId);

    List<ItemDto> listOfItemDto = this.stashWebClientGgg.retrieveStashTabItems(accountName,
        poeSessionId, stashTab.getLeagueId(), stashTab.getIndex());
    updateLastReloadedAt(stashTab);

    stashTab.getItems().clear();
    this.itemMapper.toEntityListFromDtoList(listOfItemDto).stream().forEach(stashTab::addItem);

    return this.stashTabRepository.save(stashTab);
  }

  private StashTab updateStashTabItemsMarketValue(StashTab stashTab) {
    marketOverviewService.updateMarketValue(stashTab);

    return this.stashTabRepository.save(stashTab);
  }

  private void updateLastReloadedAt(StashTab stashTab) {
    stashTab.setReloadedAt(ZonedDateTime.now());
    stashTab.setPriceCheckedAt(null);
  }

}
