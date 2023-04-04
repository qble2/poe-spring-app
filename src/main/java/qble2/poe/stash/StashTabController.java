package qble2.poe.stash;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import qble2.poe.item.ItemDto;

@RestController
@RequestMapping(path = "api/stash-tabs", produces = MediaType.APPLICATION_JSON_VALUE)
public class StashTabController {

  @Autowired
  private StashTabService stashTabService;

  @GetMapping
  public List<StashTabDto> getStashTabs(
      @RequestParam(name = "leagueId", required = true) String leagueId) {
    return this.stashTabService.getStashTabs(leagueId);
  }

  @PostMapping
  public List<StashTabDto> reloadStashTabs(
      @RequestParam(name = "accountName", required = true) String accountName,
      @RequestParam(name = "poeSessionId", required = true) String poeSessionId,
      @RequestParam(name = "leagueId", required = true) String leagueId) {
    return this.stashTabService.reloadStashTabs(accountName, poeSessionId, leagueId);
  }

  @GetMapping(path = "/{stashTabId}/items")
  public List<ItemDto> getStashTabItems(
      @PathVariable(name = "stashTabId", required = true) String stashTabId) {
    return this.stashTabService.getStashTabItems(stashTabId);
  }

  @PostMapping(path = "/{stashTabId}/items")
  public List<ItemDto> reloadStashTabItems(
      @PathVariable(name = "stashTabId", required = true) String stashTabId,
      @RequestParam(name = "accountName", required = true) String accountName,
      @RequestParam(name = "poeSessionId", required = true) String poeSessionId) {
    return this.stashTabService.reloadStashTabItems(accountName, poeSessionId, stashTabId);
  }

}
