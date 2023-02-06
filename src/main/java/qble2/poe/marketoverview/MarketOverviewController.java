package qble2.poe.marketoverview;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/market-overview", produces = MediaType.APPLICATION_JSON_VALUE)
public class MarketOverviewController {

  @Autowired
  private MarketOverviewService marketOverviewService;

  @GetMapping(path = "/leagues/{leagueId}/types/{type}")
  public List<MarketOverviewDto> getMarketOverview(
      @PathVariable(name = "leagueId", required = true) String leagueId,
      @PathVariable(name = "type", required = true) MarketOverviewTypePoeNinjaEnum type) {
    return marketOverviewService.getMarketOverview(leagueId, type);
  }

  // data is too large to be returned (Size: > 15 MB)
  @PostMapping(path = "/leagues/{leagueId}")
  public ResponseEntity<Void> reloadMarketOverview(
      @PathVariable(name = "leagueId", required = true) String leagueId) {
    marketOverviewService.reloadMarketOverview(leagueId);

    return ResponseEntity.ok().build();
  }

  @PostMapping(path = "/leagues/{leagueId}/types/{type}")
  public List<MarketOverviewDto> reloadMarketOverviewType(
      @PathVariable(name = "leagueId", required = true) String leagueId,
      @PathVariable(name = "type", required = true) MarketOverviewTypePoeNinjaEnum type) {
    return marketOverviewService.reloadMarketOverviewType(leagueId, type);
  }

}
