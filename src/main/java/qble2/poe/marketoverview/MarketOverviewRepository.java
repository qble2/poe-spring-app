package qble2.poe.marketoverview;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MarketOverviewRepository extends JpaRepository<MarketOverview, String> {

  @Query("SELECT COUNT(*) FROM MarketOverview")
  int tableCount();

  Page<MarketOverview> findAllByLeagueIdAndType(Pageable pageable, String leagueId,
      MarketOverviewTypePoeNinjaEnum type);

  Page<MarketOverview> findAllByLeagueId(Pageable pageable, String leagueId);

  Page<MarketOverview> findAllByType(Pageable pageable, MarketOverviewTypePoeNinjaEnum type);

}
