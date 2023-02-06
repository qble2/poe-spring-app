package qble2.poe.marketoverview;

import java.util.List;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MarketOverviewService {

  @Autowired
  private PoeNinjaWebClient poeNinjaWebClient;

  @Autowired
  private MarketOverviewRepository marketOverviewRepository;

  @Autowired
  private MarketOverviewMapper marketOverviewMapper;

  public List<MarketOverviewDto> getMarketOverview(String leagueId,
      MarketOverviewTypePoeNinjaEnum type) {
    List<MarketOverview> listOfMarketOverview =
        this.marketOverviewRepository.findAllByLeagueIdAndType(leagueId, type);

    return this.marketOverviewMapper.toDtoListFromEntityList(listOfMarketOverview);
  }

  // data is too large to be returned
  public void reloadMarketOverview(String leagueId) {
    Stream.of(MarketOverviewTypePoeNinjaEnum.values())
        .forEach(typeEnum -> reloadAndStoreMarketOverviewType(leagueId, typeEnum));
  }

  public List<MarketOverviewDto> reloadMarketOverviewType(String leagueId,
      MarketOverviewTypePoeNinjaEnum type) {
    reloadAndStoreMarketOverviewType(leagueId, type);

    return this.marketOverviewMapper.toDtoListFromEntityList(
        this.marketOverviewRepository.findAllByLeagueIdAndType(leagueId, type));
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

}
