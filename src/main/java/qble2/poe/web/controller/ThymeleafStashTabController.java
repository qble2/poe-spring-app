package qble2.poe.web.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import qble2.poe.item.ItemDto;
import qble2.poe.security.PrincipalUtils;
import qble2.poe.stash.StashTabDto;
import qble2.poe.stash.StashTabService;

@Controller
@RequestMapping(path = "stash-tabs")
public class ThymeleafStashTabController {

  @Autowired
  private StashTabService stashTabService;

  @Autowired
  private PrincipalUtils principalUtils;

  @InitBinder
  public void initBinder(WebDataBinder binder) {
    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
  }

  @GetMapping
  public String getStash(@RequestParam(name = "leagueId", required = false) String leagueId,
      Model model) {
    List<StashTabDto> stashTabs = this.stashTabService.getStashTabs(leagueId);
    model.addAttribute("stashTabs", stashTabs);

    return "stash";
  }

  @PostMapping
  public String reloadStash(@RequestParam(name = "leagueId", required = true) String leagueId,
      Model model) {
    String accountName = principalUtils.getAccountName();
    String poeSessionId = principalUtils.getPoeSessionId();

    List<StashTabDto> stashTabs =
        this.stashTabService.reloadStashTabs(accountName, poeSessionId, leagueId);
    model.addAttribute("stashTabs", stashTabs);

    return "stash";
  }

  @GetMapping(path = "/{stashTabId}")
  public String getStashTab(@PathVariable(name = "stashTabId", required = true) String stashTabId,
      Model model) {
    StashTabDto stashTab = this.stashTabService.getStashTab(stashTabId);
    model.addAttribute("stashTab", stashTab);

    return "stash-tab";
  }

  @GetMapping(path = "get-stash-tabs-list", headers = "HX-Request")
  public String htmxGetStashTabsListFragment(
      @RequestParam(name = "leagueId", required = false) String leagueId, Model model) {
    List<StashTabDto> stashTabs = this.stashTabService.getStashTabs(leagueId);
    model.addAttribute("stashTabs", stashTabs);

    return "fragments/stash-frags :: stash-tabs-list";
  }

  @PostMapping(path = "reload-stash-tabs-list", headers = "HX-Request")
  public String htmxReloadStashTabsListFragment(
      @RequestParam(name = "leagueId", required = true) String leagueId, Model model) {

    String accountName = principalUtils.getAccountName();
    String poeSessionId = principalUtils.getPoeSessionId();

    List<StashTabDto> stashTabs =
        this.stashTabService.reloadStashTabs(accountName, poeSessionId, leagueId);
    model.addAttribute("stashTabs", stashTabs);

    return "fragments/stash-frags :: stash-tabs-list";
  }

  @GetMapping(path = "/{stashTabId}/get-items", headers = "HX-Request")
  public String htmxGetStashTabItemsFragment(
      @PathVariable(name = "stashTabId", required = true) String stashTabId, Model model) {
    List<ItemDto> items = this.stashTabService.getStashTabItems(stashTabId);
    model.addAttribute("items", items);
    model.addAttribute("stashTabId", stashTabId);

    return "fragments/stash-frags :: stash-tab-items";
  }

  @PostMapping(path = "/{stashTabId}/reload-items", headers = "HX-Request")
  public String htmxReloadStashTabItemsFragment(
      @PathVariable(name = "stashTabId", required = true) String stashTabId, Model model) {
    String accountName = principalUtils.getAccountName();
    String poeSessionId = principalUtils.getPoeSessionId();

    List<ItemDto> items =
        this.stashTabService.reloadStashTabItems(accountName, poeSessionId, stashTabId);
    model.addAttribute("items", items);
    model.addAttribute("stashTabId", stashTabId);

    return "fragments/stash-frags :: stash-tab-items";
  }

}
