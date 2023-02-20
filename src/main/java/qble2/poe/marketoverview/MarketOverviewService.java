package qble2.poe.marketoverview;

import java.time.ZonedDateTime;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import qble2.poe.item.Item;
import qble2.poe.item.ItemCategoryResolverService;
import qble2.poe.stash.StashTab;

@Service
@Transactional
public class MarketOverviewService {

  @Autowired
  private PoeNinjaWebClient poeNinjaWebClient;

  @Autowired
  private ItemCategoryResolverService itemCategoryResolverService;

  @Autowired
  private ItemPoeNinjaDetailsIdResolverService itemPoeNinjaDetailsIdResolverService;

  @Autowired
  private MarketOverviewRepository marketOverviewRepository;

  @Autowired
  private MarketOverviewMapper marketOverviewMapper;

  public MarketOverviewsPageDto getMarketOverview(Pageable pageable, String leagueId,
      MarketOverviewTypePoeNinjaEnum type) {
    if (leagueId != null && type != null) {
      return toMarketOverviewsPage(
          this.marketOverviewRepository.findAllByLeagueIdAndType(pageable, leagueId, type));
    }

    if (leagueId != null) {
      return toMarketOverviewsPage(
          this.marketOverviewRepository.findAllByLeagueId(pageable, leagueId));
    }

    if (type != null) {
      return toMarketOverviewsPage(this.marketOverviewRepository.findAllByType(pageable, type));
    }

    return toMarketOverviewsPage(this.marketOverviewRepository.findAll(pageable));
  }

  public MarketOverviewsPageDto reloadMarketOverview(Pageable pageable, String leagueId) {
    MarketOverviewTypePoeNinjaEnum.getValues()
        .forEach(typeEnum -> reloadAndStoreMarketOverviewType(leagueId, typeEnum));

    return toMarketOverviewsPage(
        this.marketOverviewRepository.findAllByLeagueId(pageable, leagueId));
  }

  public void reloadMarketOverviewType(String leagueId, MarketOverviewTypePoeNinjaEnum type) {
    reloadAndStoreMarketOverviewType(leagueId, type);
  }

  public void updateMarketValue(StashTab stashTab) {
    updateMarketValue(stashTab.getItems());
    stashTab.setPriceCheckedAt(ZonedDateTime.now());
  }

  public void updateMarketValue(List<Item> items) {
    items.stream().forEach(item -> {
      itemCategoryResolverService.updateItemCategory(item);
      itemPoeNinjaDetailsIdResolverService.updateItemPoeNinjaDetailsId(item);
      if (item.getPoeNinjaDetailsId() != null) {
        this.marketOverviewRepository.findById(item.getPoeNinjaDetailsId())
            .ifPresent(marketOverview -> item.setChaosValue(marketOverview.getChaosValue()));
      }
    });
  }

  /////
  /////
  /////

  private void reloadAndStoreMarketOverviewType(String leagueId,
      MarketOverviewTypePoeNinjaEnum type) {
    List<MarketOverviewDto> listOfMarketOverviewDto;
    if (MarketOverviewTypePoeNinjaEnum.CURRENCY.equals(type)
        || MarketOverviewTypePoeNinjaEnum.FRAGMENT.equals(type)) {
      listOfMarketOverviewDto = poeNinjaWebClient.getCurrencyOverview(leagueId, type);
    } else {
      listOfMarketOverviewDto = poeNinjaWebClient.getItemOverview(leagueId, type);
    }
    this.marketOverviewRepository
        .saveAll(this.marketOverviewMapper.toEntityListFromDto(listOfMarketOverviewDto));
  }

  private MarketOverviewsPageDto toMarketOverviewsPage(Page<MarketOverview> page) {
    return MarketOverviewsPageDto.builder()
        .marketOverviews(this.marketOverviewMapper.toDtoListFromEntityList(page.getContent()))
        .currentPage(page.getNumber()).totalPages(page.getTotalPages())
        .totalElements(page.getTotalElements()).build();
  }

}
