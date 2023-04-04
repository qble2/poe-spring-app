package qble2.poe.marketoverview;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/market-overviews", produces = MediaType.APPLICATION_JSON_VALUE)
public class MarketOverviewController {

  @Autowired
  private MarketOverviewService marketOverviewService;

  @GetMapping(path = "/leagues/{leagueId}")
  public MarketOverviewPageDto getMarketOverview(
      @PathVariable(name = "leagueId", required = true) String leagueId,
      @RequestParam(name = "type", required = false) MarketOverviewTypePoeNinjaEnum type,
      @RequestParam(name = "page", required = false, defaultValue = "0") int page,
      @RequestParam(name = "size", required = false, defaultValue = "50") int size) {
    Pageable pageable = PageRequest.of(page, size);

    return marketOverviewService.getMarketOverview(pageable, leagueId, type);
  }

  @PostMapping(path = "/leagues/{leagueId}")
  public MarketOverviewPageDto reloadMarketOverview(
      @PathVariable(name = "leagueId", required = true) String leagueId,
      @RequestParam(name = "type", required = false) MarketOverviewTypePoeNinjaEnum type,
      @RequestParam(name = "page", required = false, defaultValue = "0") int page,
      @RequestParam(name = "size", required = false, defaultValue = "50") int size) {
    Pageable pageable = PageRequest.of(page, size);

    return marketOverviewService.reloadMarketOverview(pageable, leagueId, type);
  }

}
