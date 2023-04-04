package qble2.poe.marketoverview;

import java.time.ZonedDateTime;
import java.util.List;
import javax.annotation.Nullable;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import qble2.poe.item.Item;
import qble2.poe.item.ItemCategoryResolverService;
import qble2.poe.league.LeagueService;
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

  @Autowired
  private LeagueService leagueService;

  public MarketOverviewPageDto getMarketOverview(@NotNull String leagueId,
      @Nullable MarketOverviewTypePoeNinjaEnum type, Pageable pageable) {
    leagueService.findLeagueByIdOrThrow(leagueId);

    if (type != null) {
      return toMarketOverviewsPage(
          this.marketOverviewRepository.findAllByLeagueIdAndType(leagueId, type, pageable));
    }

    return toMarketOverviewsPage(
        this.marketOverviewRepository.findAllByLeagueId(leagueId, pageable));
  }

  public MarketOverviewPageDto reloadMarketOverview(String leagueId,
      MarketOverviewTypePoeNinjaEnum type, Pageable pageable) {
    leagueService.findLeagueByIdOrThrow(leagueId);

    if (type != null) {
      reloadAndStoreMarketOverviewType(leagueId, type);

      return toMarketOverviewsPage(
          this.marketOverviewRepository.findAllByLeagueIdAndType(leagueId, type, pageable));
    }

    MarketOverviewTypePoeNinjaEnum.getValues()
        .forEach(typeEnum -> reloadAndStoreMarketOverviewType(leagueId, typeEnum));

    return toMarketOverviewsPage(
        this.marketOverviewRepository.findAllByLeagueId(leagueId, pageable));
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
        this.marketOverviewRepository
            .findById(new MarketOverviewId(item.getPoeNinjaDetailsId(), item.getLeagueId()))
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

    List<MarketOverview> listOfMarketOverview =
        this.marketOverviewMapper.toEntityListFromDto(listOfMarketOverviewDto);
    this.marketOverviewRepository.saveAll(listOfMarketOverview);
  }

  private MarketOverviewPageDto toMarketOverviewsPage(Page<MarketOverview> page) {
    return MarketOverviewPageDto.builder()
        .marketOverviews(this.marketOverviewMapper.toDtoListFromEntityList(page.getContent()))
        .currentPage(page.getNumber()).totalPages(page.getTotalPages())
        .totalElements(page.getTotalElements()).build();
  }

}
