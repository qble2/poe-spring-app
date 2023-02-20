package qble2.poe.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import qble2.poe.marketoverview.MarketOverviewService;
import qble2.poe.marketoverview.MarketOverviewsPageDto;

@Controller
@RequestMapping(path = "market-overviews")
public class ThymeleafMarketOverviewController {

  @Autowired
  private MarketOverviewService marketOverviewService;

  @GetMapping
  public String getMarketOverview(
      @RequestParam(name = "leagueId", required = false) String leagueId,
      @RequestParam(name = "page", required = false, defaultValue = "0") int page,
      @RequestParam(name = "size", required = false, defaultValue = "50") int size, Model model) {

    Pageable pageable = PageRequest.of(page, size);
    MarketOverviewsPageDto marketOverviewsPage =
        this.marketOverviewService.getMarketOverview(pageable, null, null);
    model.addAttribute("marketOverviewsPage", marketOverviewsPage);

    return "market";
  }

}
