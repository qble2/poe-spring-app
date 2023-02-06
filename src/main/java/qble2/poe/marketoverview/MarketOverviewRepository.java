package qble2.poe.marketoverview;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarketOverviewRepository extends JpaRepository<MarketOverview, String> {

  List<MarketOverview> findAllByLeagueIdAndType(String leagueId,
      MarketOverviewTypePoeNinjaEnum type);

}
