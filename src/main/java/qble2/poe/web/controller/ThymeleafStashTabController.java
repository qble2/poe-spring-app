package qble2.poe.web.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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

  @GetMapping(path = "list", headers = "HX-Request")
  public String htmxGetStashTabsListFragment(
      @RequestParam(name = "leagueId", required = false) String leagueId, Model model) {
    List<StashTabDto> stashTabs = this.stashTabService.getStashTabs(leagueId);
    model.addAttribute("stashTabs", stashTabs);

    return "fragments/stash-frags :: stash-tabs-list";
  }

  @PostMapping(path = "list", headers = "HX-Request")
  public String htmxReloadStashTabsListFragment(
      @RequestParam(name = "leagueId", required = true) String leagueId, Model model) {

    String accountName = principalUtils.getAccountName();
    String poeSessionId = principalUtils.getPoeSessionId();

    List<StashTabDto> stashTabs =
        this.stashTabService.reloadStashTabs(accountName, poeSessionId, leagueId);
    model.addAttribute("stashTabs", stashTabs);

    return "fragments/stash-frags :: stash-tabs-list";
  }

  @GetMapping(path = "/{stashTabId}", headers = "HX-Request")
  public String htmxGetStashTabDetailsFragment(
      @PathVariable(name = "stashTabId", required = true) String stashTabId, Model model) {
    StashTabDto stashTab = this.stashTabService.getDetailedStashTab(stashTabId);
    model.addAttribute("stashTab", stashTab);

    return "fragments/stash-frags :: stash-tab-details";
  }

  @PostMapping(path = "/{stashTabId}", headers = "HX-Request")
  public String htmxReloadStashTabDetailsFragment(
      @PathVariable(name = "stashTabId", required = true) String stashTabId, Model model) {
    String accountName = principalUtils.getAccountName();
    String poeSessionId = principalUtils.getPoeSessionId();

    StashTabDto stashTab =
        this.stashTabService.reloadStashTab(accountName, poeSessionId, stashTabId);
    model.addAttribute("stashTab", stashTab);

    return "fragments/stash-frags :: stash-tab-details";
  }

  @PostMapping(path = "/{stashTabId}/price-check", headers = "HX-Request")
  public String htmxPriceCheckedStashTabDetailsFragment(
      @PathVariable(name = "stashTabId", required = true) String stashTabId, Model model) {
    StashTabDto stashTab =
        this.stashTabService.priceCheckStashTab(stashTabId);
    model.addAttribute("stashTab", stashTab);

    return "fragments/stash-frags :: stash-tab-details";
  }

  @GetMapping(path = "/{stashTabId}/table", headers = "HX-Request")
  public String htmxGetStashTabDetailsTableFragment(
      @PathVariable(name = "stashTabId", required = true) String stashTabId, Model model,
      HttpServletResponse response) {
    StashTabDto stashTab = this.stashTabService.getDetailedStashTab(stashTabId);
    model.addAttribute("stashTab", stashTab);

    response.setHeader("HX-Trigger", "stashTabDetailsTable");

    return "fragments/stash-frags :: stash-tab-details-table";
  }

  @GetMapping(path = "/{stashTabId}/2d", headers = "HX-Request")
  public String htmxGetStashTabDetails2DFragment(
      @PathVariable(name = "stashTabId", required = true) String stashTabId, Model model) {
    StashTabDto stashTab = this.stashTabService.getDetailedStashTab(stashTabId);
    model.addAttribute("stashTab", stashTab);

    boolean isPreviewAvailable = this.stashTabService.isStashTabPreviewAvailable(stashTab);
    model.addAttribute("previewAvailable", isPreviewAvailable);

    return "fragments/stash-frags :: stash-tab-details-2d";
  }

}
