package qble2.poe.marketoverview;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarketOverviewRepository extends JpaRepository<MarketOverview, MarketOverviewId> {

  Page<MarketOverview> findAllByLeagueIdAndType(String leagueId,
      MarketOverviewTypePoeNinjaEnum type, Pageable pageable);

  Page<MarketOverview> findAllByLeagueId(String leagueId, Pageable pageable);

}
