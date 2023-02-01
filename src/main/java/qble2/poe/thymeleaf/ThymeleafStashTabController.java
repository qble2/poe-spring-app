package qble2.poe.thymeleaf;

import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import qble2.poe.item.ItemDto;
import qble2.poe.stash.StashTabDto;
import qble2.poe.stash.StashTabService;

@Controller
@RequestMapping(path = "stash-tabs")
public class ThymeleafStashTabController {

  @Autowired
  private StashTabService stashTabService;

  @GetMapping
  public String getStashTabs(Model model, HttpSession session) {
    String leagueId = (String) session.getAttribute("leagueId");

    List<StashTabDto> stashTabs = this.stashTabService.getStashTabs(leagueId);
    model.addAttribute("stashTabs", stashTabs);

    return "stash-tab-list";
  }

  @PostMapping
  public String reloadStashTabs(Model model, HttpSession session) {
    String accountName = (String) session.getAttribute("accountName");
    String poeSessionId = (String) session.getAttribute("poeSessionId");
    String leagueId = (String) session.getAttribute("leagueId");

    List<StashTabDto> stashTabs =
        this.stashTabService.reloadStashTabs(accountName, poeSessionId, leagueId);
    model.addAttribute("stashTabs", stashTabs);

    return "stash-tab-list";
  }

  @GetMapping(path = "/{stashTabId}")
  public String getDetailedStashTab(
      @PathVariable(name = "stashTabId", required = true) String stashTabId, Model model,
      HttpSession session) {
    StashTabDto stashTab = this.stashTabService.getDetailedStashTab(stashTabId);
    model.addAttribute("stashTab", stashTab);

    return "stash-tab";
  }

  @PostMapping(path = "/{stashTabId}/items")
  public String reloadStashTabItems(
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
