package qble2.poe.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import qble2.poe.marketoverview.MarketOverviewService;
import qble2.poe.marketoverview.MarketOverviewsPageDto;

@Controller
@RequestMapping(path = "market-overviews")
public class ThymeleafMarketOverviewController {

  @Autowired
  private MarketOverviewService marketOverviewService;

  @InitBinder
  public void initBinder(WebDataBinder binder) {
    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
  }

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

  @PostMapping
  public String reloadMarketOverview(
      @RequestParam(name = "leagueId", required = true) String leagueId,
      @RequestParam(name = "page", required = false, defaultValue = "0") int page,
      @RequestParam(name = "size", required = false, defaultValue = "50") int size, Model model) {
    Pageable pageable = PageRequest.of(page, size);
    MarketOverviewsPageDto marketOverviewsPage =
        this.marketOverviewService.reloadMarketOverview(pageable, leagueId);
    model.addAttribute("marketOverviewsPage", marketOverviewsPage);

    return "market";
  }

  @GetMapping(path = "table", headers = "HX-Request")
  public String getMarketOverviewTableFragment(
      @RequestParam(name = "leagueId", required = false) String leagueId,
      @RequestParam(name = "page", required = false, defaultValue = "0") int page,
      @RequestParam(name = "size", required = false, defaultValue = "50") int size, Model model) {
    Pageable pageable = PageRequest.of(page, size);
    MarketOverviewsPageDto marketOverviewsPage =
        this.marketOverviewService.getMarketOverview(pageable, null, null);
    model.addAttribute("marketOverviewsPage", marketOverviewsPage);

    return "fragments/market-frags :: market-overview-table";
  }

  @PostMapping(path = "table", headers = "HX-Request")
  public String reloadMarketOverviewTableFragment(
      @RequestParam(name = "leagueId", required = true) String leagueId,
      @RequestParam(name = "page", required = false, defaultValue = "0") int page,
      @RequestParam(name = "size", required = false, defaultValue = "50") int size, Model model) {
    Pageable pageable = PageRequest.of(page, size);
    MarketOverviewsPageDto marketOverviewsPage =
        this.marketOverviewService.reloadMarketOverview(pageable, leagueId);
    model.addAttribute("marketOverviewsPage", marketOverviewsPage);

    return "fragments/market-frags :: market-overview-table";
  }

}
