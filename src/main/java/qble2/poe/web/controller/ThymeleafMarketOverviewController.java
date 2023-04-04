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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import qble2.poe.marketoverview.MarketOverviewPageDto;
import qble2.poe.marketoverview.MarketOverviewService;
import qble2.poe.marketoverview.MarketOverviewTypePoeNinjaEnum;

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
  public String getMarketOverviewPage(
      @ModelAttribute("marketOverviewPage") MarketOverviewPageDto marketOverviewPage, Model model) {

    return "market";
  }

  @GetMapping(path = "table", headers = "HX-Request")
  public String getMarketOverviewTableFragment(
      @RequestParam(name = "leagueId", required = true) String leagueId,
      @RequestParam(name = "type", required = false) MarketOverviewTypePoeNinjaEnum type,
      @RequestParam(name = "page", required = false, defaultValue = "0") int page,
      @RequestParam(name = "size", required = false, defaultValue = "50") int size, Model model) {
    Pageable pageable = PageRequest.of(page, size);
    MarketOverviewPageDto marketOverviewPage =
        this.marketOverviewService.getMarketOverview(pageable, leagueId, type);
    model.addAttribute("marketOverviewPage", marketOverviewPage);

    return "fragments/market-frags :: market-overviews-table";
  }

  @PostMapping(path = "table", headers = "HX-Request")
  public String reloadMarketOverviewTableFragment(
      @RequestParam(name = "leagueId", required = true) String leagueId,
      @RequestParam(name = "type", required = false) MarketOverviewTypePoeNinjaEnum type,
      @RequestParam(name = "page", required = false, defaultValue = "0") int page,
      @RequestParam(name = "size", required = false, defaultValue = "50") int size, Model model) {
    Pageable pageable = PageRequest.of(page, size);
    MarketOverviewPageDto marketOverviewPage =
        this.marketOverviewService.reloadMarketOverview(pageable, leagueId, type);
    model.addAttribute("marketOverviewPage", marketOverviewPage);

    return "fragments/market-frags :: market-overviews-table";
  }

}
