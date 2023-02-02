package qble2.poe.web.controller;

import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import qble2.poe.item.ItemDto;
import qble2.poe.stash.StashTabDto;
import qble2.poe.stash.StashTabService;

@Controller
@RequestMapping(path = "stash-tabs")
public class ThymeleafStashTabController {

  @Autowired
  private StashTabService stashTabService;

  @GetMapping
  public String getStashTabsPage(Model model, HttpSession session) {
    return "stash-tabs";
  }

  @GetMapping(headers = "HX-Request")
  public String getStashTabsListFragment(
      @RequestParam(name = "leagueId", required = false) String leagueId, Model model,
      HttpSession session) {
    List<StashTabDto> stashTabs = this.stashTabService.getStashTabs(leagueId);
    model.addAttribute("stashTabs", stashTabs);

    return "fragments/stash-tab-list";
  }

  @PostMapping(headers = "HX-Request")
  public String reloadStashTabsListFragment(
      @RequestParam(name = "leagueId", required = true) String leagueId, Model model,
      HttpSession session) {
    String accountName = (String) session.getAttribute("accountName");
    String poeSessionId = (String) session.getAttribute("poeSessionId");

    List<StashTabDto> stashTabs =
        this.stashTabService.reloadStashTabs(accountName, poeSessionId, leagueId);
    model.addAttribute("stashTabs", stashTabs);

    return "fragments/stash-tab-list";
  }

  @GetMapping(path = "/{stashTabId}")
  public String getStashTabPage(
      @PathVariable(name = "stashTabId", required = true) String stashTabId, Model model,
      HttpSession session) {
    StashTabDto stashTab = this.stashTabService.getStashTab(stashTabId);
    model.addAttribute("stashTab", stashTab);

    return "stash-tab";
  }

  @PostMapping(path = "/{stashTabId}/items", headers = "HX-Request")
  public String reloadStashTabItemsListFragment(
      @PathVariable(name = "stashTabId", required = true) String stashTabId, Model model,
      HttpSession session) {
    String accountName = (String) session.getAttribute("accountName");
    String poeSessionId = (String) session.getAttribute("poeSessionId");

    List<ItemDto> items =
        this.stashTabService.reloadStashTabItems(accountName, poeSessionId, stashTabId);
    model.addAttribute("items", items);

    return "fragments/item-list";
  }

}
