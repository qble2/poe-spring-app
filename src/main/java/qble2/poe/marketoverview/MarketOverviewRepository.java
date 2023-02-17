package qble2.poe.marketoverview;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MarketOverviewRepository extends JpaRepository<MarketOverview, String> {

  @Query("SELECT COUNT(*) FROM MarketOverview")
  int tableCount();

  List<MarketOverview> findAllByLeagueIdAndType(String leagueId,
      MarketOverviewTypePoeNinjaEnum type);

}
